package other;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Map {
	private HashMap<String, City> map;
	private int accessCount = 0;

	public Map() {
		map = new HashMap<String, City>();
	}

	public Map(HashMap<String, City> temp) {
		map = temp;
	}

	public Map(String filename) {
		map = new HashMap<String, City>();
		try {
			Scanner input = new Scanner(new File(filename));
			while (input.hasNextLine()) {
				String[] data = input.nextLine().split(" ");
				addRoad(data[0], data[1], Integer.parseInt(data[2]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public City addCity(String name) {
		City newCity = new City();
		newCity.name = name;
		newCity.near = new ArrayList<Road>();
		map.put(name, newCity);
		return newCity;
	}

	public City findCity(String name) {
		if (map.containsKey(name)) {
			accessCount++;
			return map.get(name);
		}
		return null;
	}

	public boolean addRoad(String name1, String name2, int length) {
		City city1 = findCity(name1);
		City city2 = findCity(name2);
		if (city1 == null) {
			city1 = addCity(name1);
		}
		if (city2 == null) {
			city2 = addCity(name2);
		}
		if (findRoad(city1, name2) || findRoad(city2, name1)) {
			return false;
		}
		city1.near.add(new Road(city2, length));
		city2.near.add(new Road(city1, length));
		return true;
	}

	public boolean findRoad(City city, String name) {
		for (int i = 0; i < city.near.size(); i++) {
			if (city.near.get(i).getCity().getName().equals(name)) {
				accessCount++;
				return true;
			}
		}
		return false;
	}

	public int findPathLength(List<City> path) {
		int length = 0;
		for (int i = 0; i < path.size()-1; i++) {
			boolean found = false;
			for (Road road : path.get(i).getNear()) {
				if (road.getCity().equals(path.get(i+1))) {
					length += road.length;
					found = true;
					break;
				}
			}
			if (!found) {
				System.out.println("!!! ERROR: No road exists from " + path.get(i) + " to " + path.get(i+1) + " !!!");
				return 0;
			}
		}
		return length;
	}

	public Collection<City> getCities() {
		return map.values();
	}

	public int size() {
		return map.size();
	}

	public void resetAccessCount() {
		accessCount = 0;
		for (City city : getCities()) {
			for (Road road : city.getNear()) {
				road.resetAccessCount();
			}
		}
	}

	public int getAccessCount() {
		// Add access count from roads
		HashMap<City, City> visited = new HashMap<>();
		for (City city : getCities()) {
			for (Road road : city.getNear()) {
				if (!(visited.keySet().contains(city) && visited.get(city).equals(road.city))) {
					accessCount += road.getAccessCount();
					visited.put(city, road.city);
					visited.put(road.city, city);
				}
			}
		}
		return accessCount;
	}
}
