package basic_classes;

import java.util.ArrayList;

public class Article {
    //Attributes

    public String id, author, journal, title, volume, pages, keywords, doi, issn, month, year, number;

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
}
