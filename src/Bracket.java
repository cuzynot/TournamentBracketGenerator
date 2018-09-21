
public abstract class Bracket {
	public abstract int getNumberOfTeams();
	public abstract int getNumberOfRounds();
	public abstract int getNumberOfMatchesInRounds(int round);
	public abstract String[][] getTeamsInMatch(int round, int matchNumber);
	public abstract void setMatchWinner(String teamName, int round, int matchNumber);
}
