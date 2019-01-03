import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OutputDisplayPanel extends JPanel implements ActionListener {
    public static final int WINDOW_WIDTH = 900;
    public static final int WINDOW_HEIGHT = 1200;
    private static final double DEFAULT_FPS = 2;
    private static final int secToMilliSec = 1000;
    Timer timer = new Timer((int)(1/DEFAULT_FPS * secToMilliSec), this);
    List<GenericComponent> components = new ArrayList<>();

    public void addComponent(GenericComponent component) {
        components.add(component);
    }

    public void addComponent(List<? extends GenericComponent> components) {
        this.components.addAll(components);
    }

    private void updateMovableImages() {
        for (GenericComponent image: components) {
            if (image instanceof MovableImage) {
                MovableImage movableImage = (MovableImage) image;
                movableImage.setX(movableImage.getX() + movableImage.getVelX());
                movableImage.setY(movableImage.getY() + movableImage.getVelY());
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);  // Set background color for this JPanel
        List<GenericComponent> drawedComponents = new LinkedList<>();
        for (GenericComponent component: components) {
            if (component instanceof GenericImage) {
                GenericImage image = (GenericImage) component;
                double rotationRequired;
                if (image instanceof MovableImage) {
                    rotationRequired = (((MovableImage) component).getAngle());
                } else {
                    rotationRequired = 0;
                }
                StaticImage rotatedImage = getRotatedImage(image, rotationRequired);
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

    /** Gets StaticImage corresponding to GenericImage rotated
     *
     * @param image GenericImage object containing image to be rotated as getImage()
     * @param rotationRequired angle in degrees to rotate
     * @return rotated static image
     */
    private StaticImage getRotatedImage(GenericImage image, double rotationRequired) {
        int widthOfTranslatedImage = widthOfTranslatedImage(image.getWidth(), image.getHeight(), rotationRequired);
        int heightOfTranslatedImage = heightOfTranslatedImage(image.getWidth(), image.getHeight(), rotationRequired);
        //finds maximum height so no information is lost during transformation
        int maxWidth;
        int maxHeight;
        if  (widthOfTranslatedImage > image.getWidth()) {
            maxWidth = widthOfTranslatedImage;
        } else {
            maxWidth = image.getWidth();
        }

        if (heightOfTranslatedImage > image.getHeight()) {
            maxHeight = heightOfTranslatedImage;
        } else {
            maxHeight = image.getHeight();
        }
        //moves to centre of max coordinates
        AffineTransform premove = AffineTransform.getTranslateInstance((maxWidth - image.getWidth()) * 0.5, (maxHeight - image.getHeight()) * 0.5);
        AffineTransformOp premoveOp = new AffineTransformOp(premove, AffineTransformOp.TYPE_BILINEAR);
        //rotates at this location
        AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(rotationRequired), maxWidth * 0.5, maxHeight * 0.5);
        AffineTransformOp rotateOp = new AffineTransformOp(rotate, AffineTransformOp.TYPE_BILINEAR);
        //moves back to centre
        AffineTransform postmove = AffineTransform.getTranslateInstance((widthOfTranslatedImage - maxWidth) * 0.5, (heightOfTranslatedImage - maxHeight) * 0.5);
        AffineTransformOp postMoveOp = new AffineTransformOp(postmove, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage preRotation = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotated = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage result = new BufferedImage(widthOfTranslatedImage, heightOfTranslatedImage, BufferedImage.TYPE_INT_ARGB);
        preRotation = premoveOp.filter(image.getImage(), preRotation);
        rotated = rotateOp.filter(preRotation, rotated);
        result = postMoveOp.filter(rotated, result);
        return new StaticImage(result, image.getX(), image.getY(), widthOfTranslatedImage, heightOfTranslatedImage);
    }

    /** Gets the width of the rectangle enclosing translated image from width and height and the angle rotated
     *
     * @param imageWidth original image width
     * @param imageHeight original image height
     * @param angleRotatedDegrees angle by which image is rotated
     * @return int width of the rectangle enclosing translated image from width and height and the angle rotated
     */
    private int widthOfTranslatedImage(int imageWidth, int imageHeight, double angleRotatedDegrees) {
        if ((angleRotatedDegrees < 0 && angleRotatedDegrees % 180 < -90) || (angleRotatedDegrees >= 0 && angleRotatedDegrees % 180 <= 90)) {
            return Math.abs(((int) (2 * radiusOfVertices(imageWidth, imageHeight) * Math.sin(Math.toRadians(angleRotatedDegrees + angleOfOffset(imageWidth, imageHeight))))));
        } else {
            return Math.abs((int) (2 * radiusOfVertices(imageWidth, imageHeight) * Math.sin(Math.toRadians(angleRotatedDegrees - angleOfOffset(imageWidth, imageHeight)))));
        }
    }

    /** Gets the height of the rectangle enclosing translated image from width and height and the angle rotated
     *
     * @param imageWidth original image width
     * @param imageHeight original image height
     * @param angleRotatedDegrees angle by which image is rotated
     * @return int height of the rectangle enclosing translated image from width and height and the angle rotated
     */
    private int heightOfTranslatedImage(int imageWidth, int imageHeight, double angleRotatedDegrees) {
        if ((angleRotatedDegrees < 0 && angleRotatedDegrees % 180 < -90) || (angleRotatedDegrees >= 0 && angleRotatedDegrees % 180 <= 90)) {
            return Math.abs((int) (2 * radiusOfVertices(imageWidth, imageHeight) * Math.cos(Math.toRadians(angleRotatedDegrees - angleOfOffset(imageWidth, imageHeight)))));
        } else {
            return Math.abs( (int) (2 * radiusOfVertices(imageWidth, imageHeight) * Math.cos(Math.toRadians(angleRotatedDegrees + angleOfOffset(imageWidth, imageHeight)))));
        }
    }

    /** Gets square of a number
     *
     * @param number number to square
     * @return number squared
     */
    private double square(double number) {
        return number * number;
    }

    /** Returns the distance of the vertices of the rectangle from the centre of the rectangle.
     *
     * @return double the distance of the vertices of the rectangle from the centre of the rectangle.
     */
    private double radiusOfVertices(int width, int height) {
        return 0.5 * Math.sqrt(square(width) + square(height));
    }

    /** Angle from the top left corner of the rectangle to the north direction.
     *
     * @return double angle from the top left corner of the rectangle to the north direction.
     */
    private double angleOfOffset(int recWidth, int recHeight) {
        return Math.toDegrees(Math.atan(((double) recWidth)/ recHeight));
    }

    public void actionPerformed(ActionEvent e) {
        updateMovableImages();
        repaint();
    }
}
