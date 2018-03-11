
package PointModel;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * This class provides a representation of points in an image in
 * the form a grid
 * @author Riley
 *
 */
public class ImageGrid {
	private final int split;
	private Set<MapPoint>[][] grid;
	
	// Representation invariant
	//   width >= 0
	//   height >= 0
	//   For each point P in grid[i][j], P.getX() / SPLIT = i
	//   and P.getY() / SPLIT = j
	//   grid[i][j] != null
	
	
	/**
	 * Constructor for an ImageGrid
	 * 
	 * @requires split < image.getWidth() && split < image.getHeight()
	 * @param image The image that this grid will represent
	 * @param split The length of the squares in the grid
	 * @throws IllegalArgumentException
	 * 		   image == null
	 */
	@SuppressWarnings("unchecked")
	public ImageGrid(BufferedImage image, int split) {
		if (image == null) {
			throw new IllegalArgumentException();
		}
		this.split = split;
		grid = (Set<MapPoint>[][]) new Set[image.getWidth() / split + 1][image.getHeight() / split + 1];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j] = new HashSet<MapPoint>();
			}
		}
		System.out.println(image.getWidth() + " : " + grid.length);
	}
	
	/**
	 * Adds the given MapPoint to the grid
	 * @param point Point to be added to the grid
	 * @throws IllegalArgumentException 
	 * 		   point == null || point not in grid
	 */
	public void addPoint(MapPoint point) {
		if (point == null || point.getX() >= split * grid.length || point.getY() >= split * grid[0].length) {
			throw new IllegalArgumentException();
		}
		int xCoord = (int) point.getX() / split;
		int yCoord = (int) point.getY() / split;
		grid[xCoord][yCoord].add(point);
	}
	
	/**
	 * Returns whether the given point is registered in the map
	 * @param point The point to be checked for
	 * @throws IllegalArgumentException
	 * 		   point == null || point not in grid
	 * @return True if the given point is in the map
	 * 		   False otherwise
	 */
	public boolean contains(MapPoint point) {
		if (point == null || point.getX() >= split * grid.length || point.getY() >= split * grid[0].length) {
			throw new IllegalArgumentException();
		}
		int xCoord = (int) point.getX() / split;
		int yCoord = (int) point.getY() / split;
		return grid[xCoord][yCoord].contains(point);
	}
	
	/**
	 * Returns the closest point to the given point in the given search 
	 * range in the grid. 
	 * @param point The point to be calculated against
	 * @param search The size of the search grid. Number of extra layers to check
	 * @throws IllegalArgumentException
	 * 		   point == null || search < 0 || point not in grid
	 * @return The closest point to point, null if there are no points in the region
	 */
	public MapPoint getClosest(MapPoint point, int search) {
		if (point == null || search < 0 || point.getX() >= split * grid.length || point.getY() >= split * grid[0].length) {
			throw new IllegalArgumentException();
		}
		Set<MapPoint> candidates = getCandidates(point, search);
		double minDistance = -1;
		MapPoint closestPoint = null;
		for (MapPoint curr : candidates) {
			double currDistance = point.getDistance(curr);
			if (currDistance < minDistance || minDistance == -1) {
				closestPoint = curr;
				minDistance = currDistance;
			}
		}
		return closestPoint;
	}
	
	/**
	 * Get the set of all potential points that could be close as determined by search
	 * @requires point != null && search >= 0 && point is in the grid
	 * @param point The point from which to check
	 * @param search The layer of search to do
	 * @return The set of all points that are possibly the closest to the point
	 */
	private Set<MapPoint> getCandidates(MapPoint point, int search) {
		Set<MapPoint> candidates = new HashSet<MapPoint>();
		System.out.println(point.getX() + " : " + point.getX() / split);
		int xCoord = (int) point.getX() / split;
		int yCoord = (int) point.getY() / split;
		
		// Set bounds on search to avoid IndexOutOfBounds
		int leftMost = xCoord - search;
		if (leftMost < 0) {
			leftMost = 0;
		}
		int rightMost = xCoord + search;
		if (rightMost >= grid.length) {
			rightMost = grid.length - 1;
		}
		int upperMost = yCoord - search;
		if (upperMost < 0) {
			upperMost = 0;
		}
		int lowerMost = yCoord + search;
		if (lowerMost >= grid[0].length) {
			lowerMost = grid[0].length - 1;
		}
		
		
		for (int i = leftMost; i <= rightMost; i++) {
			for (int j = upperMost; j <= lowerMost; j++) {
				candidates.addAll(grid[i][j]);
			}
		}
		return candidates;
	}
	
	
	
}
