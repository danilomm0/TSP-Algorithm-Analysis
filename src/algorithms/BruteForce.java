package algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import other.*;

public class BruteForce {
	private List<City> minPath = new ArrayList<>();
	private int min = Integer.MAX_VALUE;

	public void paths(String name1, String name2, Map map) {
		ArrayList<City> path = new ArrayList<>();
		path.add(map.findCity(name1));
		allPaths(map.findCity(name1), map.findCity(name2), map, new HashSet<City>(), path, 0);
	}

	private void allPaths(City city1, City city2, Map map, HashSet<City> visited, List<City> path, int distance) {
		visited.add(city1);
		if (visited.size() == map.getCities().size()) {
			// Try to find a road to the start city
			for (Road road : city1.getNear()) {
				if (road.getCity().equals(path.get(0))) {
					distance += road.getLength();
					// Replace best cost and path if found and total distance is better
					if (distance < min) {
						min = distance;
						minPath = new ArrayList<>(path);
						minPath.add(road.getCity());
					}
					break;
				}
			}
		} else {
			// Depth first search on all unvisited cities where the total distance from
			// start to said city is less than the best so far
			for (Road road : city1.getNear()) {
				if (!visited.contains(road.getCity())) {
					int temp = road.getLength();
					path.add(road.getCity());
					allPaths(map.findCity(road.getCity().getName()), city2, map, visited, path, distance + temp);
					visited.remove(road.getCity());
					path.remove(road.getCity());
				}

			}
		}
	}

	public int getBestCost() {
		return min;
	}

	public List<City> getBestPath() {
		return minPath;
	}
}