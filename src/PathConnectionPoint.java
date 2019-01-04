import Exceptions.InvalidRoadConnectionException;
import Exceptions.TrafficFlowConnectionException;

import java.awt.*;
import java.awt.geom.Point2D;

public class PathConnectionPoint {
    private RoadwayPiece memberOfRoadwayPiece;
    private Point2D point;
    private PathConnectionPoint connectedPoint;
    private TrafficFlowAtConnectionPoint trafficFlow;

    /**
     * Enum for whether a given connection point takes in or pushes out traffic
     */
    enum TrafficFlowAtConnectionPoint
    {
        INCOMING, OUTGOING;
    }

    /** Constructor specifying the connection point, whether the traffic is incoming or outgoing at the point and the point it is connected to.
     *
     * @param point 2D point corresponding to the location of the connection on the display panel
     * @param trafficFlow either INCOMING or OUTGOING
     * @param connectedPoint point to connect to
     *
     * @throws InvalidRoadConnectionException if can't connect to given point
     */
    public PathConnectionPoint(Point2D point, TrafficFlowAtConnectionPoint trafficFlow,PathConnectionPoint connectedPoint) throws InvalidRoadConnectionException {
        this.point = point;
        this.trafficFlow = trafficFlow;
        if (connectedPoint != null) {
            setConnectedPoint(connectedPoint);
        }
    }

    public PathConnectionPoint(Point point, TrafficFlowAtConnectionPoint trafficFlow) throws InvalidRoadConnectionException {
        this(point, trafficFlow, null);
    }

    /** Getter for the point
     *
     * @return Point2D object for point
     */
    public Point2D getPoint() {
        return point;
    }

    /** Getter for the connected point
     *
     * @return PathConnectionPoint connected point
     */
    public PathConnectionPoint getConnectedPoint() {
        return connectedPoint;
    }

    /** Checks if the current connection point can be connected to the given connection point
     *
     * @param pathConnectionPoint point to check
     * @return true if can connect, false otherwise
     */
    public boolean canConnect(PathConnectionPoint pathConnectionPoint) {
        if (trafficFlow == TrafficFlowAtConnectionPoint.INCOMING && connectedPoint.trafficFlow == TrafficFlowAtConnectionPoint.OUTGOING) {
            return true;
        } else if (trafficFlow == TrafficFlowAtConnectionPoint.OUTGOING && connectedPoint.trafficFlow == TrafficFlowAtConnectionPoint.INCOMING) {
            return true;
        }
        return false;
    }

    /** Sets the point connected to the current point
     *
     * @param pointToConnect point to connect to
     * @throws InvalidRoadConnectionException exception thrown if can't connect given point
     */
    public void setConnectedPoint(PathConnectionPoint pointToConnect) throws InvalidRoadConnectionException {
        if (canConnect(pointToConnect)) {
            connectedPoint = pointToConnect;
        } else {
            throw new TrafficFlowConnectionException();
        }
    }

    /** returns true if connected to another point, false otherwise
     *
     * @return returns true if connected to another point, false otherwise
     */
    public boolean isConnected() {
        return connectedPoint != null;
    }

}
