package de.fhdw.allnightlong.api;


import de.fhdw.allnightlong.bots.chatbot.ChatBotRegistry;
import de.fhdw.allnightlong.bots.chatbot.CreateChatBot;
import de.fhdw.allnightlong.utils.CompilerUtil;
public class ChatbotLoaderApi implements ChatbotLoader {

    private final ChatBotRegistry registry;

    public ChatbotLoaderApi(ChatBotRegistry registry) {
        this.registry = registry;
    }

    public void loadChatbot(String sourceCode, String className) throws Exception {
        // Kompilieren des Quellcodes zum Laden des Chatbots
        Class<?> chatbotClass = CompilerUtil.compile(sourceCode, className);

        // Instanz des Chatbots erstellen
        CreateChatBot createchatbot = (CreateChatBot) chatbotClass.getDeclaredConstructor().newInstance();

        // Chatbot im System registrieren
        registry.registerChatbot(createchatbot);
    }
}