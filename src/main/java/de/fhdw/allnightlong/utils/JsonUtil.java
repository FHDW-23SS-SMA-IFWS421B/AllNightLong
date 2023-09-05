package de.fhdw.allnightlong.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonUtil {
    public static JSONObject parseJson(String body) throws Exception {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(body);
    }
}
