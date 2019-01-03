import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CarGraphic extends MovableImage {
    private static final String IMAGE_LOCATION = "src\\img\\carblank3.png";
    private static final String IMG_ERR_MSG = "Could not read image:" + IMAGE_LOCATION;
    public static final int CAR_IMG_WIDTH = 339;
    public static final int CAR_IMG_LENGTH = 678;
    private BufferedImage carImage;
    private int x, velX, accX, y, velY, accY;

    public CarGraphic(int x, int velX,int accX, int y, int velY, int accY, double xScale, double yScale) {
        this.x = x;
        this.velX = velX;
        this.y = y;
        this.velY = velY;
        this.accX = accX;
        this.accY = accY;
        try {
            carImage = ImageManipulator.scaleImage(ImageIO.read(new File(IMAGE_LOCATION)), xScale, yScale);
        } catch (IOException e) {
            System.out.println(IMG_ERR_MSG);
        }
    }


    @Override
    public double getAngle() {
        double angle = 0;
        if (velY > 0) {
            angle = 180;
        }
        angle -= Math.toDegrees(Math.atan(((double) velX)/ velY));
        return angle;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getVelX() {
        return velX;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getVelY() {
        return velY;
    }

    @Override
    public BufferedImage getImage() {
        return carImage;
    }

    @Override
    public int getWidth() {
        return ImageManipulator.widthOfTranslatedImage(carImage.getWidth(), carImage.getHeight(), getAngle());
    }

    @Override
    public int getHeight() {
        return ImageManipulator.heightOfTranslatedImage(carImage.getWidth(), carImage.getHeight(), getAngle());
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setVelX(int velX) {
        this.velX = velX;
    }

    @Override
    public void setVelY(int velY) {
        this.velY = velY;
    }

    @Override
    public int getAccX() {
        return accX;
    }

    @Override
    public void setAccX(int accX) {
        this.accX = accX;
    }

    @Override
    public int getAccY() {
        return accY;
    }

    @Override
    public void setAccY(int accY) {
        this.accY = accY;
    }
}
