package Exceptions;

public class SamePieceException extends InvalidRoadConnectionException {
    private static final String ERR_MSG = "Can't connect to same segment.";

    @Override
    public String getMessage() {
        return ERR_MSG + "\n" + super.getMessage();
    }
}
