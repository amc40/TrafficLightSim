import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OutputDisplayFrame extends JFrame {
    private static final String WINDOW_NAME = "TrafficLight Scenario Ouput";
    private OutputDisplayPanel canvas;
    public OutputDisplayFrame(List<GenericImage> images) {
        canvas = new OutputDisplayPanel();
        canvas.addComponent(images);
        canvas.setPreferredSize(new Dimension(canvas.WINDOW_WIDTH, canvas.WINDOW_HEIGHT));
        setContentPane(canvas);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle(WINDOW_NAME);
        setVisible(true);
    }
}
