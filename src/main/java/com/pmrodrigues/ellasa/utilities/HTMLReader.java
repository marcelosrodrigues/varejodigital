package com.pmrodrigues.ellasa.utilities;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class HTMLReader {

    public String getElement(final String id, final URL uri)
            throws IOException, ParserConfigurationException {

        Document document = Jsoup.parse(this.getHTML(uri));
        document.getElementsByAttributeValue("alt", "Ico_itau").attr("src",
                "/ellasa/images/ico_itau.png");
        return document.getElementById(id).html();

    }

    private String getHTML(final URL uri) throws IOException {
        StringBuffer html = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                (InputStream) uri.getContent()))) {

            String s = null;
            while ((s = reader.readLine()) != null) {
                html.append(s);
            }

        }

        return html.toString();
    }
}
