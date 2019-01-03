import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GenericImage implements GenericComponent {
    /** Getter for the image.
     *
     * @return Image object
     */
    abstract BufferedImage getImage();

    @Override
    public boolean colliding(GenericComponent genericComponent) {
        Rectangle thisImageRect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle otherImageRect = new Rectangle(genericComponent.getX(), genericComponent.getY(), genericComponent.getWidth(), genericComponent.getHeight());
        if (!thisImageRect.intersects(otherImageRect)) {
            return false;
        }
        if  (genericComponent instanceof GenericImage) {
            System.out.println("Checking image");
            return imageOverlap((GenericImage) genericComponent);
        }
        return false;
    }

    private boolean imageOverlap(GenericImage genericImage) {
        Rectangle thisImageRect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle otherImageRect = new Rectangle(genericImage.getX(), genericImage.getY(), genericImage.getWidth(), genericImage.getHeight());
        Rectangle intersection = thisImageRect.intersection(otherImageRect);
        for (int intersectionX = 0; intersectionX < intersection.getWidth(); intersectionX++) {
            for (int intersectionY = 0; intersectionY < intersection.getHeight(); intersectionY++) {
                int xThisImage = (int) (intersectionX + intersection.getX() - thisImageRect.getX());
                int xOtherImage = (int) (intersectionX + intersection.getX() - otherImageRect.getX());
                int yThisImage = (int) (intersectionY + intersection.getY() - thisImageRect.getY());
                int yOtherImage = (int) (intersectionY + intersection.getY() - otherImageRect.getY());
                int thisPixel = getImage().getRGB(xThisImage, yThisImage);
                int otherImage = genericImage.getImage().getRGB(xOtherImage, yOtherImage);
                if (thisPixel>>24 != 0x00 && otherImage>>24 != 0x00) {
                    return true;
                }
            }
        }
        return false;
    }
}
