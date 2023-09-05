package de.fhdw.allnightlong.bots.wikibot;

import de.fhdw.allnightlong.api.WikiApi;
import de.fhdw.allnightlong.bots.chatbot.ChatBot;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;




public class Wikibot implements ChatBot {

    private WikiApi wikiApi;

    public Wikibot(WikiApi wikiApi) {
        this.wikiApi = wikiApi;
    }

    @Override
    public String respond(String query) {
        StringBuilder response = new StringBuilder();
        response.append("Folgende Informationen habe ich zu " + query + " gefunden:\n");

        JSONArray searchResults = wikiApi.search(query);
        if (searchResults != null) {
            for (Object result : searchResults) {
                JSONObject page = (JSONObject) result;
                response.append(" - ").append(page.get("title")).append(": ").append(page.get("description")).append("\n");
            }
        } else {
            response.append("Keine Informationen gefunden.");
        }

        return response.toString();
    }
}
