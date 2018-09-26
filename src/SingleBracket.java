/**
 * [SingleBracket.java]
 * Single elimination bracket object (that can update as a tournament progresses)
 * Authors: Yili Liu and Brian Li
 * September 21, 2018
 */

//Import statements
import java.util.ArrayList;

public class SingleBracket extends Bracket{
	int numRounds; //Holds the number of rounds in the tournament
	int numTeams; //Holds the number of teams in the tournament
	int[] numMatchesInRound; //Holds the number of matches in each round
	private ArrayList<Slot> slots[]; //Holds all the slots (each slot stores a match, or possible match)

	/**
	 * SingleBracket
	 * Constructor that makes a single elimination bracket given a list of teams
	 * @param teams, An ArrayList of teams
	 */
	SingleBracket(ArrayList<Team> teams) {
		numTeams = teams.size(); //Store the number of teams
		numRounds = (int)(Math.ceil(Math.log(teams.size()) / Math.log(2)));//Find the number of rounds necessary for the amount of teams
		numMatchesInRound = new int[numRounds + 1]; //Set the size of the array storing the number of matches per round

		slots = new ArrayList[numRounds + 1]; //Set the initital size of the slots list

		//Fill in first spot to be 1-indexed
		for (int i = 1; i <= numRounds; i++) {
			slots[i] = new ArrayList<Slot>();
			slots[i].add(null);
		}

		//Recursive call to construct the binary tree (of matches)
		constructSlots(teams, numRounds);
	} //End of constructor

	//Slot object, which holds the possible teams that can be playing in the corresponding match, its own round and match number, and its children and parent nodes
	private class Slot {
		ArrayList<Team> teams1, teams2;
		Slot leftSlot, rightSlot, parentSlot;
		int round, matchNumber;

		/*
		 * Slots
		 * Constructor that makes a slot object given possible teams, a round number, and a match number
		 * @param team1, An ArrayList of teams that could play on one side of the match
		 * @param team2, An ArrayList of teams that could play on the other side of the match
		 * @param round, An integer to store the match's round number
		 * @param matchNumber, An integer to store the match's match number (within the round)
		 */
		Slot(ArrayList<Team> team1, ArrayList<Team> team2, int round, int matchNumber) {
			this.teams1 = team1;
			this.teams2 = team2;
			this.round = round;
			this.matchNumber = matchNumber;
		}
	} //End of constructor

	/*
	 * constructSlots
	 * This method makes a slot with the given teams and round number, and recursively generates more slots until
	 *   there are enough slots to represent the entire bracket
	 * @param teams, An ArrayList of teams that could play in the match represented by the slot
	 * @param round, An integer to store the match's round number
	 * @return round, A slot made in the method
	 */
	private Slot constructSlots(ArrayList<Team> teams, int round) {
		Slot s; //To store the slot that will be made

		int mid;
		// if there is an even number of teams
		if (teams.size() % 2 == 0) {
			mid = teams.size() / 2;
		} else {
			// if there should be more teams on the bottom
			if ((teams.size() - 1) / 2 % 2 == 0) {
				mid = teams.size() / 2 + 1;
			} else { // if there should be more teams on the top
				mid = teams.size() / 2;
			}
		}

		//Split current list of teams into the top and bottom halves (teams that could play on the two sides of the match)
		ArrayList<Team> teams1 = truncate(teams, 0, mid);
		ArrayList<Team> teams2 = truncate(teams, mid, teams.size());

		//Make a new slot with the list of teams split into two halves
		s = new Slot(teams1, teams2, round, slots[round].size());

		//Make a left slot if there is more than 1 team left on one side of the match
		if (teams1.size() > 1 && round - 1 > 0) {
			s.leftSlot = constructSlots(teams1, round - 1);
			s.leftSlot.parentSlot = s;
		}

		//Make a right slot if there is more than 1 team left on the other side of the match
		if (teams2.size() > 1 && round - 1 > 0) {
			s.rightSlot = constructSlots(teams2, round - 1);
			s.rightSlot.parentSlot = s;
		}

		//Add the slot made to the ArrayList of slots
		slots[round].add(s);

		//Update the counter for number of matches in the round
		numMatchesInRound[round]++;

		//Return the slot made
		return s;
	} //End of constructSlots

	/*
	 * truncate
	 * This method takes an ArrayList of teams and returns a new list with about half of the original teams (half or half +/-1)
	 * @param teams, An ArrayList of teams to be truncated into two
	 * @param left, An integer representing the left index for the section of the teams list being taken
	 * @param right, An integer representing the right index for the section of the teams list being taken
	 * @return An ArrayList of teams, truncated from the original list inputted
	 */
	private ArrayList<Team> truncate (ArrayList<Team> teams, int left, int right){
		//Holds the truncated list of teams
		ArrayList<Team> newList = new ArrayList<Team>();

		//Add the teams indicated by the indices from the original list into the new list
		for (int i = left; i < right; i++) {
			newList.add(teams.get(i));
		}

		//Return the truncated list
		return newList;
	} //End of truncate

	/*
	 * inBounds
	 * This method checks if the round and match numbers are within the bounds of the tournament
	 * @param round, An integer representing the round number of the match being searched for
	 * @param matchNumber, An integer representing the match number of the match being searched for
	 * @return boolean, true if the round and match number have a corresponding match, false if they do not
	 */
	private boolean inBounds(int round, int matchNumber) {
		//Return true if there is a match corresponding to the round and match number
		if (round >= 0 && round <= numRounds && matchNumber > 0 &&matchNumber < slots[round].size()) {
			return true;
		}
		return false;
	} //End of inBounds

	/**
	 * getNumberOfTeams
	 * This method returns the number of teams in the tournament
	 * @return numTeams, An integer representing the number of teams in the tournament
	 */
	@Override
	public int getNumberOfTeams() {
		return numTeams;
	} //End of getNumberOfTeams

	/**
	 * getNumberOfRounds
	 * This method returns the number of rounds in the tournament
	 * @return numRounds, An integer representing the number of rounds in the tournament
	 */
	@Override
	public int getNumberOfRounds() {
		return numRounds;
	} //End of getNumberOfRounds

	/**
	 * getNumberOfMatchesInRound
	 * This method returns the number of matches in a given round
	 * @param round, An integer representing the round
	 * @return numMatchesInRound, An integer representing the number of matches in the round
	 */
	@Override
	public int getNumberOfMatchesInRound(int round) {
		//Return the round number if it exists within the tournament
		if (round <= numRounds) {
			return numMatchesInRound[round];
		}
		return -1;
	} //End of getNumberOfMatches

	/**
	 * getTeamsInMatch
	 * This method returns the teams that could play in a match
	 * @param round, An integer representing the round number of the match being searched for
	 * @param matchNumber, An integer representing the match number of the match being searched for
	 * @return teamNames, A 2D String array that holds the names of the teams that could be playing on each side of the match
	 */
	@Override
	public String[][] getTeamsInMatch(int round, int matchNumber) {
		//Store the names of teams that could be playing in each side of the match
		String[][] teamNames = new String[2][];

		//If the round and match number specified have a corresponding match, fill the array of team names by accessing the slot storing the match's info
		if (inBounds(round, matchNumber)) {
			Slot s = slots[round].get(matchNumber);

			//Set size of each array
			teamNames[0] = new String[s.teams1.size()];
			teamNames[1] = new String[s.teams2.size()];

			//Fill 2d array
			for (int i = 0; i < s.teams1.size(); i++) {
				teamNames[0][i] = s.teams1.get(i).getName();
			}
			for (int i = 0; i < s.teams2.size(); i++) {
				teamNames[1][i] = s.teams2.get(i).getName();
			}
		}

		//Return the array
		return teamNames;
	} //End of getTeamsInMatch

	/**
	 * setMatchWinner
	 * This method updates the bracket once the winner of a match has been determined (with the winner inputted)
	 * @param teamName, A String representing the winning team's name
	 * @param round, An integer representing the round number of the match whose winner has been determined
	 * @param matchNumber, An integer representing the match number of the match whose winner has been determined
	 */
	@Override
	public void setMatchWinner(String teamName, int round, int matchNumber) {
		if (inBounds(round, matchNumber)) {
			Slot s = slots[round].get(matchNumber);

			//ArrayList of teams to store the teams that have to be removed from each slot in the bracket (ones that were eliminated)
			ArrayList<Team> teamsRemove = new ArrayList<Team>();

			boolean foundInTeams1 = false;
			boolean foundInTeams2 = false;

			//Loop through teams1 to search for the team
			for (int i = 0; i < s.teams1.size(); i++) {
				if ((s.teams1.get(i).getName().equals(teamName))) {
					foundInTeams1 = true;
					break;
				}
			}

			//If not found in teams1
			if (!foundInTeams1) {
				//Loop through teams2
				for (int i = 0; i < s.teams2.size(); i++) {
					if ((s.teams2.get(i).getName().equals(teamName))) {
						foundInTeams2 = true;
						break;
					}
				}
			}

			if (foundInTeams1 || foundInTeams2) {
				//Remove other teams that lost and add to the teams-to-remove list
				for (int i = 0; i < s.teams1.size(); i++) {
					if (!(s.teams1.get(i).getName().equals(teamName))) {
						teamsRemove.add(s.teams1.remove(i));
						i--;
					}
				}

				for (int i = 0; i < s.teams2.size(); i++) {
					if (!(s.teams2.get(i).getName().equals(teamName))) {
						teamsRemove.add(s.teams2.remove(i));
						i--;
					}
				}

				//Remove such teams from the slot's parents
				while (s.round < numRounds) {
					s = s.parentSlot;
					for (int j = 0; j < teamsRemove.size(); j++) {
						s.teams1.remove(teamsRemove.get(j));
						s.teams2.remove(teamsRemove.get(j));
					}
				}
			}
		}
	} //End of setMatchWinner

	/**
	 * getMatchBracket
	 * This method returns whether the match with the inputted round and match number is in the winners or losers bracket
	 * @param round, An integer that represents the round number of the match being searched for
	 * @param matchNumber, An integer that represents the match number of the match being searched for
	 * @return 0, since any match in a single elimination bracket is in the winner's bracket
	 */
	@Override
	public int getMatchBracket(int round, int matchNumber) {
		return 0;
	} //End of getMatchBracket

} //End of class