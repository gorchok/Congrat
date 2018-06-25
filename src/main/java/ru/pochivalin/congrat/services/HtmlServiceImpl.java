package ru.pochivalin.congrat.services;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class HtmlServiceImpl {

    public List<String> getFamousEvent(String url,int day, int month)
    {
        List<String> famousEvent = new ArrayList<String>();
        try {
            //doc = Jsoup.connect("http://www.calend.ru/events/6-18/").get();
            //String URL = url + "/" + month + "-" + day + "/";
            String URL = url + "/" + month + "/" + day ;
            Document doc = Jsoup.connect(URL).get();

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
            for (int i=0; i<parts.length; i++){
                String number = null;
                String elTrim = parts[i].trim();

                if(elTrim.length() >= 4) {
                    number = elTrim.substring(0, 4);
                }

                if(elTrim.length() < 4) {
                    number=elTrim.substring(0,elTrim.length()-1);
                }

                if (checkString(number)) {
                        famousEvent.add(elTrim + ". ");
                        //k++;
                }
                else {
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
            e.printStackTrace();
        }
        return famousEvent;
    }

   public List<String> getRandomFamousEvent (List<String> list, int num) {
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

    public boolean checkString(String string) {
        try {
            Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
