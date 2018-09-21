import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class SingleBracket2 extends Bracket{
	int numRounds;
	int numTeams;
	HashMap<Integer, Integer> numMatchesInRound;

	private class Slot {
		ArrayList<Team> teams1, teams2;
		Slot leftSlot, rightSlot;
		int round, matchNumber;

		Slot(ArrayList<Team> team1, ArrayList<Team> team2, int round, int matchNumber) {
			this.teams1 = team1;
			this.teams2 = team2;
			this.round = round;
			this.matchNumber = matchNumber;
		}
	}

	private Slot lastSlot;

	SingleBracket2(ArrayList<Team> teams) {
		numTeams = teams.size();
		numMatchesInRound = new HashMap<Integer, Integer>();
		numRounds = (int)(Math.ceil(Math.log(teams.size()) / Math.log(2)));

		// recursive call to construct the binary tree
		lastSlot = constructSlots(teams, numRounds, 1);

		// breadth first search to get 1. number of rounds and 2. number of matches in each round
		bfs();
	}

	private Slot constructSlots(ArrayList<Team> teams, int round, int matchNumber) {
		Slot s;

		// split current list of teams into the top and bottom halves
		int mid = teams.size() / 2;
		ArrayList<Team> teams1 = truncate(teams, 0, mid);
		ArrayList<Team> teams2 = truncate(teams, mid, teams.size());

		s = new Slot(teams1, teams2, round, matchNumber);

		if (teams1.size() > 1) {
			s.leftSlot = constructSlots(teams1, round - 1, matchNumber * 2 - 1);
		}
		if (teams2.size() > 1) {
			s.rightSlot = constructSlots(teams2, round - 1, matchNumber * 2);
		}

		System.out.println("slot " + s.round + " " + s.matchNumber);

		System.out.print("team1 "); for (int i = 0; i < s.teams1.size(); i++) System.out.print(s.teams1.get(i).getName());
		System.out.println();
		System.out.print("team2 "); for (int i = 0; i < s.teams2.size(); i++) System.out.print(s.teams2.get(i).getName());
		System.out.println();
		System.out.println();

		return s;
	}

	private void bfs() {
		LinkedList<Slot> slots = new LinkedList<Slot>();
		slots.add(lastSlot);

		Stack<Integer> matchesInRound = new Stack<Integer>();

		while (!slots.isEmpty()) {
			int size = slots.size();

			for (int i = 0; i < size; i++) {
				Slot s = slots.pop();

				if (s.leftSlot != null) {
					slots.add(s.leftSlot);
				}
				if (s.rightSlot != null) {
					slots.add(s.rightSlot);
				}
			}

			matchesInRound.push(size);
		}

		int roundCounter = 1;
		while (!matchesInRound.isEmpty()) {
			int m = matchesInRound.pop();
			numMatchesInRound.put(roundCounter, m);

			roundCounter++;
		}
	}

	private ArrayList<Team> truncate (ArrayList<Team> teams, int left, int right){
		ArrayList<Team> newList = new ArrayList<Team>();

		for (int i = left; i < right; i++) {
			newList.add(teams.get(i));
		}

		return newList;
	}

	private Slot getSlot(int round, int matchNumber) {
		Stack<Boolean> stack = new Stack<Boolean>();
		for (int i = round; i < numRounds; i++) {
			if (matchNumber % 2 == 0) {
				matchNumber /= 2;
				stack.push(true);
			} else {
				matchNumber = (matchNumber + 1) / 2;
				stack.push(false);
			}
		}

		// go backwards from last slot to find the specified slot
		Slot s = lastSlot;

		while (!stack.isEmpty()) {
			if (stack.pop()) {
				s = s.rightSlot;
			} else {
				s = s.leftSlot;
			}
		}

		return s;
	}

	// check exception

	@Override
	public int getNumberOfTeams() {
		return numTeams;
	}

	@Override
	public int getNumberOfRounds() {
		return numRounds;
	}

	@Override
	public int getNumberOfMatchesInRounds(int round) {
		if (numMatchesInRound.get(round) == null) { // not within range of the tournament
			return 0;
		}
		return numMatchesInRound.get(round);
	}

	@Override
	public String[][] getTeamsInMatch(int round, int matchNumber) {
		Slot s = getSlot(round, matchNumber);
		
		String[][] teamNames = new String[2][];

		if (s != null) {
			// fill 2d array
			teamNames[0] = new String[s.teams1.size()];
			teamNames[1] = new String[s.teams2.size()];
			
			for (int i = 0; i < s.teams1.size(); i++) {
				teamNames[0][i] = s.teams1.get(i).getName();
			}
			for (int i = 0; i < s.teams2.size(); i++) {
				teamNames[1][i] = s.teams2.get(i).getName();
			}
		}

		return teamNames;
	}

	@Override
	public void setMatchWinner(String teamName, int round, int matchNumber) {
		ArrayList<Boolean> path = new ArrayList<Boolean>();
		for (int i = round; i < numRounds; i++) {
			if (matchNumber % 2 == 0) {
				matchNumber /= 2;
				path.add(true);
			} else {
				matchNumber = (matchNumber + 1) / 2;
				path.add(false);
			}
		}

		// go backwards from last slot to find the specified slot
		Slot s = lastSlot;

		for (int i = path.size() - 1; i >= 0; i--) {
			if (path.get(i)) {
				s = s.rightSlot;
			} else {
				s = s.leftSlot;
			}
		}

		ArrayList<Team> teamsRemove = new ArrayList<Team>();

		boolean foundInTeams1 = false;
		boolean foundInTeams2 = false;
		

		// loop through teams1 to search for the team
		for (int i = 0; i < s.teams1.size(); i++) {
			if ((s.teams1.get(i).getName().equals(teamName))) {
				foundInTeams1 = true;
				break;
			}
		}

		// if not found in teams1
		if (!foundInTeams1) {
			// loop through teams2
			for (int i = 0; i < s.teams2.size(); i++) {
				if ((s.teams2.get(i).getName().equals(teamName))) {
					foundInTeams2 = true;
					break;
				}
			}
		}
		
		// remove other teams that lost and add to the teams-to-remove list
		if (foundInTeams1 || foundInTeams2) {
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
		}

		if (foundInTeams1 || foundInTeams2) {
			s = lastSlot;

			for (int i = path.size() - 1; i >= 0; i--) {
				for (int j = 0; j < teamsRemove.size(); j++) {
					s.teams1.remove(teamsRemove.get(j));
					s.teams2.remove(teamsRemove.get(j));
				}
				
				if (path.get(i)) {
					s = s.rightSlot;
				} else {
					s = s.leftSlot;
				}
			}
		}
	}

	// int getMatchNumber(Team team)
	// int getRoundNumber(Team team)

}

