package de.fhdw.allnightlong.bots.chatbot;

import java.util.List;
import java.util.Map;

import de.fhdw.allnightlong.chat.ChatManager;

public interface ChatBotManager {
    void registerBot(String name, ChatBot bot);
    String handleQuery(String botName, String query);
    boolean activateBot(String botName);
    boolean deactivateBot(String botName);
    List<String> getRegisteredBotNames();
    Map<String, Boolean> getBotStatus();
    String execute(String command, String username, ChatManager chatManager);
}
