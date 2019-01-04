package Exceptions;

public class TrafficFlowConnectionException extends InvalidRoadConnectionException {
    private static final String ERR_MSG = "Trafficflow between points is not valid";

    @Override
    public String getMessage() {
        return ERR_MSG + "\n" + super.getMessage();
    }
}
