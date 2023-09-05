package de.fhdw.allnightlong.bots.chatbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhdw.allnightlong.chat.ChatManager;

public class SimpleChatBotManager implements ChatBotManager  {
    private Map<String, ChatBot> botMap = new HashMap<>();
    private Map<String, Boolean> botStatus = new HashMap<>();
    

    public void registerBot(String name, ChatBot bot) {
        botMap.put(name, bot);
        botStatus.put(name, false); // False bedeutet, dass der Bot nicht aktiviert ist
    }

    public String handleQuery(String botName, String query) {
        if (Boolean.TRUE.equals(botStatus.get(botName))) {
            ChatBot bot = botMap.get(botName);
            if (bot != null) {
                return bot.respond(query);
            }
            return "Bot nicht gefunden";
        } else {
            return "Bot ist deaktiviert";
        }
    }

    public boolean activateBot(String botName) {
        if (botStatus.containsKey(botName)) {
            botStatus.put(botName, true);
            return true;
        }
        return false;
    }


    public boolean deactivateBot(String botName) {
        if (botStatus.containsKey(botName)) {
            botStatus.put(botName, false);
            return true;
        }
        return false;
    }



    public List<String> getRegisteredBotNames() {
        return new ArrayList<>(botMap.keySet());
    }

    public Map<String, Boolean> getBotStatus() {
        return botStatus;
    }

    public String execute(String command, String username, ChatManager chatManager) {
        if (command.equalsIgnoreCase("@exit")) {
            return "Tschüss!";
        } else if (command.equalsIgnoreCase("list bots")) {
            StringBuilder sb = new StringBuilder("Verfügbare Bots:\n");
            for (Map.Entry<String, Boolean> entry : botStatus.entrySet()) {
                String status = entry.getValue() ? "(enabled)" : "(available)";
                sb.append(entry.getKey()).append(" ").append(status).append("\n");
            }
            return sb.toString();
        } else if (command.startsWith("activate bot ")) {
            String botName = command.substring("activate bot ".length());
            if (activateBot(botName)) {
                return "Bot @" + botName + " aktiviert";
            } else {
                return "Bot @" + botName + " existiert nicht";
            }
        } else if (command.startsWith("deactivate bot ")) {
            String botName = command.substring("deactivate bot ".length());
            if (deactivateBot(botName)) {
                return "Bot @" + botName + " deaktiviert";
            } else {
                return "Bot @" + botName + " existiert nicht";
            }
        } else if (command.startsWith("@")) {
            String[] parts = command.substring(1).split(" ", 2);
            if (parts.length >= 2) {
                String botName = parts[0];
                String query = parts[1];
                chatManager.saveMessage(username, "Anfrage an " + botName + ": " + query);
                String response = handleQuery(botName, query);
                chatManager.saveMessage("bot_" + botName + "_" + username, "Antwort: " + response);
                return "bot " + botName + ": " + response;
            } else {
                return "Sie müssen einen Bot mit '@' ansprechen oder 'activate bot/deactivate bot' verwenden.";
            }
        } else {
            return "Unbekannter Befehl";
        }
    }
}


