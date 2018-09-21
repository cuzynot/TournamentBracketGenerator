import java.util.ArrayList;

class BracketTest {

	public static void main(String[] args) {

		ArrayList<Team> teams = new ArrayList<Team>();
		SingleGenerator generator;

		for (int i = 1; i < 8; i++) {
			teams.add(new Team(Integer.toString(i), i));
		}

		generator = new SingleGenerator(teams, true);

		SingleBracket bracket = (SingleBracket)generator.getBracket();

		System.out.println("Round 1, match 1: " + bracket.getTeamsInMatch(2, 1)[0][0] + " " + bracket.getTeamsInMatch(2, 1)[1][0]);
		
		bracket.setMatchWinner("5", 1, 2);
		
		System.out.println("Round 1, match 1: " + bracket.getTeamsInMatch(2, 1)[0][0] + " " + bracket.getTeamsInMatch(2, 1)[1][0]);
	}

}