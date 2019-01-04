import Exceptions.InvalidRoadConnectionException;
import Exceptions.SamePieceException;

import java.util.*;

public class RoadSegmentConnection {
    //list of points from left to right
    private List<PathConnectionPoint> connectionPoints = new LinkedList<>();
    private RoadwayPiece memberOfRoadwayPiece;
    private double angleOfSegment;
    private int width;
    private int height;

    public void connectSegment(RoadSegmentConnection roadSegmentConnection) throws InvalidRoadConnectionException {
        if (roadSegmentConnection.memberOfRoadwayPiece == memberOfRoadwayPiece) {
            throw new SamePieceException();
        }
        if (connectionPoints.size() != roadSegmentConnection.connectionPoints.size()) {
            throw new InvalidRoadConnectionException();
        }

    }
}
