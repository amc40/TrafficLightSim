import java.awt.image.BufferedImage;

public class StaticImage extends GenericImage {
    private BufferedImage image;
    private int x;
    private int y;
    private int width;
    private int height;

    public StaticImage(BufferedImage image, int x, int y, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

}
