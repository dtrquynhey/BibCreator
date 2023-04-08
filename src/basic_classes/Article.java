package basic_classes;

import java.util.ArrayList;

public class Article {
    //Attributes

    private String id, author, journal, title, volume, pages, keywords, doi, issn, month, year, number;

    public static ArrayList<Article> allArticlesInFile = new ArrayList<>();

    public Article(String id, String author, String journal, String title, String volume,
                   String pages, String keywords, String doi, String issn, String month,
                   String year, String number) {
        this.id = id;
        this.author = author;
        this.journal = journal;
        this.title = title;
        this.volume = volume;
        this.pages = pages;
        this.keywords = keywords;
        this.doi = doi;
        this.issn = issn;
        this.month = month;
        this.year = year;
        this.number = number;

    }

    public static boolean validateAnArticle() {

        return true;
    }

    public static void clearArticleList() {
        Article.allArticlesInFile.clear();
    }


    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getJournal() {
        return journal;
    }

    public String getTitle() {
        return title;
    }

    public String getVolume() {
        return volume;
    }

    public String getPages() {
        return pages;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getDoi() {
        return doi;
    }

    public String getIssn() {
        return issn;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getNumber() {
        return number;
    }
}


