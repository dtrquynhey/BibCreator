package basic_classes;

import exception_class.FileInvalidException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileManagement {

    static File[] allFiles = new File[10];

    public static boolean CheckInputFiles() {
        Scanner scanner = null;
        String str_file = null;

        for (int i = 1; i <= 10; i++) {
            try {
                str_file = "Latex" + i + ".bib";
                File myFile = new File(str_file);
                scanner = new Scanner(myFile);
                //add to the static array in FileManagement

            } catch (FileNotFoundException e) {
                System.out.printf("Could not open input file %s for reading.", str_file);
                System.out.println("\nPlease check if file exists! Program will terminate after closing any opened file");
                return false;

            } finally {
                if (scanner != null) {
                    scanner.close();
                }
            }
        }
        return true;
    }

    public static boolean createFiles(String str_file) {
        try {
            PrintWriter writer = new PrintWriter(str_file);
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Could not open/create file " + str_file);
            return false;
        }
    }

    public static void deleteCreatedFile(int num) {
        File files = new File(".");
        String strNum = Integer.toString(num);
        File[] listOfFiles = files.listFiles((dir, file_name) -> file_name.endsWith(strNum + ".json"));
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                file.delete();
            }
        }
    }

    public static void deleteCreatedFile() {
        File files = new File(".");
        File[] listOfFiles = files.listFiles((dir, file_name) -> file_name.endsWith(".json"));
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                file.delete();
            }
        }
    }

    public static void processFilesForValidation(File[] files) {
        for (int i = 1; i <= files.length; i++) {
            Article.clearArticleList();
            PrintWriter writerIEEE = null;
            BufferedReader reader = null;
            try {
                // files name for reading and writing
                String fileName = "Latex" + i + ".bib";
                String fileNameIEEE = "IEEE" + i + ".json";
                String fileNameACM = "ACM" + i + ".json";
                String fileNameNJ = "NJ" + i + ".json";

                //Writing json files
                 writerIEEE = new PrintWriter(fileNameIEEE);
//                PrintWriter writerACM = new PrintWriter(fileNameACM);
//                PrintWriter writerNJ = new PrintWriter(fileNameNJ);

                //Reading bib files
                reader = new BufferedReader(new FileReader(fileName));
                String line;
                int counter;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (line.contains("@")) {     //Check for the beginning of the article
                        Map<String, String> articleFields = new HashMap<>(); //Map for the fields of bib
                        line = reader.readLine().trim();
                        while (line.isEmpty()) {  //If the line is empty, keep reading until it finds not empty line
                            line = reader.readLine().trim();
                        }
                        String id = line.substring(0, line.length() - 1); // get the id without the comma at the end
                        counter = 1; // Counter for the Braces , currently is one for the opening Brace of the article

                        // loop until it finds the closing Brace for the whole article
                        while (counter != 0) {
                            line = reader.readLine().trim();
                            while (line.isEmpty()) {  //If the line is empty, keep reading until it finds not empty line
                                line = reader.readLine().trim();
                            }
                            if (line.contains("{")) {  // Increase counter if there's opening Brace
                                counter++;
                            }
                            if (line.contains("}")) { // decrease counter if there's opening Brace
                                counter--;
                                if (counter == 0) {
                                    break;
                                }
                            }
                            String[] kv = line.split("=");
                            kv[0] = kv[0].trim();
                            kv[1] = kv[1].trim();
                            kv[1] = kv[1].substring(1, kv[1].length() - 2).trim();
                            articleFields.put(kv[0], kv[1]);
                            if (kv[1].isEmpty()) {
                                throw new FileInvalidException(fileName, kv[0]);
                            }

                        }
                        Article article = new Article(id, articleFields.get("author"), articleFields.get("journal"),
                                articleFields.get("title"), articleFields.get("volume"), articleFields.get("pages"),
                                articleFields.get("keywords"), articleFields.get("doi"), articleFields.get("ISSN"),
                                articleFields.get("month"), articleFields.get("year"), articleFields.get("number"));
                        Article.allArticlesInFile.add(article);
                        String formattedInIEEE = formatIEEE(article);
                        writeToJson(writerIEEE,formattedInIEEE);



                    }
                }
                writerIEEE.close();
                reader.close();
            } catch (FileInvalidException e) {
                System.out.println(e.getMessage());
                writerIEEE.close();
                deleteCreatedFile(i);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                deleteCreatedFile();
            }



        }
    }

    private static void writeToJson(PrintWriter wf, String formattedArticle) {
       wf.println(formattedArticle + "\n");
    }

    private static String formatIEEE(Article a) {
        //Add author
        String strAuthor = a.getAuthor().replace(" and",",");

        //Add title
        String strTitle = "\""  + a.getTitle() + "\"";

        //Add journal
        String strJournal = a.getJournal();

        //Add volume
        String strVolume = "vol. " + a.getVolume();

        //Add Number
        String strNumber = "no. " + a.getNumber();

        //add pages
        String strPages = "p. " + a.getPages();

        //Add month
        String strMonthAndYear = a.getMonth() + " " + a.getYear();


        return strAuthor + ". " + strTitle + ", " +  strJournal + ", " + strVolume + ", " + strNumber + ", " + strPages
                + ", " + strMonthAndYear + ".";
        }
    private static String formatACM(Article a, int aNum) {
        //Add Article Number
        String StrarticleNum = "[" + aNum + "]";

        //Add Author
        String[] authors = a.getAuthor().split(" and ");
        String strAuthors = authors[0] + " et al";

        //Add Year
        String strYear = a.getYear();

        //Add Title
        String strTitle = a.getTitle();

        //Add Journal
        String strJournal = a.getJournal();

        //Add Volume
        String strVolume = a.getVolume();

        //Add number and Year
        String strNumAndYear = a.getNumber() + " (" + strYear + ")";

        //Add pages
        String strPages = a.getPages();

        //Add DOI
        String strDOI =  "DOI:https://doi.org/"+ a.getDoi();

        return StrarticleNum + "\t" + strAuthors + ". " + strYear + ". " + strTitle + ". " + strJournal + ". " +
                strVolume + ", " + strNumAndYear + ", " + strPages + ". " + strDOI;
    }

}
