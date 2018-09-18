import java.util.ArrayList;

public class SingleBracket extends Bracket{
	private class Slot {
		ArrayList<Team> teams1, teams2;
		Slot leftSlot, rightSlot;
		int round, matchNumber;

		Slot(ArrayList<Team> team1, ArrayList<Team> team2) {
			this.teams1 = team1;
			this.teams2 = team2;
		}
	}

	Slot lastSlot;

	SingleBracket(ArrayList<Team> teams) {
		lastSlot = constructSlots(teams);
	}

	private Slot constructSlots(ArrayList<Team> teams) {
		Slot s;

		int mid = teams.size() / 2;

		ArrayList<Team> teams1 = truncate(teams, 0, mid);
		ArrayList<Team> teams2 = truncate(teams, mid, teams.size());

		s = new Slot(teams1, teams2);

		if (teams1.size() > 1) {
			s.leftSlot = constructSlots(teams1);
		}
		if (teams2.size() > 1) {
			s.rightSlot = constructSlots(teams2);
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
