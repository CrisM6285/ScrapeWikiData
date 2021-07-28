package model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ScrapeWikiData {
    private String url;
    private Element wikiTable;
    
    private String chpSel;
    private int chpSelOption;
    private ArrayList<Integer> chpStarters = new ArrayList<>();
    private int totalVolumes;
    private ArrayList<Integer> volumeNumbers = new ArrayList<>();
    // private ArrayList<String> volumeTitles = new ArrayList<>();
    private ArrayList<Integer> chapterNumbers = new ArrayList<>();

    public ScrapeWikiData(String url) {
        this.url = url;
        setWikiTable();
        setVolumeNumbers();
        setChapterSelector();
        // setVolumeTitles();
        setChapterNumbers();
    }

    public int getChpSelOption() {
        return chpSelOption;
    }
    private void setChapterSelector() {
        ArrayList<String> chapterSelectors = new ArrayList<>();
        chapterSelectors.add("td ol");
        chapterSelectors.add("td > ul");
        chapterSelectors.add("tr[style*='vertical-align: top;']:first-child");
        for(int i = 1; i < chapterSelectors.size()+1; i++) {
            if( this.wikiTable.select(chapterSelectors.get(i-1)).size() == totalVolumes ) {
                this.chpSel = chapterSelectors.get(i-1);
                System.out.println("Chose option (" + (i) + "): " + chpSel);
                this.chpSelOption = i;
                return;
            }
        }
        System.out.println("No chapter selector found");
    }
    

    public String getURL() {
        return url;
    }


    public Element getWikiTable() {
        return wikiTable;
    }
    public void setWikiTable() {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Could not connect to url: " + url);
            return;
        }
        this.wikiTable = doc.selectFirst(".wikitable");
    }


    public int getTotalVolumes() {
        return totalVolumes;
    }
    private void setTotalVolumes() {
        this.totalVolumes = volumeNumbers.size();
    }


    public ArrayList<Integer> getVolumeNumbers() {
        return volumeNumbers;
    }
    private void setVolumeNumbers() {
        for(Element e : wikiTable.select("th[id*='vol']")) {
            this.volumeNumbers.add(Integer.parseInt(e.ownText()));
        }
        if( url.contains("Boruto") ) {
            volumeNumbers.remove(volumeNumbers.size()-1);
        }
        setTotalVolumes();
    }

    // public ArrayList<String> getVolumeTitles() {
    //     return volumeTitles;
    // }
    // private void setVolumeTitles() {
    //     Elements volumElements = wikiTable.select("td[style='text-align: left;']");

    //     for(Element e : volumElements) {
    //         System.out.println(e.ownText());
    //     }
    // }


    public ArrayList<Integer> getChapterNumbers() {
        return chapterNumbers;
    }
    private void setChapterNumbers() {
        boolean hasLiValue = false;
        if(chpSelOption == 1) {
            System.out.println("1st element: " + wikiTable.select(chpSel).get(0).getElementsByAttribute("value").attr("value"));
            // System.out.println("1st element attr: " + wikiTable.select(chpSel).get(0).attributes());
            // System.out.println("2nd element attr: " + wikiTable.select(chpSel).get(1).attributes());
            if( wikiTable.select(chpSel).get(0).getElementsByAttribute("value").first() != null ) {
                System.out.println( "Has 'value' key" );
                hasLiValue = true;
            }
            else {
                if( !wikiTable.select(chpSel).get(0).attributes().hasKey("start") && wikiTable.select(chpSel).get(1).attributes().hasKey("start")) {
                    wikiTable.select(chpSel).get(0).attributes().put("start", "1");
                }
                System.out.println( "Has 'start' keyyyyy" );
            }
        }

        if(chpSelOption == 1) {
            for(Element ele : wikiTable.select(chpSel)) {
                String chpStart = (hasLiValue) ? ele.getElementsByAttribute("value").attr("value") : ele.attributes().get("start");
                chpStarters.add( Integer.parseInt(chpStart) );
                System.out.println( "Chp Start: " + chpStart );
            }
        }
        // for(Element e : wikiTable.select("th[id*='vol']")) {
        //     // chapterNumbers.add(Integer.parseInt(e.ownText()));
        //     System.out.print(e.ownText() + " ");
        // }
        System.out.println();
    }




}
