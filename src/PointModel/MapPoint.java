package PointModel;

/**
 * This is an immutable class representing points in an image
 * Points cannot have negative values as their coordinates
 * 
 * @author Riley
 *
 */
public class MapPoint {
	private final double x;
	private final double y;

	/**
	 * Constructor for a MapPoint object
	 * @param x The x coordinate for this point
	 * @param y The y coordinate for this point
	 * @throws IllegalArgumentException
	 * 		   x < 0 || y < 0
	 */
	public MapPoint(double x, double y) {
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructor for a MapPoint object
	 * @param x The x coordinate of this point
	 * @param y The y coordinate of this point
	 * @throws IllegalArgumentException
	 * 		   x < 0 || y < 0
	 */
	public MapPoint(int x, int y) {
		this((double) x, (double) y);
	}
	
	/**
	 * Returns the x coordinate of this point
	 * @return The x coordinate of this point
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns the y coordinate of this point
	 * @return The y coordinate of this point
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Returns the distance between this and other
	 * @param other The other point to be measured from
	 * @throws IllegalArgumentException
	 * 		   other == null
	 * @return The distance between this and other
	 */
	public double getDistance(MapPoint other) {
		if (other == null) {
			throw new IllegalArgumentException();
		}
		return Math.sqrt(Math.pow(x - other.getX(), 2) + Math.pow(y - other.getY(), 2));
	}
	
	/**
	 * Return the string representation for this object
	 */
	public String toString() {
		return "[" + getX() + ", " + getY() + "]";
	}
	
}
