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

	Slot lastSlot;

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
			int rounds = 0;
			
			for (int i = 0; i < size; i++) {
				Slot s = slots.pop();
				
				if (s.leftSlot != null) {
					slots.add(s.leftSlot);
				}
				if (s.rightSlot != null) {
					slots.add(s.rightSlot);
				}
				
				rounds++;
			}
			
			numRounds++;
			matchesInRound.push(rounds);
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
		return null;
	}

	@Override
	void setMatchWinner(String teamName, int round, int matchNumber) {
		
	}
}
