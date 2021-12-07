/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import API.IpLocation;
import API.Simsimi;
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
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Worker implements Runnable{

        
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;

    public static String huongDanCuPhap() {
        return " Cú pháp xem thời tiết: 'thoitiet;' + 'tên thành phố' @@@@ vd: thoitiet;london @@@@ vd:thoitiet;ho+chi+minh (nếu tên thành phố có 2 chữ cái trở lên thì thêm dấu '+') @@@@ "
                + "Cú pháp xem thông tin domain: 'whois; + 'tên miền' @@@@ vd: whois;sgu.edu.vn"
                + "Cú pháp xem thông tin IP: 'iplocation;' + 'địa chỉ ip' @@@@ vd; iplocation;115.76.51.83.";
    }
    
    private int indexKey;
    
    public Worker(Socket socket) {
        this.socket = socket;
    }
    
    
    private void nhanKeyVaLuu() throws Exception{
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
                String line = ListKey.decryptWithIndexKey(in.readLine(), indexKey) ;

                if (line.equals("bye")) {

                    break;
                }
                
                
                // Nhận key từ client
                
                
                
                System.out.println("Server received " + line);
                String res="";

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
                        res = currencyConverter.convertMoney(chuoi1, chuoi2, chuoi3);
                    }

                } else if (line.contains("thoitiet;")) { // nếu trong chuỗi có chứa chữ thoitiet thì thực hiện chức năng xem thời tiết
                    StringTokenizer st = new StringTokenizer(line, ";");

                    while (st.hasMoreTokens()) {
                        String syntax = st.nextToken(); // syntax là cờ hiệu nên không lấy
                        String city = st.nextToken();   // token thứ 2 là tên thành phố
                        res = thoiTiet.getWeather(city); // truyền tên thành phố vào hàm getWeather
                        System.out.println(line);
                    }

                } else if (line.contains("whois;")) {
                    StringTokenizer st = new StringTokenizer(line, ";");
                    while (st.hasMoreTokens()) {
                        String syntax = st.nextToken(); // syntax là cờ hiệu nên không lấy
                        String domain = st.nextToken();   // token thứ 2 là tên domain
                        res = Whois.getInfoDomain(domain); // truyền tên domain vào hàm getWeather
                        System.out.println(line);
                    }

                } else if (line.contains("iplocation;")) {
                    StringTokenizer st = new StringTokenizer(line, ";");
                    while (st.hasMoreTokens()) {
                        String syntax = st.nextToken(); // syntax là cờ hiệu nên không lấy
                        String ip = st.nextToken();   // token thứ 2 là ip cần tra thông tin
                        res = IpLocation.findIpInformation(ip); // truyền tên domain vào hàm findIpInfomation
                        System.out.println(line);
                    }
                } else if (line.equals("cuphap")) {
                    res = huongDanCuPhap();
                } else {
                    res = Simsimi.getResponeFromSimsimi(line);
                }

                // đây dòng này
                
                // Mã hóa dữ liệu
                String res2 = ListKey.encryptWithIndexKey(res, indexKey);
                out.write(res2);
                out.newLine();
                out.flush();

            }
            System.out.println("Server closed connection");
            in.close();
            out.close();
            socket.close();
            
        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
    }
    
}
