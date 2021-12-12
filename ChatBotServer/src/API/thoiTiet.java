/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.StringTokenizer;
import static java.util.logging.Logger.global;
import jdk.nashorn.internal.parser.JSONParser;
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
public class thoiTiet {

    static String title = "";
    
    public static void listDanhSachThanhPho() {
        try {
            String url = Jsoup.connect("https://www.metaweather.com/")
                    .followRedirects(true)
                    .execute()
                    .url()
                    .toExternalForm();

            Document doc = Jsoup.connect(url).get();

            Elements info = doc.getElementsByTag("option");

            for (Element a : info) {
                System.out.println(a.val());
            }

        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public static int getJsonThoiTiet(String city) {
        int woeid = 0;
        try {
            //Public API:
            //https://www.metaweather.com/api/location/search/?query=<CITY>
            //https://www.metaweather.com/api/location/44418/
            
            city = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());

            URL url = new URL("https://www.metaweather.com/api/location/search/?query=" + city);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                String info = informationString.toString();
               // System.out.println(info);
                // đọc các phần tử trong mảng
                JSONArray jsonarray = new JSONArray(info);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    woeid = jsonobject.getInt("woeid");
                    title = jsonobject.getString("title");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return woeid;
    }

    public static String getWeather(String city) {
        String line = "";
        try {
            
            int woeid = getJsonThoiTiet(city); // chỗ này =0 nè
            String link = "https://www.metaweather.com/" + woeid + "/";
            String url = Jsoup.connect(link)
                    .followRedirects(true)
                    .execute()
                    .url()
                    .toExternalForm();

            Document doc = Jsoup.connect(url).get();

            System.out.println("woeid 2 = " + woeid);
          //  String cityName = doc.getElementsByTag("h1").text();
          //  String weather = doc.getElementsByClass("row weather weather-lrg").text();
        
            Elements check = doc.getElementsByClass("col-lg-2");
            if (!check.isEmpty() || woeid != 0) {
          
                Elements info = doc.getElementsByClass("col-lg-2 col-md-2 col-sm-2 col-xs-4 ");
                Elements info2 = doc.getElementsByClass("col-lg-2 col-md-2 col-sm-2 col-xs-4  tomorrow");

            //    line += info2.get(0).text()+ "zzzz";
                for(int i=0; i <info.size(); i++){
                   // System.out.println(info.get(i).text());
                   // line += info.get(i).text()+ "\n";
                    line += info.get(i).text() + "@@@@";
                    if(i==0){
                        line+=info2.get(0).text()+"@@@@";
                        continue;
                    }
                }
            }  else {
                return "Không tìm thấy thành phố hoặc sai cú pháp. "
                    + "\n Cú pháp xem thời tiết: 'thoitiet;' + 'tên thành phố' @@@@ vd: thoitiet;london hoặc vd:thoitiet;ho chi minh (nếu tên thành phố có 2 từ trở lên thì thêm dấu ' ') @@@@ ";
            }

        } catch (IOException e) {
            System.err.println(e);
            return "Không tìm thấy thành phố hoặc sai cú pháp. "
                    + "\n Cú pháp xem thời tiết: 'thoitiet;' + 'tên thành phố' @@@@ vd: thoitiet;london hoặc vd:thoitiet;ho chi minh (nếu tên thành phố có 2 từ trở lên thì thêm dấu ' ') @@@@ ";
             
        }
        
        return "Thời tiết của thành phố: " + title + "\n" + line;

    }

    public static void main(String[] args) {
        //System.out.println(getWeather("ho+chi+minh"));
    }
}
