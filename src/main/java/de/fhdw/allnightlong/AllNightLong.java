package de.fhdw.allnightlong;

import de.fhdw.allnightlong.api.TranslateApi;
import de.fhdw.allnightlong.api.WeatherApi;
import de.fhdw.allnightlong.api.WikiApi;
import de.fhdw.allnightlong.bots.chatbot.ChatBotManager;
import de.fhdw.allnightlong.bots.chatbot.SimpleChatBotManager;
import de.fhdw.allnightlong.bots.translatebot.TranslateBot;
import de.fhdw.allnightlong.bots.weatherbot.WeatherBot;
import de.fhdw.allnightlong.bots.wikibot.Wikibot;
import de.fhdw.allnightlong.chat.ChatManager;
import de.fhdw.allnightlong.db.DatabaseManager;
import de.fhdw.allnightlong.db.SqlLiteManager;
import de.fhdw.allnightlong.user.Authenticator;
import de.fhdw.allnightlong.user.SimpleAuthenticator;
import de.fhdw.allnightlong.user.SimpleUserManager;
import de.fhdw.allnightlong.user.UserManager;

import java.util.List;
import java.util.Scanner;


public class AllNightLong {
    public static void main(String[] args) {
        UserManager userManager = new SimpleUserManager();
        Authenticator authenticator = new SimpleAuthenticator();
        Scanner scanner = new Scanner(System.in);
        ChatBotManager chatBotManager = new SimpleChatBotManager();
        chatBotManager.registerBot("wetterbot", new WeatherBot(new WeatherApi()));
        chatBotManager.registerBot("wikibot", new Wikibot(new WikiApi()));
        chatBotManager.registerBot("translatebot", new TranslateBot(new TranslateApi()));

        DatabaseManager databaseManager = new SqlLiteManager();
        databaseManager.connect();
        ChatManager chatManager = new ChatManager(databaseManager);

        System.out.println("Bitte Benutzernamen eingeben:");
        String username = scanner.nextLine();

        System.out.println("Bitte Passwort eingeben:");
        char[] passwordArray;
        if (System.console() != null) {
            passwordArray = System.console().readPassword();
        } else {
            passwordArray = scanner.nextLine().toCharArray();
        }
        String password = new String(passwordArray);

        if (!authenticator.authenticate(userManager, username, password)) {
            System.out.println("Falscher Benutzername oder Passwort. Beenden.");
            scanner.close();
            return;
        }
        System.out.println("Herzlich Willkommen");
        List<String> recentMessages = chatManager.getRecentMessages(username);
        System.out.println("Ihr Chatverlauf:");
        for (String msg : recentMessages) {
            System.out.println(msg);
        }
        while (true) {
            System.out.print("user: ");
            String line = scanner.nextLine();
            String output = chatBotManager.execute(line, username, chatManager);
            System.out.println(output);
            if (line.equalsIgnoreCase("@exit")) {
                scanner.close();
                break;
            }

        }

    }

}
