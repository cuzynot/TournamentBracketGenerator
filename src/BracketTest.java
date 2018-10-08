class BracketTest {

	public static void main(String[] args) {
		new ManagementSystem();

		//		ArrayList<Team> teams = new ArrayList<Team>();
		//		
		//		for (int i = 1; i <= 4; i++) {
		//			teams.add(new Team(Integer.toString(i), i));
		//		}
		//		
		//		Generator g = new SingleGenerator(teams, false);
		//		Bracket b = g.getBracket();
		//		
		//		printAll(b);
		//		
		//		b.setMatchWinner("1", 1, 1);
		//		
		//		printAll(b);
	}

	static void printAll(Bracket b) {
		System.out.println("---------------------------");

		for (int i = 1; i <= b.getNumberOfRounds(); i++) {
			for (int j = 1; j <= b.getNumberOfMatchesInRound(i); j++) {
				System.out.println("Round " + i + " match " + j);

				String[][] names = b.getTeamsInMatch(i, j);

				System.out.println("possible teams 1");
				for (int k = 0; k < names[0].length; k++) {
					System.out.print(names[0][k] + " ");
				}
				System.out.println();

				System.out.println("possible teams 2");
				for (int k = 0; k < names[1].length; k++) {
					System.out.print(names[1][k] + " ");
				}
				System.out.println();
				System.out.println();
			}
		}
	}

}