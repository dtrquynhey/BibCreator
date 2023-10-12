package test_package;

import exception_classes.FileNotFoundException;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import static basic_classes.FileManagement.*;

public class BibCreator {
    public static void main(String[] args) {

        File[] allFiles = new File[10];
        ArrayList<String> createdJsonFiles = null;
        System.out.println("Welcome to BibCreator!");

        // file processing
        if (checkInputFiles()) {
            for (int i = 1; i <= 10; i++) {
                if (!(createFiles("IEEE" + i + ".json") &&
                        createFiles("ACM" + i + ".json") &&
                        createFiles("NJ" + i + ".json"))) {
                    deleteCreatedFile();
                }
            }
            createdJsonFiles = processFilesForValidation(allFiles);
            System.out.println(getResultAfterProcess());
        }

        Scanner scanner = new Scanner(System.in);
        String reviewFileName;

        for (int i=0; i < 2; i++){
            System.out.print("Please enter the name of one of the files that you need to review: ");
            reviewFileName = scanner.nextLine();
            try {
                if (createdJsonFiles != null && !createdJsonFiles.contains(reviewFileName)) {
                    throw new FileNotFoundException();
                }
                System.out.println("Here are the contents of the successfully created Json File: " + reviewFileName);
                System.out.println(displayJsonFile(reviewFileName));
                System.out.println("\nGoodbye! Hope you have enjoyed creating the needed files using BibCreator.");
                break;
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                if (i < 1){
                    System.out.println("However you will be allowed another chance to enter another file name.");
                } else {
                    System.out.println("\nSorry, I am unable to display your desired files! Program will exit!");
                }
            }
        }
    }
}
