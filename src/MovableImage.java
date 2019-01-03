import java.awt.*;

public abstract class MovableImage extends GenericImage {
    /** Getter for the x velocity
     *
     * @return x velocity
     */

    abstract int getVelX();
    abstract int getVelY();
    abstract int getAccX();
    abstract int getAccY();

    /** Returns current angle in degrees.
     *
     * @return double angle in degrees.
     */
    abstract double getAngle();
    abstract void setX(int x);
    abstract void setY(int y);
    abstract void setVelX(int velX);
    abstract void setVelY(int velY);
    abstract void setAccX(int accX);
    abstract void setAccY(int accY);
}
