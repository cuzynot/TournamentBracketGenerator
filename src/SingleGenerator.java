import java.util.ArrayList;
import java.util.Collections;

public class SingleGenerator extends Generator{

	SingleBracket sb;
	
	SingleGenerator(ArrayList<Team> teams, boolean seed){
		if (seed) {
			teams = organizeSeeds(teams);
		}

		sb = new SingleBracket(teams);
	}
	
	/**
	 * organizeSeeds
	 * This method takes in an arraylist of teams and returns the same list
	 * in order for the bracket to fill in the teams if the bracket is seeded
	 * @param An ArrayList of teams
	 * @return An ArrayList of teams in appropriate seeding order
	 */

	private ArrayList<Team> organizeSeeds (ArrayList<Team> teams) {
		if (teams.size() > 1) {
			// sort
			Collections.sort(teams);
			
			// split
			ArrayList<Team> teams1 = new ArrayList<Team>();
			ArrayList<Team> teams2 = new ArrayList<Team>();
			
			teams1.add(teams.remove(0));

			int count = 0;
			while (!teams.isEmpty()) {
				if ((count / 2) % 2 == 0) {
					teams2.add(teams.remove(0));
				} else {
					teams1.add(teams.remove(0));
				}
				count++;
			}

			// merge
			for (int i = 0; i < teams1.size(); i++) {
				teams.add(teams1.get(i));
			}
			
			for (int i = 0; i < teams2.size(); i++) {
				teams.add(teams2.get(i));
			}
		}

		return teams;
	}
	
	public Bracket getBracket() {
		return sb;
	}
}
