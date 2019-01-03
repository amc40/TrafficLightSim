import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OutputDisplayPanel extends JPanel implements ActionListener {
    public static final double PIXELS_PER_METRE = 20;
    public static final int WINDOW_WIDTH = 900;
    public static final int WINDOW_HEIGHT = 1200;
    private static final double DEFAULT_FPS = 30;
    private static final int secToMilliSec = 1000;
    Timer timer = new Timer((int)(1/DEFAULT_FPS * secToMilliSec), this);
    List<GenericComponent> components = new ArrayList<>();

    public void addComponent(GenericComponent component) {
        components.add(component);
    }

    public void addComponents(List<? extends GenericComponent> components) {
        this.components.addAll(components);
    }

    private void updateMovableImages() {
        for (GenericComponent image: components) {
            if (image instanceof MovableImage) {
                MovableImage movableImage = (MovableImage) image;
                movableImage.setX(movableImage.getX() + (int) (movableImage.getVelX()/DEFAULT_FPS ));
                movableImage.setY(movableImage.getY() + (int) (movableImage.getVelY()/DEFAULT_FPS));
                movableImage.setVelX(movableImage.getVelX() + (int) (movableImage.getAccX()/DEFAULT_FPS));
                movableImage.setVelY(movableImage.getVelY() + (int) (movableImage.getAccY()/ DEFAULT_FPS));
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g);
        setBackground(Color.WHITE);  // Set background color for this JPanel
        List<GenericComponent> drawedComponents = new LinkedList<>();
        //TODO: fix concurrent modification exception
        for (GenericComponent component: components) {
            if (component instanceof GenericImage) {
                GenericImage image = (GenericImage) component;
                double rotationRequired;
                if (image instanceof MovableImage) {
                    rotationRequired = (((MovableImage) component).getAngle());
                } else {
                    rotationRequired = 0;
                }
                StaticImage rotatedImage = ImageManipulator.getRotatedImage(image, rotationRequired);
                g.drawImage(rotatedImage.getImage(), rotatedImage.getX(), rotatedImage.getY(), null);
                if (checkForCollisions(rotatedImage, drawedComponents)) System.out.println("Colliding!");
                drawedComponents.add(rotatedImage);
            }
        }
        timer.start();
    }

    /** Checks to make sure component at end
     *
     * @return true if there is a collision with this components, false otherwise
     */
    private boolean checkForCollisions(GenericComponent currentComponent, List<GenericComponent> drawedComponents) {
        for (GenericComponent genericComponent : drawedComponents) {
            if (currentComponent.colliding(genericComponent)) {
                return true;
            }
        }
        return false;
    }

    public void actionPerformed(ActionEvent e) {
        updateMovableImages();
        repaint();
    }
}
