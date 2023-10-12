package uz.istart.kafedra.core.exceptions.file;

public class FileMustDoesNotEmptyException extends RuntimeException {
    public FileMustDoesNotEmptyException(String message) {
        super(message);
    }
}
