public class Car {
    private Driver driver;
    private CarGraphic graphic;
    //length of car in metres
    private double length;
    //width of car in metres
    private double width;
    //max Acceleration of can in kmph per second
    private double maxAcceleration;
    //maximum speed of car in kmph
    private double maxSpeed;
    //current acceleration in kmph
    private double currentAcceleration;
    //current Speed Of Car in kmph
    private double currentSpeed;
    //maxAngle of wheels in a turn in degrees
    private double maxTurnAngle;
    //angle of car from top of screen in degrees
    private double currentAngle;
    //distance in X in metres from left of display
    private double xDistance;
    //distance in y in metres from top of display
    private double yDistance;
    //display in which the car is shown
    private OutputDisplayPanel displayPanel;

    public Car(Driver driver, double length, double width, double maxAcceleration, double maxSpeed, double currentAcceleration, double currentSpeed, double maxTurnAngle, double currentAngle, double xDistance, double yDistance, OutputDisplayPanel displayPanel) {
        this.driver = driver;
        this.length = length;
        this.width = width;
        this.maxAcceleration = maxAcceleration;
        this.maxSpeed = maxSpeed;
        this.currentAcceleration = currentAcceleration;
        this.currentSpeed = currentSpeed;
        this.maxTurnAngle = maxTurnAngle;
        this.currentAngle = currentAngle;
        this.xDistance = xDistance;
        this.yDistance = yDistance;
        this.displayPanel = displayPanel;
        double imageXScale = ((double)toPixels(width)) / CarGraphic.CAR_IMG_WIDTH;
        double imageYScale = ((double) toPixels(length)) / CarGraphic.CAR_IMG_LENGTH;
        graphic = new CarGraphic(toPixels(xDistance), xPixelsPerSecond(), xPixelsPerSecondPerSecond(), toPixels(yDistance),yPixelsPerSecond(), yPixelsPerSecondPerSecond(), imageXScale, imageYScale);
    }

    private int xPixelsPerSecondPerSecond() {
        return Math.round(toPixelsPerSecondPerSecond(Math.sin(Math.toRadians(currentAngle)) * currentAcceleration));
    }

    private int yPixelsPerSecondPerSecond() {
        return -Math.round(toPixelsPerSecondPerSecond(Math.cos(Math.toRadians(currentAngle)) * currentAcceleration));
    }

    private int xPixelsPerSecond() {
        return Math.round(toPixelsPerSecond(Math.sin(Math.toRadians(currentAngle)) * currentSpeed));
    }

    private int yPixelsPerSecond() {
        return -Math.round(toPixelsPerSecond(Math.cos(Math.toRadians(currentAngle)) * currentSpeed));
    }

    private int toPixelsPerSecondPerSecond(double kmphPerSec) {
        return (int) (toPixels(kmphPerSec * 100) / (60 * 60));
    }

    private int toPixelsPerSecond(double kmph) {
        return (int) (toPixels(kmph * 1000) /(60 * 60));
    }

    private int toPixels(double metres) {
        return (int) Math.round(displayPanel.PIXELS_PER_METRE * metres);
    }

    public CarGraphic getGraphic() {
        return graphic;
    }
}
