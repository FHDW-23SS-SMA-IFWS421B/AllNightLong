package de.fhdw.allnightlong.api;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.HashMap;

import de.fhdw.allnightlong.config.AppConfig;
import de.fhdw.allnightlong.utils.HttpUtil;
import de.fhdw.allnightlong.utils.JsonUtil;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class TranslateApi implements TranslationProcessor {
         private final String apiKey;
            private final Map<String, String> languageMap;

        public TranslateApi() {
        AppConfig appConfig = new AppConfig();
        this.apiKey = appConfig.getApiKey("translate_api_key");
        languageMap = new HashMap<>();
        languageMap.put("bulgarische", "BG");
        languageMap.put("tschechische", "CS");
        languageMap.put("dänische", "DA");
        languageMap.put("deutsche", "DE");
        languageMap.put("griechische", "EL");
        languageMap.put("englische", "EN"); 
        languageMap.put("englische (britische)", "EN-GB");
        languageMap.put("englische (amerikanische)", "EN-US");
        languageMap.put("spanische", "ES");
        languageMap.put("estnische", "ET");
        languageMap.put("finnische", "FI");
        languageMap.put("französische", "FR");
        languageMap.put("ungarische", "HU");
        languageMap.put("indonesische", "ID");
        languageMap.put("italienische", "IT");
        languageMap.put("japanische", "JA");
        languageMap.put("koreanische", "KO");
        languageMap.put("litauische", "LT");
        languageMap.put("lettische", "LV");
        languageMap.put("norwegische (bokmål)", "NB");
        languageMap.put("niederländische", "NL");
        languageMap.put("polnische", "PL");
        languageMap.put("portugiesische", "PT");  
        languageMap.put("portugiesische (brasilianische)", "PT-BR");
        languageMap.put("portugiesische (europäische)", "PT-PT");
        languageMap.put("rumänische", "RO");
        languageMap.put("russische", "RU");
        languageMap.put("slowakische", "SK");
        languageMap.put("slowenische", "SL");
        languageMap.put("schwedische", "SV");
        languageMap.put("türkische", "TR");
        languageMap.put("ukrainische", "UK");
        languageMap.put("chinesische", "ZH");
     }

     
     private String getLanguageCode(String languageName) {
        return languageMap.getOrDefault(languageName.toLowerCase(), "Unbekannt");
    }

    public String getLanguageCodePublicWrapper(String languageName) {
        return getLanguageCode(languageName);
    }
    @SuppressWarnings("unchecked")
     @Override
    public String translate(String text, String languageCode) {
        JSONObject json = new JSONObject();
        JSONArray textArray = new JSONArray();
        textArray.add(text); 
        json.put("text", textArray);
        json.put("target_lang", languageCode);

        HttpRequest request = HttpUtil.createHttpRequest(
                "https://api-free.deepl.com/v2/translate",
                "POST",
                json.toJSONString(),
                Map.of(
                    "Authorization", "DeepL-Auth-Key " + apiKey,
                    "Content-Type", "application/json"
                )
        );

        try {
            HttpResponse<String> response = HttpUtil.sendRequest(request);
            JSONObject jsonResponse = JsonUtil.parseJson(response.body());
            JSONArray translations = (JSONArray) jsonResponse.get("translations");
            JSONObject translation = (JSONObject) translations.get(0);
            return (String) translation.get("text");
        } catch (Exception e) {
            e.printStackTrace();
            return "Fehler bei der Übersetzung.";
        }
    }

}