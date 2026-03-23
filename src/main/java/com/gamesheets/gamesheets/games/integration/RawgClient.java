package com.gamesheets.gamesheets.games.integration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesheets.gamesheets.games.model.Game;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RawgClient {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String apiKey;

    public RawgClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public Game searchGames(String title, int page, int pageSize) {
        try {
            String baseURL = "https://api.rawg.io/api/games";
            String encodedTitle = URLEncoder.encode(title, "UTF-8");

            String params = "?key=" + apiKey + "&search=" + encodedTitle + "&page=" + page + "&page_size=" + pageSize;
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseURL + params)).build();


            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                Game game = objectMapper.readValue(response.body(), Game.class);
                System.out.println(game);
                return game;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
