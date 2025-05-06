package algorithms;

import other.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;

public class BranchBound {
	private int bestCost = Integer.MAX_VALUE;
	private List<City> bestPath = new ArrayList<>();

	public void findShortestPath(Map map, String startCityName, String endCityName) {
		City startCity = map.findCity(startCityName);
		City endCity = map.findCity(endCityName);

		if (startCity == null || endCity == null) {
			throw new IllegalArgumentException("Start or end city not found in the map");
		}

		PriorityQueue<PathNode> queue = new PriorityQueue<>(Comparator.comparingInt(PathNode::getCost));
		queue.add(new PathNode(new ArrayList<>(Arrays.asList(startCity)), 0, startCity));

		while (!queue.isEmpty()) {
			PathNode current = queue.poll();
			City lastCity = current.getLastCity();

			if (current.getPath().size() == map.getCities().size()) {
				int tempCost = current.getCost();
				for (Road road : current.lastCity.getNear()) {
					if (road.getCity().equals(startCity)) {
						tempCost += road.getLength();
						if (tempCost < bestCost) {
							bestCost = tempCost;
							bestPath = current.getPath();
							bestPath.add(startCity);
						}
						break;
					}
				}
				continue;
			}

			for (Road road : lastCity.getNear()) {
				City nextCity = map.findCity(road.getCity().getName());
				if (!current.getPath().contains(nextCity)) {
					int newCost = current.getCost() + road.getLength();
					if (newCost < bestCost) {
						List<City> newPath = new ArrayList<>(current.getPath());
						newPath.add(nextCity);
						queue.add(new PathNode(newPath, newCost, nextCity));
					}
				}
			}
		}
	}

	public String pathString() {
		return bestPath.toString();
	}

	public List<City> getBestPath() {
		return bestPath;
	}

	public int getBestCost() {
		return bestCost;
	}

	private static class PathNode {
		private List<City> path;
		private int cost;
		private City lastCity;

		public PathNode(List<City> path, int cost, City lastCity) {
			this.path = path;
			this.cost = cost;
			this.lastCity = lastCity;
		}

		public List<City> getPath() {
			return path;
		}

		public int getCost() {
			return cost;
		}

		public City getLastCity() {
			return lastCity;
		}
	}
}