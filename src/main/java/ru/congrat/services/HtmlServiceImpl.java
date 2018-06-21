package ru.congrat.services;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class HtmlServiceImpl {

    private Document doc;


    public List<String> getFamousEvent(String url,int day, int month, String tag)
    {
        List<String> famousEvent = new ArrayList<String>();
        try {
            //doc = Jsoup.connect("http://www.calend.ru/events/6-18/").get();
            String URL = url + "/" + month + "-" + day + "/";
            doc = Jsoup.connect(URL).get();

            //Elements ee = doc.getElementsByClass("famous-date plusyear");
            //Elements eee = ee.html("title");

            //Element el = doc.getElementById("content");
            Elements child = doc.getElementsByTag(tag);
            //List<String> list = new ArrayList<String>();
            //List<String> list2 = new ArrayList<String>();
            for (Element q:child){
                //list2.add(q.attr("title"));
            if((q.toString().contains("/holidays/") && q.toString().contains("день"))
             ||(q.toString().contains("/holidays/") && q.toString().contains("День")) ){
                String str = q.text();
                famousEvent.add(str);
            }
                System.out.println(q);
            }
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

}
