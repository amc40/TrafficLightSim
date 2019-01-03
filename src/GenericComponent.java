public interface GenericComponent {
    /** Getter for X Coordinate
     *
     * @return x coordinate
     */
    int getX();

    /** Getter for the y coordinate.
     *
     * @return y coordinate
     */
    int getY();
    /** Getter for the width of the component
     *  @return component width
     */
    int getWidth();

    /** Getter for the component height
     *
     * @return component height
     */
    int getHeight();

    boolean colliding(GenericComponent genericComponent);
}
