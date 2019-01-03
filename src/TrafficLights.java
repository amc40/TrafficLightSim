import sun.awt.geom.AreaOp;

import java.util.LinkedList;
import java.util.List;

public class TrafficLights {
    public static void main(String[] args) {
        OutputDisplayFrame outputDisplayFrame = new OutputDisplayFrame();
        OutputDisplayPanel canvas = outputDisplayFrame.getCanvas();
        Driver driver1 = new Driver(-50);
        Driver driver2 = new Driver(50);
        //Driver driver, double length, double width, double maxAcceleration, double maxSpeed, double currentAcceleration, double currentSpeed, double maxTurnAngle, double currentAngle, double xDistance, double yDistance, OutputDisplayPanel displayPanel
        Car car1 = new Car(driver1, 4, 1.5, 4, 150, 30, 30, 30, 120, 20, 20, canvas);
        canvas.addComponent(car1.getGraphic());
        Car car2 = new Car(driver2, 3, 1, 2, 120,  0, 40, 30 , 25, 20, 30, canvas);
        canvas.addComponent(car2.getGraphic());
    }
}
