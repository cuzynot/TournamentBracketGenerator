import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class SingleBracket extends Bracket{
	int numRounds;
	int numTeams;
	HashMap<Integer, Integer> numMatchesInRound;

	private class Slot {
		ArrayList<Team> teams1, teams2;
		Slot leftSlot, rightSlot;
		int round, matchNumber;
		Team winner;

		Slot(ArrayList<Team> team1, ArrayList<Team> team2, int round, int matchNumber) {
			this.teams1 = team1;
			this.teams2 = team2;
			this.round = round;
			this.matchNumber = matchNumber;
		}
	}

	private Slot lastSlot;

	SingleBracket(ArrayList<Team> teams) {
		numTeams = teams.size();
		numMatchesInRound = new HashMap<Integer, Integer>();

		// recursive call to construct the binary tree
		lastSlot = constructSlots(teams, 1, 1);

		// breadth first search to get 1. number of rounds and 2. number of matches in each round
		bfs();
	}

	private void bfs() {
		LinkedList<Slot> slots = new LinkedList<Slot>();
		slots.add(lastSlot);

		numRounds = 0;
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

			numRounds++;
			matchesInRound.push(size);
		}

		int roundCounter = 1;
		while (!matchesInRound.isEmpty()) {
			int m = matchesInRound.pop();
			numMatchesInRound.put(roundCounter, m);

			roundCounter++;
		}
	}

	private Slot constructSlots(ArrayList<Team> teams, int round, int matchNumber) {
		Slot s;

		// split current list of teams into the top and bottom halves
		int mid = teams.size() / 2;
		ArrayList<Team> teams1 = truncate(teams, 0, mid);
		ArrayList<Team> teams2 = truncate(teams, mid, teams.size());

		s = new Slot(teams1, teams2, round, matchNumber);

		if (teams1.size() > 1) {
			s.leftSlot = constructSlots(teams1, round + 1, matchNumber * 2 - 1);
		}
		if (teams2.size() > 1) {
			s.rightSlot = constructSlots(teams2, round + 1, matchNumber * 2);
		}

		return s;
	}

	private ArrayList<Team> truncate (ArrayList<Team> teams, int left, int right){
		ArrayList<Team> newList = new ArrayList<Team>();

		for (int i = left; i < right; i++) {
			newList.add(teams.get(i));
		}

		return newList;
	}

	// check exception

	@Override
	int getNumberOfTeams() {
		return numTeams;
	}

	@Override
	int getNumberOfRounds() {
		return numRounds;
	}

	@Override
	int getNumberOfMatchesInRounds(int round) {
		if (numMatchesInRound.get(round) == null) { // not within range of the tournament
			return 0;
		}
		return numMatchesInRound.get(round);
	}

	@Override
	String[][] getTeamsInMatch(int round, int matchNumber) {

		Slot s = getSlot(round, matchNumber);

		// fill 2d array
		String[][] teamNames = new String[2][Integer.max(s.teams1.size(), s.teams2.size())];

		for (int i = 0; i < s.teams1.size(); i++) {
			teamNames[0][i] = s.teams1.get(i).getName();
		}
		for (int i = 0; i < s.teams2.size(); i++) {
			teamNames[1][i] = s.teams2.get(i).getName();
		}

		return teamNames;
	}

	@Override
	void setMatchWinner(String teamName, int round, int matchNumber) {

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

		if (s.teams1.size() == 1) {
			s.winner = s.teams1.get(0);
		} else {
			s.winner = s.teams2.get(0);
		}

		s = lastSlot;

		for (int i = path.size() - 1; i > 0; i--) {
			for (int j = 0; j < teamsRemove.size(); j++) {
				s.teams1.remove(teamsRemove.get(i));
			}
			for (int j = 0; j < teamsRemove.size(); j++) {
				s.teams2.remove(teamsRemove.get(i));
			}
			if (path.get(i)) {
				s = s.rightSlot;
			} else {
				s = s.leftSlot;
			}
		}
	}

	private Slot getSlot(int round, int matchNumber) {
		Stack<Boolean> stack = new Stack<Boolean>();
		for (int i = round; i < numRounds; i++) {
			if (matchNumber % 2 == 0) {
				matchNumber /= 2;
				stack.add(true);
			} else {
				matchNumber = (matchNumber + 1) / 2;
				stack.add(false);
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

	// int getMatchNumber(Team team)
	// int getRoundNumber(Team team)

}
