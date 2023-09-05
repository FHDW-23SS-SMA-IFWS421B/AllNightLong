package de.fhdw.allnightlong.api;

public interface WeatherService {
    String getCurrentWeather(String location);
    String getWeatherForecast(String location);
}
