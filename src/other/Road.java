package other;

public class Road {
	City city;
	int length;
	private int accessCount;

	Road(City city, int num) {
		this.city = city;
		length = num;
	}

	public City getCity() {
		accessCount++;
		return city;
	}

	public int getLength() {
		accessCount++;
		return length;
	}

	public void resetAccessCount() { accessCount = 0; }

	public int getAccessCount() { return accessCount; }
}
