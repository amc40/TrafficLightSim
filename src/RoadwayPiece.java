import java.awt.geom.Point2D;
import java.util.List;

public abstract class RoadwayPiece {
    //standard size x size dimension in metres of roadway piece
    private static final int STANDARD_TILE_SIZE = 25;
    private OutputDisplayPanel displayPanel;
    Point2D centre;
    List<RoadSegmentConnection>

    public Point2D getCentre() {
        return centre;
    }

    public void setCentre(Point2D centre) {
        this.centre = centre;
    }


}
