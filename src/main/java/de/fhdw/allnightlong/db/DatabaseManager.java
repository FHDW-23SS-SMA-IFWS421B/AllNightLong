package de.fhdw.allnightlong.db;

import java.util.List;

public interface DatabaseManager {


        void connect();
        void disconnect();
        void saveMessage(String username, String message);
        List<String> getRecentMessages(String username, int limit);
    }

