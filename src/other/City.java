package other;

import java.util.ArrayList;

public class City {
	String name;
	int visited;
	int min;
	ArrayList<Road> near;

	public String getName() {
		return name;
	}

	public ArrayList<Road> getNear() {
		return near;
	}

	public String toString() {
		return name;
	}
}