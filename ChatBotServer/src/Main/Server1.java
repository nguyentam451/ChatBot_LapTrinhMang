package Main;

import API.IpLocation;
import API.Simsimi;
import API.Whois;
import API.currencyConverter;
import API.thoiTiet;
//import Clients.CurrencyConverterForm;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author Admin
 */
public class Server1 {

    private static ServerSocket server = null;
    private static int port=5000;
    private static final int numThread=10;
    

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(numThread);
        try {
            server = new ServerSocket(port);
            System.out.println("Server đã kết nối");
            System.out.println("Đang đợi client.....");
            
            while(true){
                Socket socket = server.accept();
                executor.execute(new Worker(socket));
            }

        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        
        
        
//        try {
//            server = new ServerSocket(5100);
//            while (true) {
//                try {
//                    System.out.println("Server started, waiting for clients...");
//                    
//
//                    
//
//                    
//
//                } catch (IOException e) {
//                    System.err.println("Lỗi ở đâu đó trong vòng lặp" + e);
//                }
//            }
//
//        } catch (IOException e) {
//            System.out.println(e);
//        }
    }
}
