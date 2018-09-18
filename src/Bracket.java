
public abstract class Bracket {
	abstract int getNumberOfTeams();
	abstract int getNumberOfRounds();
	abstract int getNumberOfMatchesInRounds();
	abstract String[][] getTeamsInMatch(int round, int matchNumber);
	abstract void setMatchWinner();
}
