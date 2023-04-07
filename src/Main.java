import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to BibCreator!");
        if (CheckInputFiles()) {
            for (int i = 1; i <= 10; i++) {
                if (!(createFiles("IEEE"+ i + ".json") &&
                        createFiles("ACM"+ i + ".json") &&
                        createFiles("NJ"+ i + ".json"))) {
                    deleteCreatedFile();
                }
            }
        }


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
}