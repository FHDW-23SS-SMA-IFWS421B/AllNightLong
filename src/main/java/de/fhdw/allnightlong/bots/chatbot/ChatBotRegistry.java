package de.fhdw.allnightlong.bots.chatbot;

import java.util.HashMap;
import java.util.Map;

public class ChatBotRegistry {
    private Map<String, CreateChatBot> createchatbots = new HashMap<>();

    public void registerChatbot(CreateChatBot createchatbot) {
        createchatbots.put(createchatbot.getName(), createchatbot);
    }

    public CreateChatBot getChatbot(String name) {
        return createchatbots.get(name);
    }
}