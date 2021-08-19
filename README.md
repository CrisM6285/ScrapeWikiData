# ScrapeWikiData
This script is able to scrape manga chapter details from Wikipedia.

## Public Methods
```java
// constructor
ScrapeWikiData(String url);

// Returns url provided in constructor
String getURL();

// Returns the total number of volumes
int getTotalVolumes();

// Returns all volume numbers [1, 2, 3, ..., n]
ArrayList<Integer> getVolumeNumbers();

// Returns all chapter titles
ArrayList<String> getAllVolChapterTitles();

// Returns chapter titles in specified volume
ArrayList<String> getVolChpTitles(int vol);

// Returns all chapter numbers [1, 2, 3, ..., n]
ArrayList<String> getAllVolChapterKs();

// Returns chapter numbers in specified volume
ArrayList<String> getVolChpKs(int vol);

// Returns total number of chapters in all volumes (AOT example: [4, 5, 4, ..., 5])
ArrayList<Integer> getAllNumOfChaptersPerVolume();
```

## Packages used
- [jsoup](https://jsoup.org/) (manipulate HTML elements)
- java.util.regex.[Matcher/Pattern] (extract chapter numbers/titles from string)

## Usage
Run Main.java with a manga Wikipedia page in the constructor, and call any of the above methods of your choice.