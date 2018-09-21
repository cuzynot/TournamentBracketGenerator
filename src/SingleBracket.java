import java.util.ArrayList;

public class SingleBracket extends Bracket{
	int numRounds;
	int numTeams;
	int[] numMatchesInRound;

	SingleBracket(ArrayList<Team> teams) {
		numTeams = teams.size();
		numRounds = (int)(Math.ceil(Math.log(teams.size()) / Math.log(2)));
		numMatchesInRound = new int[numRounds + 1];

		slots = new ArrayList[numRounds + 1];

		// fill in first spot to be 1-indexed
		for (int i = 1; i <= numRounds; i++) {
			slots[i] = new ArrayList<Slot>();
			slots[i].add(null);
		}

		// recursive call to construct the binary tree
		constructSlots(teams, numRounds);
	}

	private class Slot {
		ArrayList<Team> teams1, teams2;
		Slot leftSlot, rightSlot, parentSlot;
		int round, matchNumber;

		Slot(ArrayList<Team> team1, ArrayList<Team> team2, int round, int matchNumber) {
			this.teams1 = team1;
			this.teams2 = team2;
			this.round = round;
			this.matchNumber = matchNumber;
		}
	}

	private ArrayList<Slot> slots[];

	private Slot constructSlots(ArrayList<Team> teams, int round) {
		Slot s;

		// split current list of teams into the top and bottom halves
		int mid = teams.size() / 2;
		ArrayList<Team> teams1 = truncate(teams, 0, mid);
		ArrayList<Team> teams2 = truncate(teams, mid, teams.size());

		s = new Slot(teams1, teams2, round, slots[round].size());

		if (teams1.size() > 1 && round - 1 > 0) {
			s.leftSlot = constructSlots(teams1, round - 1);
			s.leftSlot.parentSlot = s;
		}
		if (teams2.size() > 1 && round - 1 > 0) {
			s.rightSlot = constructSlots(teams2, round - 1);
			s.rightSlot.parentSlot = s;
		}

		slots[round].add(s);
		numMatchesInRound[round]++;

		System.out.println("slot " + s.round + " " + s.matchNumber);

		System.out.print("team1 "); for (int i = 0; i < s.teams1.size(); i++) System.out.print(s.teams1.get(i).getName());
		System.out.println();
		System.out.print("team2 "); for (int i = 0; i < s.teams2.size(); i++) System.out.print(s.teams2.get(i).getName());
		System.out.println();
		System.out.println();

		return s;
	}

	private ArrayList<Team> truncate (ArrayList<Team> teams, int left, int right){
		ArrayList<Team> newList = new ArrayList<Team>();

		for (int i = left; i < right; i++) {
			newList.add(teams.get(i));
		}

		return newList;
	}

	// check if round and match numbers are in bound
	private boolean inBounds(int round, int matchNumber) {
		if (round >= 0 && round <= numRounds && matchNumber > 0 && matchNumber < slots[round].size()) {
			return true;
		}
		return false;
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
		if (round <= numRounds) {
			return numMatchesInRound[round];
		}
		return -1;
	}

	@Override
	public String[][] getTeamsInMatch(int round, int matchNumber) {
		String[][] teamNames = new String[2][];

		if (inBounds(round, matchNumber)) {
			Slot s = slots[round].get(matchNumber);

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
		if (inBounds(round, matchNumber)) {
			Slot s = slots[round].get(matchNumber);

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

			if (foundInTeams1 || foundInTeams2) {
				// remove other teams that lost and add to the teams-to-remove list
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

				// remove such teams from the slot's parents
				while (s.round < numRounds) {
					s = s.parentSlot;
					for (int j = 0; j < teamsRemove.size(); j++) {
						s.teams1.remove(teamsRemove.get(j));
						s.teams2.remove(teamsRemove.get(j));
					}
				}
			}
		}
	}
}

