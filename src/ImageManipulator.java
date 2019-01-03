import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageManipulator {
    /** Gets StaticImage corresponding to GenericImage rotated
     *
     * @param image GenericImage object containing image to be rotated as getImage()
     * @param rotationRequired angle in degrees to rotate
     * @return rotated static image
     */
    public static StaticImage getRotatedImage(GenericImage image, double rotationRequired) {
        int widthOfTranslatedImage = widthOfTranslatedImage(image.getImage().getWidth(), image.getImage().getHeight(), rotationRequired);
        int heightOfTranslatedImage = heightOfTranslatedImage(image.getImage().getWidth(), image.getImage().getHeight(), rotationRequired);
        //finds maximum height so no information is lost during transformation
        int maxWidth;
        int maxHeight;
        if  (widthOfTranslatedImage > image.getImage().getWidth()) {
            maxWidth = widthOfTranslatedImage;
        } else {
            maxWidth = image.getImage().getWidth();
        }

        if (heightOfTranslatedImage > image.getImage().getHeight()) {
            maxHeight = heightOfTranslatedImage;
        } else {
            maxHeight = image.getImage().getHeight();
        }
        //moves to centre of max coordinates
        AffineTransform premove = AffineTransform.getTranslateInstance((maxWidth - image.getImage().getWidth()) * 0.5, (maxHeight - image.getImage().getHeight()) * 0.5);
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
    public static int widthOfTranslatedImage(int imageWidth, int imageHeight, double angleRotatedDegrees) {
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
    public static int heightOfTranslatedImage(int imageWidth, int imageHeight, double angleRotatedDegrees) {
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
    private static double square(double number) {
        return number * number;
    }

    /** Returns the distance of the vertices of the rectangle from the centre of the rectangle.
     *
     * @return double the distance of the vertices of the rectangle from the centre of the rectangle.
     */
    private static double radiusOfVertices(int width, int height) {
        return 0.5 * Math.sqrt(square(width) + square(height));
    }

    /** Angle from the top left corner of the rectangle to the north direction.
     *
     * @return double angle from the top left corner of the rectangle to the north direction.
     */
    private static double angleOfOffset(int recWidth, int recHeight) {
        return Math.toDegrees(Math.atan(((double) recWidth)/ recHeight));
    }

    /** Scales a BufferedImage by a given factor in the x and y direction
     *
     * @param image Image to be scaled
     * @param scalex factor by which to scale the x coordinates
     * @param scaley factor by which to scale the y coordinates
     *
     * @return Scaled BufferedImage
     */
    public static BufferedImage scaleImage(BufferedImage image, double scalex, double scaley) {
        AffineTransform scale = AffineTransform.getScaleInstance(scalex, scaley);
        AffineTransformOp scaleOp = new AffineTransformOp(scale, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage result = new BufferedImage((int) (image.getWidth() * scalex), (int) (image.getHeight() * scaley), BufferedImage.TYPE_INT_ARGB);
        scaleOp.filter(image, result);
        return result;
    }
}
