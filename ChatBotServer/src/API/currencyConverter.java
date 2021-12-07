/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
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
public class currencyConverter {

    public static void main(String[] args) {

        //  System.out.println(convertMoney("USD", "VND", "1"));
        ArrayList<String> arr = new ArrayList<>();
        arr = getListCodeCity();
        for(int i =0; i < arr.size() ; i++){
            System.out.println(arr.get(i));
        }
   // System.out.println(convertMoney("USD", "VND", "100"));

    }

    public static String convertMoney(String fromCode, String toCode, String amount) {
        // api: https://vn.exchange-rates.org/converter/USD/VND/100
        String result = "";
        try {
            String link = "https://vn.exchange-rates.org/converter/" + fromCode + "/" + toCode + "/" + amount;
            String url = Jsoup.connect(link)
                    .followRedirects(true)
                    .execute()
                    .url()
                    .toExternalForm();

            Document doc = Jsoup.connect(url).get();

            Elements cur1 = doc.getElementsByClass("col-xs-6 result-cur1");
            String money1 = cur1.get(0).getElementsByTag("strong").get(0).getElementsByTag("span").get(0).text();

            Elements cur2 = doc.getElementsByClass("col-xs-6 result-cur2");
            String money2 = cur2.get(0).getElementsByTag("strong").get(0).getElementsByTag("span").get(0).text();

            result = money1 + " " + fromCode + " = " + money2 + " " + toCode;

        } catch (IOException e) {
            System.out.println(e);
        }

        return result;

    }


    public static ArrayList getListCodeCity() {
        ArrayList<String> listCodeCity = new ArrayList<String>();
        try {
            // api currencyConverter: http://api.exchangeratesapi.io/v1/latest?access_key=3b37b317d8b08357cba01b7f8c3e5c6b

            URL url = new URL("http://api.exchangeratesapi.io/v1/latest?access_key=3b37b317d8b08357cba01b7f8c3e5c6b");

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
                //  System.out.println(info);

                JSONObject obj = new JSONObject(info);
                //  System.out.println(obj);

                JSONObject rates = obj.getJSONObject("rates");

                
                // lấy key (mã code của các quốc gia) trong trong rates
                Iterator<String> listKEY = rates.keys();
                do {
                    String newKEY = listKEY.next().toString();
                    listCodeCity.add(newKEY);
                  //  System.out.println(newKEY);

                } while (listKEY.hasNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCodeCity;
    }
}
