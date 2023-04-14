package exception_classes;

public class FileNotFoundException extends Exception {
    public FileNotFoundException() {
        super("\nCould not open input file. File does not exist; possibly it could not be created.");
    }
}
