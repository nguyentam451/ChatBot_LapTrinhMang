/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import java.io.BufferedReader;
import static java.io.FileDescriptor.in;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
public class Simsimi {

    public static void main(String[] args) {
        
        System.out.println("Simsimi said: " + getResponeFromSimsimi("mày là ai"));
    }

    public static String getResponeFromSimsimi(String input) {
        String text = "";
        try {
            // api https://api.simsimi.net/v2/?text=hi&lc=vn&cf=true
            // lc là ngôn ngữ
            // cf là chatfuel
            
            // trả về một chuỗi json
            URL url = new URL("https://api.simsimi.net/v2/?text="+input+"&lc=vn&cf=true");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // dòng này fix lỗi respone code 403
            conn.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();
            System.out.println(responseCode);
            // 200 OK
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);

                }
                in.close();
                
                
                
                System.out.println(response);
                // response là chuỗi json
                // bóc tách chuỗi json
                JSONObject obj = new JSONObject(response.toString());
                // messages là 1 mảng
                
                JSONArray arr = obj.getJSONArray("messages");
                for (int i = 0; i < arr.length(); i++) {
                    text = "Simsimi: "+ arr.getJSONObject(i).getString("text");
                   
                }
               
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Loi";
        }
        return text;
    }
}
