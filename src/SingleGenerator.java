/**
 * [SingleGenerator.java]
 * Generator for a single elimination bracket, that organizes teams by seeds (if needed) and makes an appropriate bracket
 * Authors: Yili Liu and Brian Li
 * October 09, 2018
 */

//Import statements
import java.util.ArrayList;
import java.util.Collections;

public class SingleGenerator extends Generator{

	private SingleBracket sb; //Holds (access to) the bracket object made

	/**
	 * SingleGenerator
	 * Constructor that creates a single bracket given an arrayList of teams
	 * @param teams, An ArrayList of teams
	 * @param seed, A boolean indicating whether teams are seeded or not
	 */
	SingleGenerator(ArrayList<Team> teams, boolean seed){
		if (seed) {
			//Sort the teams in ascending order by seed
			Collections.sort(teams);
			teams = organizeSeeds(teams);
		}

		sb = new SingleBracket(teams);
	} //End of constructor

	/*
	 * organizeSeeds
	 * This method takes in an arraylist of teams and returns the same list
	 * in order for the bracket to fill in the teams if the bracket is seeded
	 * @param teams, An ArrayList of teams
	 * @return teams, An ArrayList of teams in appropriate seeding order
	 */
	private ArrayList<Team> organizeSeeds (ArrayList<Team> teams) {
		if (teams.size() > 1) {
			//Make two new ArrayLists to hold the split list of teams
			ArrayList<Team> teams1 = new ArrayList<Team>();
			ArrayList<Team> teams2 = new ArrayList<Team>();

			//Always add the first team in the original ArrayList to the first ArrayList
			teams1.add(teams.remove(0));

			int count = 0; //Counter to go through the original arraylist
			while (!teams.isEmpty()) {
				//Sort teams from the original list into the two new lists based on desired matches (based on seeding)
				if ((count / 2) % 2 == 0) {
					teams2.add(teams.remove(0));
				} else {
					teams1.add(teams.remove(0));
				}
				count++;
			}

			//Recurse to further split into more ArrayLists (until each ArrayList has only one team)
			teams1 = organizeSeeds(teams1);
			teams2 = organizeSeeds(teams2);

			//Merge the lists into one list (now ordered by seed)
			for (int i = 0; i < teams1.size(); i++) {
				teams.add(teams1.get(i));
			}

			for (int i = 0; i < teams2.size(); i++) {
				teams.add(teams2.get(i));
			}
		}

		return teams;
	} //End of organizeSeeds


	/**
	 * getBracket
	 * This method returns the bracket made inside the generator
	 * @return sb, A bracket object, representing the bracket made
	 */
	public Bracket getBracket() {
		return sb;
	}
} //End of class