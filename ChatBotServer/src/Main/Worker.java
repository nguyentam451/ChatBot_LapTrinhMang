package Main;

import API.IpLocation;
import API.Simsimi;
import API.PortScanner;
import API.Whois;
import API.currencyConverter;
import API.thoiTiet;
import MaHoa.DecryptKeyAES;
import MaHoa.ListKey;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Worker implements Runnable {

    private Socket socket; // hình như cho nay null nen khong su dung duoc multithread *********** cho nay quan trong
    private BufferedReader in; // hai vl, lan dau thay 
    private BufferedWriter out;

    public static String huongDanCuPhap() {
        return " Cú pháp xem thời tiết: 'thoitiet;' + 'tên thành phố' @@@@ vd: thoitiet;london hoặc vd:thoitiet;ho chi minh (nếu tên thành phố có 2 từ trở lên thì thêm dấu ' ') @@@@ "
                + "Cú pháp xem thông tin domain: 'whois; + 'tên miền' @@@@ vd: whois;sgu.edu.vn"
                + "@@@@ Cú pháp xem thông tin IP: 'iplocation;' + 'địa chỉ ip' @@@@ vd: iplocation;115.76.51.83."
                + "@@@@ Cú pháp quét port: 'quetport;' + 'địa chỉ ip' + 'port x' + port 'y' @@@@ vd: quetport;115.76.51.83;5623;6666"
                + "@@@@ Cú pháp chuyển đổi tiền: chuyentien";
    }

    private int indexKey;

    public Worker(Socket socket) {
        this.socket = socket;
    }

    private void nhanKeyVaLuu() throws Exception {
        try {
            String line = in.readLine(); // nhận key

            // giải mã và lưu trữ vào arraylist, với thứ tự key
            indexKey = ListKey.addKey(DecryptKeyAES.DecryptKeyWithRSA(line)); // index =0
            System.out.println("Số thứ tự key " + indexKey);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void run() {
        System.out.println("Client " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " is connected");

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            nhanKeyVaLuu();

            while (true) {

                // nhận từ client, giải mã
                String line = ListKey.decryptWithIndexKey(in.readLine(), indexKey);

                if (line.equals("bye")) {

                    break;
                }

                System.out.println("Server received " + line);
                String res = "";

                // chỗ nãy để xử lý chức năng
                // nếu chuỗi chứa từ currencyConverter thì sẽ thực hiện chức năng chuyển tiền
                // line sẽ có dạng: currencyConverter USD VND 1000  sau đó dùng StringTokenizer tách ra
                if (line.contains("currencyConverter")) {
                    //  line = in2.readLine();
                    System.out.println(line);
                    StringTokenizer st = new StringTokenizer(line, " ");
                    while (st.hasMoreTokens()) {
                        String syntax = st.nextToken(); // syntax là cờ hiệu currencyConverter nên không lấy
                        String chuoi1 = st.nextToken();
                        String chuoi2 = st.nextToken();
                        String chuoi3 = st.nextToken();
                        // gọi api
                        res = currencyConverter.convertMoney(chuoi1, chuoi2, chuoi3);
                    }

                } else if (line.contains("thoitiet;")) { // nếu trong chuỗi có chứa chữ thoitiet thì thực hiện chức năng xem thời tiết
                    StringTokenizer st = new StringTokenizer(line, ";");
                    int i = 0;
                    String syntax, city = null;

                    if (st.countTokens() != 2) {
                        res = " Sai cú pháp @@@@ Cú pháp xem thời tiết: 'thoitiet;' + 'tên thành phố' \n vd: thoitiet;london hoặc vd:thoitiet;ho chi minh (nếu tên thành phố có 2 từ trở lên thì thêm dấu ' ')";
                    } else {

                        while (st.hasMoreTokens()) {
                            syntax = st.nextToken(); // syntax là cờ hiệu nên không lấy
                            city = st.nextToken().trim();   // token thứ 2 là tên thành phố
                            break;
                        }
                        
                        res = thoiTiet.getWeather(city); // truyền tên thành phố vào hàm getWeather
                        System.out.println(line);

                    }

                } else if (line.contains("whois;")) {
                    StringTokenizer st = new StringTokenizer(line, ";");
                    String syntax, domain = null;
                    if (st.countTokens() != 2) {
                        res = " Sai cú pháp @@@@ Cú pháp xem thông tin domain: 'whois; + 'tên miền' @@@@ vd: whois;sgu.edu.vn";
                    } else {
                        while (st.hasMoreTokens()) {
                            syntax = st.nextToken(); // syntax là cờ hiệu nên không lấy
                            domain = st.nextToken().trim();   // token thứ 2 là tên domain
                            break;

                        }

                        res = Whois.getInfoDomain(domain); // truyền tên domain vào hàm getWeather
                        System.out.println(line);

                    }

                } else if (line.contains("thời tiết") || line.contains("xem port") || line.contains("vị trí ip") || line.contains("tên miền") || line.contains("chuyển đổi") || line.contains("quét")) {
                     Random rand = new Random();
                     int ranNum = rand.nextInt(5)+1;
                   //  System.out.println("aaaaaaaaaaaaaaaaaa" + ranNum);
                     if(ranNum == 1){
                         res = "Bạn hãy ấn vào nút hướng dẫn ở góc phải màn hình để xem cú pháp";
                     }
                     else if(ranNum == 2){
                         res = "Bạn ấn vô nút hướng dẫn ở trên kia đuy";
                     }
                     else if (ranNum == 3 ){
                         res = "Ấn nút hướng dẫn bên phải để xem cú pháp kìa ông";
                     }
                     else if (ranNum == 4){
                         res = "Click chuột vào hướng dẫn để xem cú pháp tra cứu nghen";
                     }
                     else if (ranNum == 5){
                         res = "Nút hướng dẫn ở trên kìaaaaa";
                     }
                     
                    
                } else if (line.contains("iplocation;")) {
                    StringTokenizer st = new StringTokenizer(line, ";");
                    String syntax, ip = null;
                    if (st.countTokens() != 2) {
                        res = " Sai cú pháp @@@@ Cú pháp xem thông tin IP: 'iplocation;' + 'địa chỉ ip' @@@@ vd; iplocation;115.76.51.83";
                    } else {
                        while (st.hasMoreTokens()) {
                            syntax = st.nextToken(); // syntax là cờ hiệu nên không lấy
                            ip = st.nextToken();   // token thứ 2 là ip cần tra thông tin
                            break;
                        }
                        res = IpLocation.findIpInformation(ip); // truyền tên domain vào hàm findIpInfomation
                        System.out.println(line);

                    }

                } else if (line.equals("chuyentien")) {
                    ArrayList<String> arr = new ArrayList<>();
                    arr = currencyConverter.getListCodeCity();
                    res = currencyConverter.chuyenListCodeCitySangChuoi(arr);

                } else if (line.contains("quetport;")) {
                    // phân cách bởi dấu ';'
                    StringTokenizer st = new StringTokenizer(line, ";");
                    String syntax, port = null, x = null, y = null;
                    if (st.countTokens() != 4) {
                        res = " Sai cú pháp @@@@ Cú pháp quét port: 'quetport;' + 'địa chỉ ip' + 'port x' + port 'y' @@@@ vd: quetport;115.76.51.83;5623;6666";
                    } else {
                        while (st.hasMoreTokens()) {

                            syntax = st.nextToken(); // syntax là cờ hiệu nên không lấy
                            port = st.nextToken().trim();   // token thứ 2 là tên port muốn quét
                            x = st.nextToken().trim();   // quét từ port x
                            y = st.nextToken().trim();   // đến port y

                            break;
                        }
                        // vì x, y là kiểu int nên ép về kiểu int
                        if (kiemTraSo(x) && kiemTraSo(y)) {
                            int xTemp = Integer.parseInt(x);
                            int yTemp = Integer.parseInt(y);
                            res = PortScanner.quetport(port, xTemp, yTemp);
                        } else {
                            res = " Sai cú pháp @@@@ Cú pháp quét port: 'quetport;' + 'địa chỉ ip' + 'port x' + port 'y' @@@@ vd: quetport;115.76.51.83;5623;6666\"";
                        }
                    }

                } else if (line.equals("cuphap")) {
                    res = huongDanCuPhap();
                } else {
                    res = Simsimi.getResponeFromSimsimi(line);
                }

                System.out.println(res + "Key " + indexKey + " " + socket.toString());

                // Mã hóa dữ liệu
                String res2 = ListKey.encryptWithIndexKey(res, indexKey);
                System.out.println(res2);
                out.write(res2);
                out.newLine();
                out.flush();

            }
            System.out.println("Server closed connection");
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Lỗi 1: " + e);
        } catch (Exception ex) {
            System.out.println("Lỗi 2: " + ex);
        }

    }

    public static boolean kiemTraSo(String number) {
        if (!number.matches("^[1-9][\\d]*$")) {
            return false;
        }
        return true;
    }
}
