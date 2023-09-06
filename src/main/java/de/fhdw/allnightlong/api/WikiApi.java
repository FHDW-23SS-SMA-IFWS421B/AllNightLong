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
            query= query.replace(" ", "_");
        if (query.toLowerCase().startsWith("was ist ")) {
            return query.substring(8);
        }
        return query;
    }

    @Override
    public JSONArray search(String query) {
        try {
            HttpClient client = HttpUtil.createHttpClient();
            HttpRequest request = HttpUtil.createHttpRequest("https://de.wikipedia.org/w/rest.php/v1/search/page?q=" + cleanQuery(query) + "&limit=3");
            return processHttpRequest(client, request);
        } catch (Exception e) {
            System.err.println("Ein Fehler ist aufgetreten: " + e.getMessage());

            return new JSONArray(); // Rückgabe eines leeren JSONArray
        }
    }

    private JSONArray processHttpRequest(HttpClient client, HttpRequest request) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = JsonUtil.parseJson(response.body());
            JSONArray pages = (JSONArray) json.get("pages");

            for (Object obj : pages) {
                if (obj instanceof JSONObject) {
                    JSONObject page = (JSONObject) obj;
                    Object description = page.get("description");
                    if (description == null || "null".equals(description.toString())) {
                        page.put("description", "Keine Angabe von Wikipedia");
                    }
                }
            }

            return pages;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
