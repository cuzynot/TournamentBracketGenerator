
public class Team implements Comparable<Team>{
	private int seed;
	private String name;
	
	String getName() {
		return name;
	}
	
	int getSeed() {
		return seed;
	}

	@Override
	public int compareTo(Team o) {
		return this.seed - o.seed;
	}
}
