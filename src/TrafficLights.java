import java.util.LinkedList;
import java.util.List;

public class TrafficLights {
    public static void main(String[] args) {
        List<GenericImage> cars = new LinkedList<>();
        cars.add(new CarGraphic(500,-2,500,-7));
        cars.add(new CarGraphic(0, 2, 500, -1));
        OutputDisplayFrame outputDisplayFrame = new OutputDisplayFrame(cars);
    }
}
