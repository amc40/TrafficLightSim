import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OutputDisplayFrame extends JFrame {
    private static final String WINDOW_NAME = "TrafficLight Scenario Ouput";
    private OutputDisplayPanel canvas;
    public OutputDisplayFrame() {
        canvas = new OutputDisplayPanel();
        canvas.setPreferredSize(new Dimension(canvas.WINDOW_WIDTH, canvas.WINDOW_HEIGHT));
        setContentPane(canvas);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle(WINDOW_NAME);
        setVisible(true);
    }

    public void addComponentToCanvas(GenericComponent component) {
        canvas.addComponent(component);
    }

    public OutputDisplayPanel getCanvas() {
        return canvas;
    }
}
