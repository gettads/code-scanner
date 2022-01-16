package scanner.exception;

public class GeneralScanException extends Exception {
    public GeneralScanException(String message)
    {
        super("The process of file's scanning is corrupted: " + message);
    }
}
