package cz.muni.fi.parser;

/**
 * Created by vacullik on 22/02/2017.
 */
public class InvalidJsonFormatException extends RuntimeException {

    public InvalidJsonFormatException(String msg) {
        super(msg);
    }

    public InvalidJsonFormatException(String msg, Exception cause) {
        super(msg, cause);
    }
}
