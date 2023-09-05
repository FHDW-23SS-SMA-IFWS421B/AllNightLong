package de.fhdw.allnightlong.api;

import de.fhdw.allnightlong.api.Wikiprocessor.Wikiprocessor;
import de.fhdw.allnightlong.utils.HttpUtil;
import de.fhdw.allnightlong.utils.JsonUtil;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WikiApi implements Wikiprocessor {
    private String cleanQuery(String query) {
        if (query.toLowerCase().startsWith("was ist ")) {
            return query.substring(8);
        }
        return query;
    }

    @Override
    public JSONArray search(String query) {
        query = cleanQuery(query);
        HttpClient client = HttpUtil.createHttpClient();
        HttpRequest request = HttpUtil.createHttpRequest("https://de.wikipedia.org/w/rest.php/v1/search/page?q=" + query + "&limit=3");
        return processHttpRequest(client, request);
    }

    private JSONArray processHttpRequest(HttpClient client, HttpRequest request) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = JsonUtil.parseJson(response.body());  // Using JsonUtil
            return (JSONArray) json.get("pages");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
