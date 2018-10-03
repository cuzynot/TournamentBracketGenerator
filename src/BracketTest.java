import java.util.ArrayList;

class BracketTest {

	public static void main(String[] args) {
		// ManagementSystem ManagementSys = 
		// new ManagementSystem();

		ArrayList<Team> teams = new ArrayList<Team>();
		SingleGenerator generator;

		for (int i = 1; i <= 8; i++) {
			teams.add(new Team(Integer.toString(i), i));
		}

		generator = new SingleGenerator(teams, true);

		Bracket bracket = generator.getBracket();

		String[][] s;

		s = bracket.getTeamsInMatch(1, 1);
		for (int i = 0; i < 2; i++) {	
			for (int j = 0; j < s[i].length; j++) {
				System.out.println(s[i][j]);
			}	
		}
		System.out.println();
		s = bracket.getTeamsInMatch(2, 1);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < s[i].length; j++) {
				System.out.println(s[i][j]);
			}
		}
		System.out.println();
		s = bracket.getTeamsInMatch(2, 2);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < s[i].length; j++) {
				System.out.println(s[i][j]);
			}
		}
		
		bracket.setMatchWinner("1", 1, 1);
		bracket.setMatchWinner("2", 3, 1);
		
		System.out.println();

		// new Display(generator.getBracket());


		//		long first = System.currentTimeMillis();
		//
		//		ArrayList<Team> teams = new ArrayList<Team>();
		//		SingleGenerator generator;
		//
		//		for (int i = 1; i < 1000; i++) {
		//			teams.add(new Team(Integer.toString(i), i));
		//		}
		//
		//		generator = new SingleGenerator(teams, true);
		//
		//		SingleBracket bracket = (SingleBracket)generator.getBracket();
		//		
		//		for (int i = 1; i <= bracket.getNumberOfRounds(); i++) {
		//			for (int j = 1; j <= bracket.getNumberOfMatchesInRound(i); j++) {
		//				String[][] s = bracket.getTeamsInMatch(i, j);
		//			}
		//		}
		//		
		//		long last = System.currentTimeMillis();
		//		
		//		System.out.println(last - first);
	}

}