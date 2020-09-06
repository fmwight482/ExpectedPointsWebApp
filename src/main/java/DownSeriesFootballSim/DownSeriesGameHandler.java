package DownSeriesFootballSim;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

public class DownSeriesGameHandler implements RequestHandler<Map<String, String>, Double[][]> {

	@Override
	public Double[][] handleRequest(Map<String, String> map, Context context) {
		int reps = Integer.parseInt(map.get("reps"));
		String team1 = map.get("team1");
		String team2 = map.get("team2");
		double dsr1 = Double.parseDouble(map.get("dsr1"));
		double dsr2 = Double.parseDouble(map.get("dsr2"));

		return DownSeriesFootballGameRunner.executeGames(reps, team1, dsr1, team2, dsr2, false);
	}
}
