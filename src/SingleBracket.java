import java.util.ArrayList;

public class SingleBracket extends Bracket{
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

	Slot lastSlot;

	SingleBracket(ArrayList<Team> teams) {
		lastSlot = constructSlots(teams, 1, 1);
	}

	private Slot constructSlots(ArrayList<Team> teams, int round, int matchNumber) {
		Slot s;

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

	@Override
	int getNumberOfTeams() {
		return 0;
	}

	@Override
	int getNumberOfRounds() {
		return 0;
	}

	@Override
	int getNumberOfMatchesInRounds() {
		return 0;
	}

	@Override
	String[][] getTeamsInMatch(int round, int matchNumber) {
		return null;
	}

	@Override
	void setMatchWinner() {
	}
}
