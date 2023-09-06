package de.fhdw.allnightlong.bots.translatebot;


import de.fhdw.allnightlong.api.TranslateApi;
import de.fhdw.allnightlong.bots.chatbot.ChatBot;

public class TranslateBot implements ChatBot {
    private final TranslateApi apiProcessor;

    public TranslateBot(TranslateApi apiProcessor) {
        this.apiProcessor = apiProcessor;
    }

    @Override
    public String respond(String query) {
        try {
            String[] parts = query.split(": ", 2);
            String[] langParts = parts[0].split(" ", 5);
            String targetLanguageName = langParts[4];
            String targetLanguageCode = apiProcessor.getLanguageCodePublicWrapper(targetLanguageName);
            String text = parts[1];
            return apiProcessor.translate(text, targetLanguageCode);
        } catch (Exception e) {
            System.err.println("Ein Fehler ist aufgetreten: " + e.getMessage());
            return "Ungültige Anfrage. Format sollte sein: 'Übersetze mir das ins [Sprache]: [Text]'";
        }
    }
}
