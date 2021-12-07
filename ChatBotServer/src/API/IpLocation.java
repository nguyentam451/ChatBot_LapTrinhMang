/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import java.io.IOException;
import java.util.StringTokenizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Admin
 */
public class IpLocation {

    // url: https://www.iplocation.net/search?cx=partner-pub-1026064395378929%3A2796854705&cof=FORID%3A10&ie=UTF-8&q=124.158.13.4
    public static void main(String[] args) {
        System.out.println(findIpInformation("115.76.51.83."));
    }

    public static String findIpInformation(String ip) {

        String result = "";
        try {
            String link = "https://www.iplocation.net/search?cx=partner-pub-1026064395378929%3A2796854705&cof=FORID%3A10&ie=UTF-8&q=" + ip;
            String url = Jsoup.connect(link)
                    .followRedirects(true)
                    .execute()
                    .url()
                    .toExternalForm();

            Document doc = Jsoup.connect(url).get();

            Elements info = doc.getElementsByClass("table_dark_green").get(0).getElementsByTag("td");

            //   String domainName = doc.getElementsByClass("table_dark_green").get(0).getElementsByTag("td").get(0).text();
            for (int i = 0; i < info.size(); i++) {
                String domainName = doc.getElementsByClass("table_dark_green").get(i).getElementsByTag("td").get(0).text();
                String country = doc.getElementsByClass("table_dark_green").get(i).getElementsByTag("td").get(1).text();
                String region = doc.getElementsByClass("table_dark_green").get(i).getElementsByTag("td").get(2).text();
                String city = doc.getElementsByClass("table_dark_green").get(i).getElementsByTag("td").get(3).text();
                String ISP = doc.getElementsByClass("table_dark_green").get(i).getElementsByTag("td").get(4).text();
                String organization = doc.getElementsByClass("table_dark_green").get(i).getElementsByTag("td").get(5).text();
                String latitude = doc.getElementsByClass("table_dark_green").get(i).getElementsByTag("td").get(6).text();
                String longitude = doc.getElementsByClass("table_dark_green").get(i).getElementsByTag("td").get(7).text();

                result += "domain name: " + domainName
                        + "; country: "
                        + country + "; region: "
                        + region + "; city: "
                        + city + "; ISP: "
                        + ISP + "; organization: "
                        + organization + "; latitude: "
                        + latitude + "; longitude: "
                        + longitude
                        + "@@@@";
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        return result;

    }
}
