package edu.ycce.rssreader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.regex.*;

public class RegEx{

    private static final String PATTERN = "(<img.*?/>)|(<a.*?/a>)";
    private static final String PATTERN1 = "htt(p|ps)://.*?\\.(jpg|JPEG|jpeg|JPG|png|PNG)['\"]";
    private static final String REPLACEMENT = "";
    private static final Pattern COMPILED_PATTERN = Pattern.compile(PATTERN,  Pattern.CASE_INSENSITIVE);
    private static final Pattern COMPILED_PATTERN1 = Pattern.compile(PATTERN1,  Pattern.CASE_INSENSITIVE);

    public static String replaceMatches(String html){
        Matcher matcher = COMPILED_PATTERN.matcher(html);
        html = matcher.replaceAll(REPLACEMENT);
        return html;
    }

    public static String findImage(String html) {
        Document doc = Jsoup.parse(html);
        Elements images = doc.select("img");
        String src = images.first().attr("src");
        return src;
    }
}
