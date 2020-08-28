package DownSeriesFootballSim;

import java.util.Random;

public class DownSeriesFootballGameRunner {

	public static void main(String[] args) throws FootballException {
		Random rand = new Random();

		DownSeriesFootballTeam archers = new DownSeriesFootballTeam("ATL", rand, 0.67);
		DownSeriesFootballTeam redWings = new DownSeriesFootballTeam("DRW", rand, 0.67);

		DownSeriesFootballGame game = new DownSeriesFootballGame(archers, redWings, rand);

		int reps = 100000;
		int samples = 99;

		for (int i=1; i<=samples; i++) {
			double ep = game.getExpectedPoints(i, archers, reps);
			System.out.printf("Expected points value for %s ball at the %d in %d attempts is %.2f",
					archers, i, reps, Math.round(ep * 100.0) / 100.0);
			System.out.println();
		}
	}
}
