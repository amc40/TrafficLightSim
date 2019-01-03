public class Driver {
    //number to take into account how aggressive the driver is in their actions 0 = average more positive = more aggressive
    private double aggressiveFactor;

    public Driver(double aggressiveFactor) {
        this.aggressiveFactor = aggressiveFactor;
    }

    public double getAggressiveFactor() {
        return aggressiveFactor;
    }
}
