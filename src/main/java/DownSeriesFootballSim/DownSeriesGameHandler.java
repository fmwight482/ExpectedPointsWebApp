package DownSeriesFootballSim;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DownSeriesGameHandler implements RequestHandler<Map<String, Object>, String> {

	@Override
	public String handleRequest(Map<String, Object> map, Context context) {
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

	private String jsonResponse(Double[][] values) {
		JSONObject resp = new JSONObject();
		JSONObject headers = new JSONObject();
		headers.put("Content-Type", "application/json");
		resp.put("StatusCode", 200);
		resp.put("headers", headers);
		resp.put("body", values);
		resp.put("isBase64Encoded", false);
		return resp.toString();
	}
}
