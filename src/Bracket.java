/**
 * [Bracket.java]
 * Bracket object that outlines the methods necessary for a single or double elimination bracket object
 * Authors: Yili Liu and Brian Li
 * September 21, 2018
 */

public abstract class Bracket {

	/**
	 * getNumberOfTeams
	 * This method returns the number of teams in the tournament
	 * @return numTeams, An integer representing the number of teams in the tournament
	 */ 
	public abstract int getNumberOfTeams();

	/**
	 * getNumberOfRounds
	 * This method returns the number of rounds in the tournament
	 * @return numRounds, An integer representing the number of rounds in the tournament
	 */
	public abstract int getNumberOfRounds();

	/**
	 * getNumberOfMatchesInRound
	 * This method returns the number of matches in a given round
	 * @param round, An integer representing the round
	 * @return numMatchesInRound, An integer representing the number of matches in the round
	 */
	public abstract int getNumberOfMatchesInRound(int round);

	/**
	 * getTeamsInMatch
	 * This method returns the teams that could play in a match
	 * @param round, An integer representing the round number of the match being searched for
	 * @param matchNumber, An integer representing the match number of the match being searched for
	 * @return teamNames, A 2D String array that holds the names of the teams that could be playing on each side of the match
	 */
	public abstract String[][] getTeamsInMatch(int round, int matchNumber);

	/**
	 * setMatchWinner
	 * This method updates the bracket once the winner of a match has been determined (with the winner inputted)
	 * @param teamName, A String representing the winning team's name
	 * @param round, An integer representing the round number of the match whose winner has been determined
	 * @param matchNumber, An integer representing the match number of the match whose winner has been determined
	 */
	public abstract void setMatchWinner(String teamName, int round, int matchNumber);

	/**
	 * getMatchBracket
	 * This method returns whether the match with the inputted round and match number is in the winners or losers bracket
	 * @param round, An integer that represents the round number of the match being searched for
	 * @param matchNumber, An integer that represents the match number of the match being searched for
	 * @return 0, since any match in a single elimination bracket is in the winner's bracket
	 */
	public abstract int getMatchBracket(int round, int matchNumber);

} //End of class

