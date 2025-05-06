package algorithms;

import other.*;
import java.util.ArrayList;

// This implementation assumes a complete graph (which is typically the case in TSP)
public class Greedy {

	private int distance;
	private ArrayList<City> path;

	public void findShortestPath(Map map, String startCity) {
		// Check valid input
		City start = map.findCity(startCity);
		if (start == null) {
			throw new IllegalArgumentException("Start city not found in map");
		}

		// Initialize important variables
		int cities = map.getCities().size();
		City currentCity = map.findCity(startCity);
		path = new ArrayList<City>();

		// While there are unvisited cities
		while (path.size() < cities - 1) {
			ArrayList<Road> curNear = currentCity.getNear();
			Road minRoad = null;
			// Check each road, and find unvisited city of minimum length
			for (Road r : curNear) {
				if (minRoad == null && !path.contains(r.getCity())) {
					minRoad = r;
				}
				if (minRoad != null && !path.contains(r.getCity()) && r.getLength() < minRoad.getLength()) {
					minRoad = r;
				}
			}
			// Add current city to path, and search the next city in next iteration
			path.add(currentCity);
			// Return max integer value and empty path if next node cannot be found
			if (minRoad == null) {
				distance = Integer.MAX_VALUE;
				path = new ArrayList<>();
				return;
			}
			distance = distance + minRoad.getLength();
			currentCity = map.findCity(minRoad.getCity().getName());
		}
		// Add final path
		path.add(currentCity);
		ArrayList<Road> curNear = currentCity.getNear();
		for (Road r : curNear) {
			if (r.getCity().getName().equals(startCity)) {
				path.add(map.findCity(startCity));
				distance = distance + r.getLength();
				return;
			}
		}

		path = new ArrayList<>();
		distance = 0;
	}

	public int getBestCost() {
		return distance;
	}

	public ArrayList<City> getBestPath() {
		return path;
	}

	public String pathString() {
		return path.toString();
	}

}
