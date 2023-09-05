package de.fhdw.allnightlong.utils;
import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class HttpUtil {
    public static HttpClient createHttpClient() {
        return HttpClient.newHttpClient();
    }

    public static HttpRequest createHttpRequest(String url) {
        return HttpRequest.newBuilder().uri(URI.create(url)).build();
    }

        public static HttpRequest createHttpRequest(String url, String method, String body, Map<String, String> headers) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method(method, HttpRequest.BodyPublishers.ofString(body));
        
        headers.forEach(builder::header);

        return builder.build();
    }

    public static HttpResponse<String> sendRequest(HttpRequest request) throws Exception {
        HttpClient client = createHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
