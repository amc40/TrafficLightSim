import java.awt.*;

public abstract class MovableImage extends GenericImage {
    /** Getter for the x velocity
     *
     * @return x velocity
     */

    abstract int getVelX();
    abstract int getVelY();

    /** Returns current angle in degrees.
     *
     * @return double angle in degrees.
     */
    abstract double getAngle();
    abstract void setX(int x);
    abstract void setY(int y);
    abstract void setVelX(int velX);
    abstract void setVelY(int velY);

}
