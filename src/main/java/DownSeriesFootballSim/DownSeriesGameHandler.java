package DownSeriesFootballSim;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DownSeriesGameHandler implements RequestHandler<Map<String, Object>, LambdaResponse> {

	@Override
	public LambdaResponse handleRequest(Map<String, Object> map, Context context) {
		Map<String, String> paramMap;
		if (map.containsKey("queryStringParameters")) {
			paramMap = (Map<String, String>)(map.get("queryStringParameters"));
		}
		else {
			paramMap = new HashMap<>();
			for (Map.Entry<String, Object> e : map.entrySet()) {
				paramMap.put(e.getKey(), e.getValue().toString());
			}
		}
		return jsonResponse(handleFromMap(paramMap));
	}

	private Double[][] handleFromMap(Map<String, String> map) {
		System.out.println("MAP = " + map.toString());

		int reps = Integer.parseInt(map.get("reps"));
		String team1 = map.get("team1");
		String team2 = map.get("team2");
		double dsr1 = Double.parseDouble(map.get("dsr1"));
		double dsr2 = Double.parseDouble(map.get("dsr2"));

		return DownSeriesFootballGameRunner.executeGames(reps, team1, dsr1, team2, dsr2, false);
	}

	private LambdaResponse jsonResponse(Double[][] values) {
		String body = Arrays.deepToString(values);
		int statusCode = 200;
		boolean encoded = false;
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		LambdaResponse resp = new LambdaResponse(body, statusCode, headers, encoded);
		return resp;
	}
}
