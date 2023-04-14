package exception_classes;

public class FileInvalidException extends Exception{
    public FileInvalidException() {
        super("\nError: Input file cannot be parsed due to to missing information " +
                "(i.e. month={}, title={}, etc.)");
    }

    public FileInvalidException(String str_file,  String errorField) {
        super("\nError: Detected Empty Field!" +
                "\n============================" +
                "\nProblem detected with input file: " + str_file +
                "\nFile is Invalid: Field \"" + errorField + "\" is empty." +
                " Processing stopped at this point. Other empty fields may be present as well!\n");
    }
    /*
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    */
}