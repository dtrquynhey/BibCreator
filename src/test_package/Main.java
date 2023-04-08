package test_package;

import basic_classes.Article;
import exception_class.FileInvalidException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static basic_classes.FileManagement.*;

public class Main {
    public static void main(String[] args) {

        File[] allFiles = new File[10];
        System.out.println("Welcome to BibCreator!");
        if (CheckInputFiles()) {
            for (int i = 1; i <= 10; i++) {
                if (!(createFiles("IEEE" + i + ".json") &&
                        createFiles("ACM" + i + ".json") &&
                        createFiles("NJ" + i + ".json"))) {
                    deleteCreatedFile();
                }
            }
            processFilesForValidation(allFiles);
        }


    }
}