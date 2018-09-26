/**
 * [Team.java]
 * Class that represents a team competing in a tournament
 * Authors: Yili Liu and Brian Li
 * September 25, 2018
 */

public class Team implements Comparable<Team>{

	private int seed; //Team's seed (smaller int = higher seed)
	private String name;//Team's name

	/**
	 * Team
	 * Constructor that makes a team with the name of the team inputted
	 * @param name, A String representing the name of the team
	 */
	Team(String name){
		this.name = name;
	} //End of constructor

	/**
	 * Team
	 * Constructor that makes a team with the name of the team and its seeding inputted
	 * @param name, a String representing the name of the team
	 * @param seed, an integer representing the team's seed
	 */
	Team(String name, int seed){
		this.name = name;
		this.seed = seed;
	} //End of constructor

	/**
	 * getName
	 * This method returns the team name
	 * @return name, A String representing the name of the team
	 */
	public String getName() {
		return name;
	} //End of getName

	/**
	 * getSeed
	 * This method returns the team's seed
	 * @return seed, An integer representing the seed of the team
	 */
	public int getSeed() {
		return seed;
	} //End of getSeed

	/**
	 * compareTo
	 * This method compares one team's seeding to another, outputting a negative integer if the team running the method 
	 * has a higher seed, and a positive integer if the team running the method has a lower seed
	 * @param o, A Team that is being compared to the team running the method
	 * @return An integer, indicating which team has the higher seed
	 */
	@Override
	public int compareTo(Team o) {
		return this.seed - o.seed;
	} //End of compareTo

} //End of class
