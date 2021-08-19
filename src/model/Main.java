package model;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        String link;

        // ** if
        link = "https://en.wikipedia.org/wiki/List_of_Attack_on_Titan_chapters";
        // link = "https://en.wikipedia.org/wiki/List_of_Boruto:_Naruto_Next_Generations_chapters";
        // link = "https://en.wikipedia.org/wiki/List_of_Dragon_Ball_Super_chapters";
        // link = "https://en.wikipedia.org/wiki/List_of_My_Hero_Academia_chapters";
        // link = "https://en.wikipedia.org/wiki/List_of_Naruto_chapters_(Part_I)";
        // link = "https://en.wikipedia.org/wiki/List_of_Naruto_chapters_(Part_II,_volumes_28%E2%80%9348)";
        // link = "https://en.wikipedia.org/wiki/List_of_Naruto_chapters_(Part_II,_volumes_49%E2%80%9372)";
        // link = "https://en.wikipedia.org/wiki/List_of_One-Punch_Man_chapters";
        // link = "https://en.wikipedia.org/wiki/List_of_The_Promised_Neverland_chapters";
        // link = "https://en.wikipedia.org/wiki/List_of_Tokyo_Ghoul_chapters";

        // ** else - if
        // link = "https://en.wikipedia.org/wiki/List_of_Death_Note_chapters";
        // link = "https://en.wikipedia.org/wiki/List_of_Demon_Slayer:_Kimetsu_no_Yaiba_chapters";
        // link = "https://en.wikipedia.org/wiki/List_of_Jujutsu_Kaisen_chapters";
        // link = "https://en.wikipedia.org/wiki/List_of_One_Piece_chapters_(1%E2%80%93186)";
        // link = "https://en.wikipedia.org/wiki/List_of_One_Piece_chapters_(187%E2%80%93388)";
        
        // ** else - else
        // link = "https://en.wikipedia.org/wiki/List_of_Bleach_chapters_(1%E2%80%93187)";
        // link = "https://en.wikipedia.org/wiki/List_of_Bleach_chapters_(188%E2%80%93423)";
        // link = "https://en.wikipedia.org/wiki/List_of_Bleach_chapters_(424%E2%80%93686)";
        // link = "https://en.wikipedia.org/wiki/List_of_Dragon_Ball_chapters_(series)";
        // link = "https://en.wikipedia.org/wiki/List_of_Dragon_Ball_Z_chapters";
        // link = "https://en.wikipedia.org/wiki/List_of_Hunter_%C3%97_Hunter_chapters";
        // link = "https://en.wikipedia.org/wiki/List_of_The_Seven_Deadly_Sins_chapters";
        
        Date start = new Date();

        
        System.out.println("\n\nLink: " + link + "\n");
        ScrapeWikiData docThing = new ScrapeWikiData(link);

        // System.out.println("                       getURL(): " + docThing.getURL());
        System.out.println("             getVolumeNumbers(): " + docThing.getVolumeNumbers());
        // System.out.println("      getVolumeNumbers().size(): " + docThing.getVolumeNumbers().size());
        // System.out.println("              getTotalVolumes(): " + docThing.getTotalVolumes());
        // System.out.println("getAllVolChapterTitles().size(): " + docThing.getAllVolChapterTitles().size());
        // System.out.println("    getAllVolChapterKs().size(): " + docThing.getAllVolChapterKs().size());
        // System.out.println();
        System.out.println(" getAllNumOfChaptersPerVolume(): " + docThing.getAllNumOfChaptersPerVolume());
        System.out.println();
        System.out.println();
        System.out.println(" getVolChpTitles(): " + docThing.getVolChpTitles(38));
        System.out.println("     getVolChpKs(): " + docThing.getVolChpKs(38));
        System.out.println();

        Date end = new Date();
        totalTime(start, end);
    }

    public static void totalTime(Date start, Date end) {
        long diff = end.getTime() - start.getTime();
        long min = diff / (1000 * 60);
        long sec = diff / (1000);
        long ms = diff % 1000;
        System.out.println("\n\nTime: " + min + " min, " + sec + " sec, " + ms + " ms");
    }
}
