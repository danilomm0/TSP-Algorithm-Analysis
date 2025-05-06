package algorithms;

import other.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class HeldKarp_Dynamic {
    private long[][] dp;
    private int[][] nextCity;
    private HashMap<Integer, String> indexToCity;
    private Map cityMap;

    // Constructor
    public HeldKarp_Dynamic(Map cityMap) {
        this.cityMap = cityMap;
        setUpIndexToCityMap();
        initializeDP();
    }

    // Setting up index-to-city map
    private void setUpIndexToCityMap() {
        indexToCity = new HashMap<>();
        int idx = 0;
        for (City city : cityMap.getCities()) {
            indexToCity.put(idx++, city.getName());
        }
    }

    // Initialize Dynamic Programming Table
    private void initializeDP() {
        int totalCities = cityMap.size();
        dp = new long[1 << totalCities][totalCities];
        nextCity = new int[1 << totalCities][totalCities];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j] = -1;
                nextCity[i][j] = -1;
            }
        }
    }

    // Calculating distance
    private long calcDist(City fromCity, City toCity) {
        if (fromCity == null || toCity == null) {
            return Long.MAX_VALUE;
        }
        for (Road r : fromCity.getNear()) {
            if (r.getCity().equals(toCity)) {
                return (long) r.getLength();
            }
        }
        return Long.MAX_VALUE;
    }

    // Find shortest route
    public long findShortestRoute(String startCityName) {
        int startCityIndex = getIndexByCityName(startCityName);
        if (startCityIndex == -1) return -1; // Invalid start city
        return tsp(startCityIndex, 1 << startCityIndex, startCityIndex);
    }

    // The TSP algorithm implementation
    private long tsp(int currentCityIndex, int visited, int startCityIndex) {
        // Check if all cities have been visited
        if (visited == (1 << cityMap.size()) - 1) {
            // Return distance to start city or Long.MAX_VALUE if no direct path
            return calcDist(cityMap.findCity(indexToCity.get(currentCityIndex)), cityMap.findCity(indexToCity.get(startCityIndex)));
        }

        // Use precomputed value if available
        if (dp[visited][currentCityIndex] != -1) {
            return dp[visited][currentCityIndex];
        }

        long shortestDist = Long.MAX_VALUE;
        for (int cityIdx = 0; cityIdx < cityMap.size(); cityIdx++) {
            if ((visited & (1 << cityIdx)) == 0) {
                City currentCity = cityMap.findCity(indexToCity.get(currentCityIndex));
                City nextCity = cityMap.findCity(indexToCity.get(cityIdx));
                long distToNextCity = calcDist(currentCity, nextCity);

                // Proceed only if there is a path to the next city
                if (distToNextCity != Long.MAX_VALUE) {
                	long temp = tsp(cityIdx, visited | (1 << cityIdx), startCityIndex);
                	if (temp != Long.MAX_VALUE) {
	                    long dist = distToNextCity + temp;
	
	                    // Avoid overflow
	                    if (dist < shortestDist) {
	                        shortestDist = dist;
	                        this.nextCity[visited][currentCityIndex] = cityIdx;
	                    }
                	}
                }
            }
        }

        dp[visited][currentCityIndex] = shortestDist;
        return shortestDist;
    }

    // Utility method to get index by city name
    private int getIndexByCityName(String cityName) {
        for (int key : indexToCity.keySet()) {
            if (indexToCity.get(key).equals(cityName)) {
                return key;
            }
        }
        return -1;
    }

    // PATH GET????
    public List<String> getBestPath(String startCityName) {
        int startCityIndex = getIndexByCityName(startCityName);
        if (startCityIndex == -1) return null; // Invalid start city

        List<String> path = new ArrayList<>();
        int visited = 1 << startCityIndex;
        int currentCityIndex = startCityIndex;

        while (true) {
            path.add(indexToCity.get(currentCityIndex));
            if (nextCity[visited][currentCityIndex] == -1) {
                break;
            }
            int nextIndex = nextCity[visited][currentCityIndex];
            visited |= (1 << nextIndex);
            currentCityIndex = nextIndex;
        }
        path.add(startCityName);
        return path;
    }
}