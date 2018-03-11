package Locator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import PointModel.MapPoint;

public class MapLines {
	// Representation Invariant
	// 	groups != null
	//  For MapPoint in groups.keySet(), groups.get(key) != null
	
	
	private Map<MapPoint, Set<MapPoint>> groups = new HashMap<MapPoint, Set<MapPoint>>();
	
	/**
	 * Constructor for a MapLines object
	 */
	public MapLines() {}
	
	/**
	 * Adds the given point to the graph if the start is not already in the object
	 * @param start The start point to add to the object
	 * @throws IllegalArgumentException
	 * 		   start == null
	 * @return True if the start was successfully added, false otherwise
	 */
	public boolean addStart(MapPoint start) {
		if (start == null) {
			throw new IllegalArgumentException();
		}
		if (groups.containsKey(start)) {
			return false;
		}
		groups.put(start, new HashSet<MapPoint>());
		return true;
	}
	
	/**
	 * Associates the given destination to the given start in the MapLines object
	 * @param start The start of the line
	 * @param destination The end of the line
	 * @throws IllegalArgumentException
	 * 		   start == null || destination == null
	 */
	public void associateLine(MapPoint start, MapPoint destination) {
		if (start == null || destination == null) {
			throw new IllegalArgumentException();
		}
		if (!groups.containsKey(start)) {
			groups.put(start, new HashSet<MapPoint>());
		} 
		groups.get(start).add(destination);
	}
	
	/**
	 * Returns the associated destinations with the given start point
	 * @param start The point from which lines will start from
	 * @throws IllegalArgumentException
	 * 		   start == null
	 * @return Set of points to which start has a line to, null if start
	 * 		   is not in graph
	 */
	public Set<MapPoint> getDestination(MapPoint start) {
		if (start == null) {
			throw new IllegalArgumentException();
		}
		if (groups.containsKey(start)) {
			return groups.get(start);
		}
		return null;
	}
}
