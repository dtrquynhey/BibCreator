package basic_classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManagement {

    public static boolean CheckInputFiles(){
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
            PrintWriter writer = new PrintWriter(new File(str_file));
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.printf("Could not open/create file $s.", str_file);
            return false;
        }
    }

    public static void deleteCreatedFile() {
        File folder = new File(".");
        File[] listOfFiles = folder.listFiles((dir, file_name) -> file_name.endsWith(".json"));
        for (File file : listOfFiles) {
            file.delete();
        }
    }
}
