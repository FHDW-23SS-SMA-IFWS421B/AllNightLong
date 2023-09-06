package de.fhdw.allnightlong.chat;




import java.util.List;

public interface ChatManager {
    void saveMessage(String username, String message);
    List<String> getRecentMessages(String username);
}