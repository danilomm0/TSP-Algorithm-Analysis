/**
 * @author Ethan Huang
 */

package other;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import algorithms.MST;

public class MSTTest {
    public static void main(String[] args) throws InterruptedException {
		Map map = parseData("src\\other\\testset3");
        MST mst = new MST(map);
		long time1 = System.nanoTime();
		mst.buildTree(map);
		long time2 = System.nanoTime();
		System.out.println("Time: " + ((time2 - time1) / 1000000.0) + " ms");
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
