package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Admin
 */
public class Whois {

    public static void main(String[] args) {
        System.out.println(getInfoDomain("sgu.edu.vn"));
    }

    public static String getInfoDomain(String domain) {
        // api: https://whois.inet.vn/api/whois/domainspecify/sgu.edu.vn
        String result = "";
        try {

            // lấy json về
            URL url = new URL("https://whois.inet.vn/api/whois/domainspecify/" + domain);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // dòng này fix lỗi respone code 403
            conn.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();
            //System.out.println(responseCode);
            // 200 OK
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);

                }
                in.close();

                // response là chuỗi json
                // bóc tách chuỗi json
                JSONObject obj = new JSONObject(response.toString());
                // messages là 1 mảng
                
                // Kiểm tra json code để biết domain có được đăng ký hay không
                String code = obj.getString("code");
                
                if (code.equals("0")){
                    String message = obj.getString("message");
                    if (message.equals("Đã được đăng ký")){
                        
                
                String domainName = obj.getString("domainName");
                String registrar = obj.getString("registrar");
                
                
                
                String nameServer = "";
                JSONArray nameServerArr = obj.getJSONArray("nameServer");
                for (int i = 0; i < nameServerArr.length(); i++) {
                    nameServer += (String) nameServerArr.get(i) + "; ";
                }
                String status = "";
                JSONArray statusArr = obj.getJSONArray("status");
                for (int i = 0; i < statusArr.length(); i++) {
                    status += (String) statusArr.get(i) + "; ";
                }

                String creationDate = obj.getString("creationDate");
                String expirationDate = obj.getString("expirationDate");
                String registrantName = obj.getString("registrantName");

                //System.out.println("Tên miền: " + domainName);
                //System.out.println("Quản lý tại nhà đăng ký: " + registrar);
                //System.out.println("Nameserver: " + nameServer);
                //System.out.println("Cờ trạng thái: " + status);
                //System.out.println("Ngày đăng ký: " + creationDate);
                //System.out.println("Ngày hết hạn: " + expirationDate);
                //System.out.println("Chủ sở hữu tên miền: " + registrantName);
                
                // chạy ra ko ? ra bình thường , để t chạy thử
                result = "Tên miền: " + domainName + "@@@@"
                        + "Quản lý tại nhà đăng ký: " + registrar + "@@@@"
                        + "Nameserver: " + nameServer + "@@@@"
                        + "Cờ trạng thái: " + status + "@@@@"
                        + "Ngày đăng ký: " + creationDate + "@@@@"
                        + "Ngày hết hạn: " + expirationDate + "@@@@"
                        + "Chủ sở hữu tên miền: " + registrantName + "@@@@";
                
                System.out.println(result);
                    } else {
                        return message;
                    }
                } else {
                    return "Domain chưa được đăng ký";
                }
            
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}
