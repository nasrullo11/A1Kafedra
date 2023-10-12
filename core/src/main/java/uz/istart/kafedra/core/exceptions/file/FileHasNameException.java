package uz.istart.kafedra.core.exceptions.file;

public class FileHasNameException extends RuntimeException {

    public FileHasNameException(String fileName) {
        super(fileName);
    }
}
