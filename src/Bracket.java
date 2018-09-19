
public abstract class Bracket {
	abstract int getNumberOfTeams();
	abstract int getNumberOfRounds();
	abstract int getNumberOfMatchesInRounds(int round);
	abstract String[][] getTeamsInMatch(int round, int matchNumber);
	abstract void setMatchWinner(String teamName, int round, int matchNumber);
}
