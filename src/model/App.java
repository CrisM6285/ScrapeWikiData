package model;
// import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// import org.jsoup.Jsoup;
// import org.jsoup.nodes.Document;
// import org.jsoup.nodes.Element;
// import org.jsoup.select.Elements;

// import model.ScrapeWikiData;


public class App {
    public static void main(String[] args) {
        Pattern p;
        Matcher m;

        // String link = "https://en.wikipedia.org/wiki/List_of_Attack_on_Titan_chapters";
        // String link = "https://en.wikipedia.org/wiki/List_of_Boruto:_Naruto_Next_Generations_chapters";
        // String link = "https://en.wikipedia.org/wiki/List_of_Bleach_chapters_(1%E2%80%93187)";
        // String link = "https://en.wikipedia.org/wiki/List_of_Bleach_chapters_(188%E2%80%93423)";
        // String link = "https://en.wikipedia.org/wiki/List_of_Hunter_%C3%97_Hunter_chapters";
        String link = "https://en.wikipedia.org/wiki/List_of_Jujutsu_Kaisen_chapters";
        // String link = "https://en.wikipedia.org/wiki/List_of_Naruto_chapters_(Part_I)";
        // String link = "https://en.wikipedia.org/wiki/List_of_Naruto_chapters_(Part_II,_volumes_28%E2%80%9348)";
        
        System.out.println("\n\nLink: " + link + "\n");
        ScrapeWikiData docThing = new ScrapeWikiData(link);

        
    }
}
