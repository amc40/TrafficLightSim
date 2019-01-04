package Exceptions;

public class InvalidRoadConnectionException extends Exception {
    private static final String ERR_MSG = "Can't connect road segments";

    @Override
    public String getMessage() {
        return ERR_MSG + "\n" + super.getMessage();
    }
}
