/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import static Clients.FormChat.txtArea;
import MaHoa.AESClient;
import MaHoa.EncryptKeyAES;
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
public class Client {
    // t làm là để nó thành 1 đối tượng riêng, có in out rồi socket trong này
    private  Socket socket = null;
    private  BufferedReader in = null;
    private  BufferedWriter out = null;
    private String serverResponse;
    
    public Client(){
        khoiTao();
    }
    
    private void khoiTao(){
        try {
            socket = new Socket("localhost", 5000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            sendKeyServer();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        
        
    }

    public Socket getSocket() {
        return socket;
        
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public BufferedWriter getOut() {
        return out;
    }

    public void setOut(BufferedWriter out) {
        this.out = out;
    }
    
    public void sendClient(String input){
        try {
            out.write(input + "\n");
            out.flush();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        nhanClient();
            
    }
    
    private void sendKeyServer() throws Exception{
         try {
            
            
            System.out.println("Tạo khóa và mã hóa khóa gửi cho sv");
            AESClient.initKey(); //tạo khóa
            String keyString = EncryptKeyAES.EncryptKeyWithRSA(); // mã hóa khóa
            try {
                out.write(keyString);
                    out.newLine();
                out.flush();
            } catch (IOException ex) {
                System.out.println(ex);
            }
            System.out.println("gửi key đã mã hóa thành công.");
            
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    private void nhanClient(){
        try {
            String tmp = in.readLine();
            System.out.println(tmp);
            serverResponse = AESClient.decrypt(tmp) + "\r\n";
            // nếu response có chứa chuỗi "\t" thì sẽ thực hiện tách các tokens thành từng dòng in ra cho đẹp :>
            System.out.println(serverResponse);
            // Hin2h nhu cho nay
            if (serverResponse.contains("@@@@")) {
//                System.out.println("ok chuaaaaaaaaaaa");
                StringTokenizer st = new StringTokenizer(serverResponse, "@@@@", false);
                while (st.hasMoreTokens()) {

                    txtArea.append(st.nextToken() + "\r\n");
                }
                // gán chuỗi bằng rỗng để không in ra thêm lần nữa
                serverResponse = "";
            }
            txtArea.append(serverResponse);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    
     public void dongKetNoi() {
        System.out.println("Client closed connection");
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }

//            stdIn.close();
    }
    
    
    
    
    
    
    
    
    
}
