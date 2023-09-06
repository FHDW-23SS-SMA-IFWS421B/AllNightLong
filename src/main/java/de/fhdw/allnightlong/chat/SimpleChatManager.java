package de.fhdw.allnightlong.chat;

import de.fhdw.allnightlong.db.DatabaseManager;

import java.util.List;

public class SimpleChatManager implements ChatManager {

    private final DatabaseManager databaseManager;

    public SimpleChatManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void saveMessage(String username, String message) {
        databaseManager.saveMessage(username, message);
    }

    public List<String> getRecentMessages(String username) {
        return databaseManager.getRecentMessages(username, 100);
    }
}
