/**
 * [Bracket.java]
 * Bracket object that outlines the methods necessary for a single or double elimination bracket object
 * Authors: Yili Liu and Brian Li
 * September 21, 2018
 */

public abstract class Bracket {

	public abstract int getNumberOfTeams(); //To return total number of teams in the tournament
	public abstract int getNumberOfRounds(); //To return the total number of rounds in the tournament
	public abstract int getNumberOfMatchesInRound(int round); //To return the number of matches in the given round
	public abstract String[][] getTeamsInMatch(int round, int matchNumber); //To store the possible teams that can play in a given match
	public abstract void setMatchWinner(String teamName, int round, int matchNumber); //To update the bracket with the winner of a match once a match ends

} //End of class

