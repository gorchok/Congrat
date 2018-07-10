package ru.pochivalin.congrat.services;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class ImageServiceImpl {

    public void downloadImage() {
        List<String> images = new ArrayList<String>();
        try {
            String strURL = "https://yandex.ru/images/search?text=%D1%81%20%D0%B4%D0%BD%D0%B5%D0%BC%20%D1%80%D0%BE%D0%B6%D0%B4%D0%B5%D0%BD%D0%B8%D1%8F";
            Document doc = Jsoup.connect(strURL).get();
            Elements clazz = doc.getElementsByClass("serp-list serp-list_type_search serp-list_unique_yes serp-list_rum_yes serp-list_justifier_yes serp-controller__list counter__reqid clearfix i-bem serp-list_js_inited");

            Elements imageElements = doc.select("img");

            for (Element imageElement : imageElements) {

                //make sure to get the absolute URL using abs: prefix
                String strImageURL = imageElement.attr("abs:src");

                //download image one by one
                //downloadImage(strImageURL);
                images.add(strImageURL);
            }

            String urlImage = images.get(5);
            URL urlImg = new URL(urlImage);
                InputStream in = urlImg.openStream();

                byte[] buffer = new byte[4096];
                int n = -1;

                OutputStream os = new FileOutputStream("_qwe.jpg");

                //write bytes to the output stream
                while ((n = in.read(buffer)) != -1) {
                    os.write(buffer, 0, n);
                }

                //close the stream
                os.close();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
