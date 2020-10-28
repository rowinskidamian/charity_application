package pl.damianrowinski.charity.exceptions;

public class ObjectNotFoundApiException extends RuntimeException {
    public ObjectNotFoundApiException(String message) {
        super(message);
    }
}
