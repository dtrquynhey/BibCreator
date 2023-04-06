import java.io.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to BibCreator!");

        //Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= 10; i++) {
            String str_file = "Latex" + i + ".bib";
            File myFile = new File(str_file);
            if (!myFile.exists()) {
                System.out.printf("Could not open input file %s for reading.", str_file);
                System.out.println("\nPlease check if file exists! Program will terminate after closing any opened file");
                break;
            } else {
                String ieeeFile = "IEEE" + i + ".json";
                String acmFile = "ACM" + i + ".json";
                String njFile = "NJ" + i + ".json";
                if (!(createFile(ieeeFile) && createFile(acmFile) && createFile(njFile))) {
                    deleteCreatedFile();
                }
            }
        }
    }

    public static boolean createFile(String str_file) {
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
        File[] listOfFiles = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        });
        for (File file : listOfFiles) {
            file.delete();
        }
    }
}