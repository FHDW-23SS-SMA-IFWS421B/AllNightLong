package de.fhdw.allnightlong.api;

import de.fhdw.allnightlong.utils.HttpUtil;
import de.fhdw.allnightlong.utils.JsonUtil;
import de.fhdw.allnightlong.config.AppConfig;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WeatherApi implements ApiProcessor {
    private final String API_KEY;

    public WeatherApi() {
        AppConfig config = new AppConfig();
        this.API_KEY = config.getApiKey("weather_api_key");
    }

    @Override
    public String processRequest(String query) {
        if (query.startsWith("wie ist das Wetter in ")) {
            return getCurrentWeather(query.substring("wie ist das Wetter in ".length()));
        } else if (query.startsWith("wie wird das Wetter in ")) {
            return getWeatherForecast(query.substring("wie wird das Wetter in ".length()));
        }
        throw new RuntimeException("Ich verstehe die Anfrage nicht.");
    }

    private String getCurrentWeather(String location) {
        HttpClient client = HttpUtil.createHttpClient();
        HttpRequest request = HttpUtil.createHttpRequest("https://api.openweathermap.org/data/2.5/weather?q=" + location + "&lang=de" + "&appid=" + API_KEY + "&units=metric");
        return processHttpRequest(client, request, location);
    }

    private String getWeatherForecast(String location) {
        HttpClient client = HttpUtil.createHttpClient();
        HttpRequest request = HttpUtil.createHttpRequest("https://api.openweathermap.org/data/2.5/forecast?q=" + location + "&lang=de" + "&appid=" + API_KEY + "&units=metric");
        return processHttpRequestForForecast(client, request, location);
    }

    private String processHttpRequest(HttpClient client, HttpRequest request, String location) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = JsonUtil.parseJson(response.body()); // Using JsonUtil
            JSONObject main = (JSONObject) json.get("main");
            double temp = (double) main.get("temp");
            JSONObject wind = (JSONObject) json.get("wind");
            double windSpeed = (double) wind.get("speed");
            return String.format("In %s ist es aktuell:\n - Temperatur: %.1fºC\n - Windgeschwindigkeit: %.1f m/s", location, temp, windSpeed);

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Abrufen der Wetterdaten: ", e);
        }
    }

    private String processHttpRequestForForecast(HttpClient client, HttpRequest request, String location) {
        StringBuilder forecastText = new StringBuilder();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = JsonUtil.parseJson(response.body()); // Using JsonUtil
            JSONArray list = (JSONArray) json.get("list");
    
            forecastText.append(String.format("Die Wettervorhersage für %s lautet wie folgt:\n", location));
    
            for (int i = 0; i < list.size(); i += 8) {
                JSONObject forecast = (JSONObject) list.get(i);
                JSONObject main = (JSONObject) forecast.get("main");
                double tempMax = (double) main.get("temp_max");
                double tempMin = (double) main.get("temp_min");
    
                JSONObject weather =(JSONObject) ((JSONArray) forecast.get("weather")).get(0);
                String description = (String) weather.get("description");
    
                JSONObject wind = (JSONObject) forecast.get("wind");
                double windSpeed = (double) wind.get("speed");
    
                String dateText = (String) forecast.get("dt_txt");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(dateText);
                SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, dd.MM.yyyy");
    
                forecastText.append(String.format("  - %s - Max: %.1f°C, Min: %.1f°C, %s, Wind: %.1f m/s\n",
                        outputFormat.format(date), tempMax, tempMin, description, windSpeed));
            }

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Abrufen der Wetterdaten: ", e);
        }
        return forecastText.toString();
    }
}
