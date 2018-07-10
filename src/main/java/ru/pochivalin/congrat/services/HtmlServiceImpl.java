package ru.pochivalin.congrat.services;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Log4j2
@Component
public final class HtmlServiceImpl {

    @Value("${html.string.size}")
    private int stringSize;

    public List<String> getFamousEvent(final String url, final int day, final int month) {
        List<String> famousEvent = new ArrayList<String>();
        try {
            //doc = Jsoup.connect("http://www.calend.ru/events/6-18/").get();
            //String URL = url + "/" + month + "-" + day + "/";
            String urlParse = url + "/" + month + "/" + day;
            Document doc = Jsoup.connect(urlParse).get();

            Elements p = doc.select("p");
            List<Integer> countList = new ArrayList<Integer>();
            for (Element el : p) {
                String strEl = el.toString();
                Integer iInteger = new Integer(strEl.length());
                countList.add(iInteger);
                //System.out.println(strEl.length());
            }

            int maxEl = countList.indexOf(Collections.max(countList));
            String strMaxEl = p.get(maxEl).text().toString();
            String[] parts = strMaxEl.split("\\.");
            //List<String> famousEvent = new ArrayList<String>();
            //int k=0;
            for (int i = 0; i < parts.length; i++) {
                String number = null;
                String elTrim = parts[i].trim();

                if (elTrim.length() >= stringSize) {
                    number = elTrim.substring(0, stringSize);
                }

                if (elTrim.length() < stringSize) {
                    number = elTrim.substring(0, elTrim.length() - 1);
                }

                if (checkString(number)) {
                        famousEvent.add(elTrim + ". ");
                } else {
                        int oldIndex = famousEvent.size() - 1;
                        famousEvent.set(oldIndex, famousEvent.get(oldIndex).concat(elTrim).concat("."));
                }

            }

            //System.out.println(rezult);

            //Elements clazz = doc.getElementsByClass("famous-date plusyear");
            //Elements tagA = clazz.select("a");

            //Elements child = el.getElementsByTag(tag);
            //List<String> list = new ArrayList<String>();
            //List<String> list2 = new ArrayList<String>();
            /*for (String a:rezult){
                //list2.add(q.attr("title"));
                String str = a.attr("title").toString();
                if(str.length()>0){
                    famousEvent.add(str);
                }
            }*/
           // Elements ch = el.children();
            //doc.select("p").forEach(System.out::println);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return famousEvent;
    }

   public List<String> getRandomFamousEvent(final List<String> list, final int num) {
       Random rand = new Random();
       List<String> randomFamousEvent = new ArrayList<String>();

       int numberOfElements = num;

       for (int i = 0; i < numberOfElements; i++) {
           int randomIndex = rand.nextInt(list.size());
           String randomElement = list.get(randomIndex);
           randomFamousEvent.add(randomElement);
           list.remove(randomIndex);
       }
       return randomFamousEvent;
   }

    public boolean checkString(final String string) {
        try {
            Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
