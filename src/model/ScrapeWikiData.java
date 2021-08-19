package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;



public class ScrapeWikiData {
    private String url;
    private Element wikiTable;
    
    private String chpSel;
    private int totalVolumes;
    private ArrayList<Integer> volumeNumbers = new ArrayList<>();

    private int chpSelOption;
    private ArrayList<String> volChapterKs = new ArrayList<>();
    private ArrayList<String> volChapterTitles = new ArrayList<>();
    private ArrayList<Integer> numOfChaptersPerVolume = new ArrayList<>();

    public ScrapeWikiData(String url) {
        this.url = url;
        setWikiTable();
        setVolumeNumbers();
        setChapterSelector();
        setChapterDetails();
    }


    private void setChapterSelector() {
        ArrayList<String> chapterSelectors = new ArrayList<>();
        chapterSelectors.add("td ol");
        chapterSelectors.add("td > ul");
        chapterSelectors.add("tr[style*='vertical-align: top;']:first-child");
        for(int i = 1; i < chapterSelectors.size()+1; i++) {
            if( this.wikiTable.select(chapterSelectors.get(i-1)).size() == totalVolumes ) {
                this.chpSel = chapterSelectors.get(i-1);
                // System.out.println("Chose option (" + (i) + "): " + chpSel);
                this.chpSelOption = i;
                return;
            }
        }
        System.out.println("No chapter selector found");
    }
    

    public String getURL() {
        return url;
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

    
    public ArrayList<String> getAllVolChapterTitles() {
        return volChapterTitles;
    }
    public ArrayList<String> getAllVolChapterKs() {
        return volChapterKs;
    }
    public ArrayList<Integer> getAllNumOfChaptersPerVolume() {
        return numOfChaptersPerVolume;
    }
    public ArrayList<String> getVolChpTitles(int vol) {
        return getVolumeDetails(volChapterTitles, vol);
    }
    public ArrayList<String> getVolChpKs(int vol) {
        return getVolumeDetails(volChapterKs, vol);
    }


    private void setWikiTable() {
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


    private ArrayList<String> getVolumeDetails(ArrayList<String> itemSet, int vol) {
        if(vol < volumeNumbers.get(0) || vol > volumeNumbers.get(totalVolumes-1)) throw new IllegalArgumentException("Volume " + vol + " is not in set of volumes.");
        int indexOfVol = binarySearchIndexOfVol(vol);
        ArrayList<String> itemToReturn = new ArrayList<>();
        int indexChpStart = 0;
        for(int i = 0; i < indexOfVol; i++) indexChpStart += numOfChaptersPerVolume.get(i);
        for(int i = indexChpStart; i < indexChpStart+numOfChaptersPerVolume.get(indexOfVol); i++) itemToReturn.add( itemSet.get(i) );
        return itemToReturn;
    }

    private int binarySearchIndexOfVol(int vol) {
        int low = 0;
        int high = totalVolumes-1;
        int mid;
        while(low <= high) {
            mid = (low + high) / 2;
            if(volumeNumbers.get(mid) == vol) {
                return mid;
            } else if(volumeNumbers.get(mid) > vol) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }


    private void setChapterDetails() {
        boolean hasLiValue = false;
        if(chpSelOption == 1) {
            if( wikiTable.select(chpSel).get(0).getElementsByAttribute("value").first() != null ) {
                hasLiValue = true;
                // System.out.println( "Has 'value' key" );
            }
            else if( !wikiTable.select(chpSel).get(0).attributes().hasKey("start") && wikiTable.select(chpSel).get(1).attributes().hasKey("start")) {
                wikiTable.select(chpSel).get(0).attributes().put("start", "1");
                // System.out.println( "Has 'start' keyyyyy" );
            }
        }
        Pattern p;
        Matcher m;
        // int volStart = volumeNumbers.get(0);
        String chpRow;
        String chpK;
        String chpTitle;

        if(chpSelOption == 1) {
            // System.out.println("\n** if\n\n");

            for(Element vol : wikiTable.select(chpSel)) {
                // System.out.println( "Volume " + volStart );
                int chpStart = (hasLiValue) ? Integer.parseInt( vol.getElementsByAttribute("value").attr("value") ) : Integer.parseInt( vol.attributes().get("start") );
                int numOfChps = 0;

                for(Element chpRowItem : vol.select("li")) {
                    chpRow = chpRowItem.text().strip();

                    if(chpRow.isEmpty()) continue;
                    // System.out.println("Test: " + chpRow);
                    p = Pattern.compile("^\"(.+?)[\"|(]");
                    m = p.matcher(chpRow);
                    if(m.find()) {
                        chpK = String.format("%1$3s", chpStart);
                        chpTitle = m.group(1).strip();

                        volChapterKs.add(chpK);
                        volChapterTitles.add(chpTitle);
                        // System.out.println( "    " + chpK + ": " + chpTitle );
                    }
                    else {
                        System.out.println("******* " + String.format("%1$3s", chpStart) + " was skipped *******");
                    }
                    chpStart++;
                    numOfChps++;
                }
                numOfChaptersPerVolume.add(numOfChps);
                // volStart++;
                // System.out.println();
            }
        }
        else {
            if(wikiTable.select(chpSel).get(0).select("ol").size() == 2) {
                // System.out.println("\n** else - if\n\n");

                for(Element vol : wikiTable.select(chpSel)) {
                    // System.out.println( "Volume " + volStart );
                    int chpStart = Integer.parseInt( vol.select("ol").first().attr("start") );
                    int numOfChps = 0;

                    for( Element chpRowItem : vol.select("li") ) {
                        chpRow = chpRowItem.text().strip();
                        if(chpRow.isEmpty()) continue;
                        p = Pattern.compile("\"(.+?)[\"|(]");
                        m = p.matcher(chpRow);
                        if(m.find()) {
                            chpK = String.format("%1$3s", chpStart);
                            chpTitle = m.group(1).strip();

                            volChapterKs.add(chpK);
                            volChapterTitles.add(chpTitle);
                            // System.out.println( "    " + chpK + ": " + chpTitle);
                        }
                        else {
                            System.out.println("******* " + chpStart + " was skipped *******");
                        }
                        chpStart++;
                        numOfChps++;
                    }
                    numOfChaptersPerVolume.add(numOfChps);
                    // volStart++;
                    // System.out.println();
                }
            }
            else {
                // System.out.println("\n** else - else\n\n");

                for(Element vol : wikiTable.select(chpSel)) {
                    // System.out.println( "Volume " + volStart );
                    int numOfChps = 0;

                    for( Element chpRowItem : vol.select("li") ) {
                        chpRow = chpRowItem.text().strip();
                        if(chpRow.isEmpty()) continue;
                        p = Pattern.compile("^(.+?)[:|\\.] \"(.+?)[\"|(]");
                        m = p.matcher(chpRow);
                        if(m.find()) {
                            chpK = String.format("%1$3s", m.group(1));
                            chpTitle = m.group(2).strip();

                            volChapterKs.add(chpK);
                            volChapterTitles.add(chpTitle);
                            // System.out.println( "    " + chpK + ": " + chpTitle );
                        }
                        else {
                            System.out.println("******* skipped *******");
                            System.out.println(chpRow);
                            System.out.println("******* skipped *******");
                        }
                        numOfChps++;
                    }
                    numOfChaptersPerVolume.add(numOfChps);
                    // volStart++;
                    // System.out.println();
                }
            }
        }
    }
}
