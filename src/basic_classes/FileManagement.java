package basic_classes;

import exception_classes.FileInvalidException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileManagement {


    static ArrayList<String> validStrFilesArrayList = new ArrayList<>();
    static ArrayList<String> createdJsonFilesArrayList = new ArrayList<>();


    /**
     * method checks whether there are 10 files exited, if yes return true, else return false
     * @return boolean
     */
    public static boolean checkInputFiles() {
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


    /**
     * method takes an argument of a string name of bib file and creates 3 according json files, if succeed return true,
     * else return false
     * @return boolean
     */
    public static boolean createFiles(String strFile) {
        try {
            PrintWriter writer = new PrintWriter(strFile);
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Could not open/create file " + strFile);
            return false;
        }
    }


    /**
     * method deletes all created json files
     */
    public static void deleteCreatedFile() {
        File files = new File(".");
        File[] listOfFiles = files.listFiles((dir, file_name) -> file_name.endsWith(".json"));
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                file.delete();
            }
        }
    }


    /**
     * overloaded method takes an argument of the id (1-10) of the latex file and deletes all json files with the given
     * number
     */
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


    /**
     * method
     */
    public static ArrayList<String> processFilesForValidation(File[] files) {

        for (int i = 1; i <= files.length; i++) {   // for loop iterates from latex1 to latex10
            Article.resetArticleNum();  // start counting article from 0 in the new file

            // PrintWriter for writing to json files
            PrintWriter writerIEEE = null;
            PrintWriter writerACM = null;
            PrintWriter writerNJ = null;

            // BufferReader for reading bib files
            BufferedReader reader;

            try {
                // files' name for reading and writing
                String fileName = "Latex" + i + ".bib";
                String fileNameIEEE = "IEEE" + i + ".json";
                String fileNameACM = "ACM" + i + ".json";
                String fileNameNJ = "NJ" + i + ".json";

                // initialize PrintWriter for each json type files
                writerIEEE = new PrintWriter(fileNameIEEE);
                writerACM = new PrintWriter(fileNameACM);
                writerNJ = new PrintWriter(fileNameNJ);

                // start reading bib files
                reader = new BufferedReader(new FileReader(fileName));
                String line;
                int counter;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (line.contains("@")) {   // check for the beginning of the article
                        Map<String, String> articleFields = new HashMap<>();    // map for the fields of each article
                        line = reader.readLine().trim();
                        while (line.isEmpty()) {    // if the line is empty, keep reading until it finds non-empty line
                            line = reader.readLine().trim();
                        }
                        String id = line.substring(0, line.length() - 1);   // get the id without the comma at the end
                        counter = 1;    // counter for the braces, currently is 1 for the opening brace of the article

                        // loop until the closing brace of the whole article is found
                        while (counter != 0) {
                            line = reader.readLine().trim();
                            while (line.isEmpty()) {    // if the line is empty, keep reading until it finds non-empty line
                                line = reader.readLine().trim();
                            }
                            if (line.contains("{")) {
                                counter++;  // increase counter if any opening brace of the fields is found
                            }
                            if (line.contains("}")) {
                                counter--;  // decrease counter if any closing brace of the fields is found
                                if (counter == 0) {
                                    break;
                                }
                            }
                            String[] kv = line.split("=");  // split key-value pair by "="
                            kv[0] = kv[0].trim();
                            kv[1] = kv[1].trim();
                            kv[1] = kv[1].substring(1, kv[1].length() - 2).trim(); //substring to get rid of "{" and "},"
                            articleFields.put(kv[0], kv[1]);    // add to the map

                            // when the value of a field is empty <-> when a bib file is invalid
                            // -> exception is thrown and will be handled later within this method
                            if (kv[1].isEmpty()) {
                                throw new FileInvalidException(fileName, kv[0]);
                            }

                        }
                        // create object of Article to store the read information
                        Article article = new Article(id, articleFields.get("author"), articleFields.get("journal"),
                                articleFields.get("title"), articleFields.get("volume"), articleFields.get("pages"),
                                articleFields.get("keywords"), articleFields.get("doi"), articleFields.get("ISSN"),
                                articleFields.get("month"), articleFields.get("year"), articleFields.get("number"));

                        // format all the articles within one bib file
                        String formattedInIEEE = formatIEEE(article);
                        String formattedInACM = formatACM(article);
                        String formattedInNJ = formatNJ(article);

                        // write to json files
                        writeToJson(writerIEEE, formattedInIEEE);
                        writeToJson(writerACM, formattedInACM);
                        writeToJson(writerNJ, formattedInNJ);



                    }
                }
                validStrFilesArrayList.add(fileName);
                createdJsonFilesArrayList.add(fileNameIEEE);
                createdJsonFilesArrayList.add(fileNameNJ);
                createdJsonFilesArrayList.add(fileNameACM);
                writerIEEE.close();
                writerACM.close();
                writerNJ.close();
                reader.close();

                // when a bib is invalid, all its created json files will be all deleted
            } catch (FileInvalidException e) {
                System.out.println(e.getMessage());
                writerIEEE.close();
                writerACM.close();
                writerNJ.close();
                deleteCreatedFile(i); // overloaded method is used, with argument 'i' is the id of the bib file

                // any other exceptions lead all created json files to be deleted
            } catch (IOException e) {
                System.out.println(e.getMessage());
                deleteCreatedFile();
            }
        }
        return createdJsonFilesArrayList;
    }


    /**
     * method takes a PrintWriter object and a string of a formatted article and writes this article into json file
     */
    private static void writeToJson(PrintWriter wf, String formattedArticle) {
        wf.println(formattedArticle + "\n");
    }


    /**
     * method formats an article into IEEE form
     * @return String
     */
    private static String formatIEEE(Article a) {
        // format all fields
        String strAuthor = a.getAuthor().replace(" and",",");
        String strTitle = "\""  + a.getTitle() + "\"";
        String strJournal = a.getJournal();
        String strVolume = "vol. " + a.getVolume();
        String strNumber = "no. " + a.getNumber();
        String strPages = "p. " + a.getPages();
        String strMonthAndYear = a.getMonth() + " " + a.getYear();

        return strAuthor + ". " + strTitle + ", " +  strJournal + ", " + strVolume + ", " + strNumber + ", " + strPages
                + ", " + strMonthAndYear + ".";
    }


    /**
     * method formats an article into ACM form
     * @return String
     */
    private static String formatACM(Article a) {
        // format all fields
        String StrArticleNum = "[" + Article.getArticleNum() + "]";
        String[] authors = a.getAuthor().split(" and ");
        String strAuthors = authors[0] + " et al";
        String strYear = a.getYear();
        String strTitle = a.getTitle();
        String strJournal = a.getJournal();
        String strVolume = a.getVolume();
        String strNumAndYear = a.getNumber() + " (" + strYear + ")";
        String strPages = a.getPages();
        String strDOI =  "DOI:https://doi.org/"+ a.getDoi();

        return StrArticleNum + "\t\t" + strAuthors + ". " + strYear + ". " + strTitle + ". " + strJournal + ". " +
                strVolume + ", " + strNumAndYear + ", " + strPages + ". " + strDOI + ".";
    }


    // method formats an article into NJ form
    private static String formatNJ(Article a) {
        // format all fields
        String strAuthor = a.getAuthor().replace("and","&");
        String strTitle = a.getTitle();
        String strJournal = a.getJournal();
        String strVolume = a.getVolume();
        String strPagesAndYear = a.getPages() + "(" + a.getYear() + ")";

        return strAuthor + ". " + strTitle + ". " +  strJournal + ". " + strVolume + ", " + strPagesAndYear + ".";
    }


    //
    public static String getResultAfterProcess() {
        return String.format("A total of %d files were invalid, and could not be processed. All other %d \"Valid\" files have been created.",
                (10-validStrFilesArrayList.size()), validStrFilesArrayList.size());
    }

    public static String displayJsonFile(String strFile) {
        BufferedReader bufferedReader;
        String line;
        StringBuilder allLines = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new FileReader(strFile));
            while ((line = bufferedReader.readLine()) != null) {
                allLines.append(line).append("\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            return e.getMessage();
        }
        return allLines.toString();
    }
}