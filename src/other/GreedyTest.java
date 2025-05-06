package other;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import algorithms.Greedy;

public class GreedyTest {

    public static void main(String[] args) {
		Map map = parseData("src\\other\\testset3");
        Greedy greedy = new Greedy();
        greedy.findShortestPath(map, "a");
        System.out.println(greedy.getBestCost());
        System.out.println(greedy.getBestPath().toString());
    }

	private static Map parseData(String filename) {
		Map map = new Map();
		try {
			Scanner input = new Scanner(new File(filename));
			while (input.hasNextLine()) {
				String[] data = input.nextLine().split(" ");
				map.addRoad(data[0], data[1], Integer.parseInt(data[2]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return map;
	}


}
