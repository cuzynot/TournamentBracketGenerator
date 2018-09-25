import java.util.ArrayList;

class BracketTest {

	public static void main(String[] args) {
		
		long first = System.currentTimeMillis();

		ArrayList<Team> teams = new ArrayList<Team>();
		SingleGenerator generator;

		for (int i = 1; i < 1000; i++) {
			teams.add(new Team(Integer.toString(i), i));
		}

		generator = new SingleGenerator(teams, true);

		SingleBracket bracket = (SingleBracket)generator.getBracket();
		
		for (int i = 1; i <= bracket.getNumberOfRounds(); i++) {
			for (int j = 1; j <= bracket.getNumberOfMatchesInRound(i); j++) {
				String[][] s = bracket.getTeamsInMatch(i, j);
			}
		}
		
		long last = System.currentTimeMillis();
		
		System.out.println(last - first);
	}

}