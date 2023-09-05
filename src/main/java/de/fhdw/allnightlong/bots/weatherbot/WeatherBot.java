
package de.fhdw.allnightlong.bots.weatherbot;


import de.fhdw.allnightlong.api.ApiProcessor;
import de.fhdw.allnightlong.bots.chatbot.ChatBot;

public class WeatherBot implements ChatBot {
    private final ApiProcessor apiProcessor;

    public WeatherBot(ApiProcessor apiProcessor) {
        this.apiProcessor = apiProcessor;
    }

    @Override
    public String respond(String query) {
        return apiProcessor.processRequest(query);
    }
}
