
public class Team implements Comparable<Team>{
	private int seed;
	private String name;
	
	// constructors
	Team(String name){
		this.name = name;
	}
	
	Team(String name, int seed){
		this.name = name;
		this.seed = seed;
	}
	
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
