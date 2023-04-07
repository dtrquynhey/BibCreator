package test_package;

import static basic_classes.FileManagement.*;

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
}