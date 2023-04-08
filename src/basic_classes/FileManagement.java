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

        Article.clearArticleList();

        for (int i = 1; i <= files.length; i++) {
            try {
                String fileName = "Latex" + i + ".bib";
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line;
                int counter;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();


                    if (line.contains("@")) {
                        Map<String, String> articleFields = new HashMap<>();
                        line = reader.readLine().trim();
                        while (line.isEmpty()) {
                            line = reader.readLine().trim();
                        }
                        String id = line.substring(0, line.length() - 1);
                        counter = 1;

                        while (counter != 0) {
                            line = reader.readLine().trim();

                            while (line.isEmpty()) {
                                line = reader.readLine().trim();
                            }

                            if (line.contains("{")) {
                                counter++;
                            }
                            if (line.contains("}")) {
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

                    }
                }
                reader.close();
            } catch (FileInvalidException e) {
                System.out.println(e.getMessage());
                deleteCreatedFile(i);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                deleteCreatedFile();
            }
            //int fileNum = i;
            //writeToIEEE(i, );
        }
    }

    private static void writeToIEEE(int num, ArrayList<Article> articles) {
        String file_str = "IEEE" + num + ".json";
        try {
            PrintWriter writer = new PrintWriter(file_str);
            for (Article a : articles) {
                String article = formatIEEE(a);
                writer.println(article);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String formatIEEE(Article a) {
        return "";
    }

}
