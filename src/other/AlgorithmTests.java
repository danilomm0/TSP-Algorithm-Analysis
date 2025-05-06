package other;

import java.util.List;
import org.junit.Test;
import algorithms.*;

public class AlgorithmTests {

	@Test
	public void test() {
		Map map = new Map();
		map.addRoad("tucson", "albuquerque", 318);
		map.addRoad("chandler", "tucson", 95);
		map.addRoad("elPaso", "tucson", 262);
		map.addRoad("tucson", "phoenix", 116);
		map.addRoad("phoenix", "flagstaff", 117);
		map.addRoad("yuma", "elPaso", 481);
		map.addRoad("phoenix", "yuma", 159);
		map.addRoad("tucson", "yuma", 220);
		map.addRoad("lasCruces", "tucson", 242);
		map.addRoad("lasCruces", "elPaso", 38);
		map.addRoad("chandler", "phoenix", 21);
		map.addRoad("lasCruces", "santafe", 285);
		map.addRoad("santafe", "albuquerque", 54);
		BruteForce test = new BruteForce();
		test.paths("tucson", "flagstaff", map);
	}

	@Test
	public void test2() {
		int[] working = new int[] { 1, 2, 3, 4, 5, 6 }; // put the number for each test case you want to run
		for (int i : working) {
			Map map = new Map("src\\other\\testset" + i);
			long time1, time2;
			int accessCount;
			double bruteTime = 0;
			double branchTime = 0;
			double greedyTime = 0;
			double karpTime = 0;
			double branchMultiple = 0;
			double greedyMultiple = 0;
			double karpMultiple = 0;
			long bruteAccess = 0;
			long branchAccess = 0;
			long greedyAccess = 0;
			long karpAccess = 0;
			double paths = 0;
			int branchCorrect = 0;
			int greedyCorrect = 0;
			int karpCorrect = 0;
			for (City city1 : map.getCities()) { //prints information solving one TSP problem
				City city2 = city1;
				BruteForce control = new BruteForce();
				BranchBound test = new BranchBound();
				Greedy greedy = new Greedy();
				HeldKarp_Dynamic heldkarp = new HeldKarp_Dynamic(map);
				System.out.println(city1.getName());

				map.resetAccessCount();
				time1 = System.nanoTime();
				control.paths(city1.getName(), city2.getName(), map);
				time2 = System.nanoTime();
				bruteTime += (time2 - time1) / 1000000.0;
				accessCount = map.getAccessCount();
				bruteAccess += accessCount;
				int controlLength = map.findPathLength(control.getBestPath());
				System.out.println("Control (brute force): " + controlLength);
				System.out.println("\ttime (ms): " + (time2 - time1) / 1000000.0);
				System.out.println("\taccess count: " + accessCount);

				map.resetAccessCount();
				time1 = System.nanoTime();
				test.findShortestPath(map, city1.getName(), city2.getName());
				time2 = System.nanoTime();
				branchTime += (time2 - time1) / 1000000.0;
				accessCount = map.getAccessCount();
				branchAccess += accessCount;
				int branchLength = map.findPathLength(test.getBestPath());
				System.out.println("Branch and bound: " + branchLength);
				System.out.println("\tmultiple: " + (double) branchLength / (double) controlLength);
				System.out.println("\ttime (ms): " + (time2 - time1) / 1000000.0);
				System.out.println("\taccess count: " + accessCount);
				branchMultiple += (double) branchLength / (double) controlLength;

				map.resetAccessCount();
				time1 = System.nanoTime();
				greedy.findShortestPath(map, city1.getName());
				time2 = System.nanoTime();
				greedyTime += (time2 - time1) / 1000000.0;
				accessCount = map.getAccessCount();
				greedyAccess += accessCount;
				int greedyLength = map.findPathLength(greedy.getBestPath());
				System.out.println("Greedy: " + greedyLength);
				System.out.println("\tmultiple: " + (double) greedyLength / (double) controlLength);
				System.out.println("\ttime (ms): " + (time2 - time1) / 1000000.0);
				System.out.println("\taccess count: " + accessCount);
				greedyMultiple += (double) greedyLength / (double) controlLength;

				map.resetAccessCount();
				time1 = System.nanoTime();
				long karpLength = heldkarp.findShortestRoute(city1.getName());
				time2 = System.nanoTime();
				karpTime += (time2 - time1) / 1000000.0;
				accessCount = map.getAccessCount();
				karpAccess += accessCount;
				System.out.println("Held Karp (dynamic): " + karpLength);
				System.out.println("\tmultiple: " + (double) karpLength / (double) controlLength);
				System.out.println("\ttime (ms): " + (time2 - time1) / 1000000.0);
				System.out.println("\taccess count: " + accessCount);
				karpMultiple += (double) karpLength / (double) controlLength;
				System.out.println("Path (control):          " + control.getBestPath());
				System.out.println("Path (branch and bound): " + test.getBestPath());
				System.out.println("Path (greedy):           " + greedy.getBestPath());
				System.out.println("Path (HeldKarp):         " + heldkarp.getBestPath(city1.getName())); // idk if this is implemented yet
				
				paths++;
				if (branchLength == controlLength)
					branchCorrect++;
				if (greedyLength == controlLength)
					greedyCorrect++;
				if (karpLength == controlLength)
					karpCorrect++;
				
				System.out.println();
			}
			System.out.println("Testcase" + i + " Analysis: ");
			System.out.println("Average path length multiplier compared to best path:");
			System.out.println("Branch and Bound: " + branchMultiple / paths);
			System.out.println("Greedy:           " + greedyMultiple / paths);
			System.out.println("HeldKarp:         " + karpMultiple / paths + "\n");
			System.out.println("Total Runtimes in ms:");
			System.out.println("Brute Force:      " + bruteTime);
			System.out.println("Branch and Bound: " + branchTime);
			System.out.println("Greedy:           " + greedyTime);
			System.out.println("HeldKarp:         " + karpTime + "\n");
			System.out.println("Total Access Count:");
			System.out.println("Brute Force:      " + bruteAccess);
			System.out.println("Branch and Bound: " + branchAccess);
			System.out.println("Greedy:           " + greedyAccess);
			System.out.println("HeldKarp:         " + karpAccess + "\n");
			System.out.println("Success rate in finding best path: ");
			System.out.println("Branch and Bound: " + ((double) branchCorrect / paths) * 100 + "%");
			System.out.println("Greedy:           " + ((double) greedyCorrect / paths) * 100 + "%");
			System.out.println("HeldKarp:         " + ((double) karpCorrect / paths) * 100 + "%\n");
		}
	}

	// NOTE: Only works with complete graphs
	public float getAccuracy(List<City> path, List<City> bestPath) {
		int pathLength = 0;
		int minLength = 0;
		if (path.size() != bestPath.size())
			return 0;
		for (int i = 0; i < path.size() - 1; i++) {
			City curCity = path.get(i);
			City nextCity = path.get(i + 1);
			City curCityB = bestPath.get(i);
			City nextCityB = bestPath.get(i + 1);
			for (Road r : curCity.getNear()) {
				if (r.getCity().getName().equals(nextCity.getName())) {
					pathLength = pathLength + r.getLength();
				}
			}
			for (Road r : curCityB.getNear()) {
				if (r.getCity().getName().equals(nextCityB.getName())) {
					minLength = minLength + r.getLength();
				}
			}
		}
		return (float) pathLength / (float) minLength;
	}
}
