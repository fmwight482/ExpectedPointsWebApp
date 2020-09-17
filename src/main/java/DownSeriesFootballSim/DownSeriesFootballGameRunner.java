package DownSeriesFootballSim;

import java.util.Random;

public class DownSeriesFootballGameRunner {

	public static Double[][] executeGames(int reps, String name1, double dsr1, String name2, double dsr2,
	                                boolean verbose) {
		Random rand = new Random();

		DownSeriesFootballTeam team1 = new DownSeriesFootballTeam(name1, dsr1, rand);
		DownSeriesFootballTeam team2 = new DownSeriesFootballTeam(name2, dsr2, rand);

		DownSeriesState.setReps(reps);

		DownSeriesFootballGame game = new DownSeriesFootballGame(team1, team2, rand);

		// teams 0-1, yard lines 1-99, no value at 0 index
		Double[][] epArray = new Double[2][100];

		// Get values for yard lines 1-99
		int samples = 99;

		if (verbose) {
			System.out.println("**** " + team1 + " ****");
		}
		for (int i=1; i<=samples; i++) {
			double ep = game.getExpectedPoints(i, team1, reps);
			epArray[0][i] = ep;
			if (verbose) {
				System.out.printf("Expected points value for %s ball at the %d in %d attempts is %.2f",
						team1, i, reps, Math.round(ep * 100.0) / 100.0);
				System.out.println();
			}
		}

		if (verbose) {
			System.out.println("**** " + team2 + " ****");
		}
		for (int i=1; i<=samples; i++) {
			double ep = game.getExpectedPoints(i, team2, reps);
			epArray[1][i] = ep;
			if (verbose) {
				System.out.printf("Expected points value for %s ball at the %d in %d attempts is %.2f",
						team2, i, reps, Math.round(ep * 100.0) / 100.0);
				System.out.println();
			}
		}

		return epArray;
	}

	public static void main(String[] args) {
		//executeGames(100000, "BAL", 0.79, "OPP", 0.67, true);
		//executeGames(100000, "NE", 0.70, "OPP", 0.61, true);
		//executeGames(100000, "ATL", 0.67, "DRW", 0.67, true);
		executeGames(100000, "KC", 0.8, "HOU", 0.75, true); 
	}
}
