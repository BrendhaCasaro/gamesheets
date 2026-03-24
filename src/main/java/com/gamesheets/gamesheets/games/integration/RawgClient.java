package com.gamesheets.gamesheets.games.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesheets.gamesheets.games.model.Game;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RawgClient {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String apiKey;
    ObjectMapper mapper = new ObjectMapper();
    String baseURL = "https://api.rawg.io/api/games";

    public RawgClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<Game> searchGames(String title, int page, int pageSize) {
        try {
            String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);

            String params = "?key=" + apiKey + "&search=" + encodedTitle + "&page=" + page + "&page_size=" + pageSize;
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseURL + params)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("The status code is not 200");
            }

            JsonNode results = mapper.readTree(response.body()).path("results");
            List<Game> games = new ArrayList<>();

            for (JsonNode item : results) {
                String gameTitle = item.path("name").asText();
                String backgroundImage = item.path("background_image").asText();
                String released = item.path("released").asText();
                Integer metacritic = item.path("metacritic").isNull() ? null : item.path("metacritic").asInt();

                List<String> platforms = new ArrayList<>();
                for (JsonNode p : item.path("platforms")) {
                    String platformName = p.path("platform").path("name").asText();
                    platforms.add(platformName);
                }
                games.add(new Game(gameTitle, backgroundImage, released, platforms, metacritic));
            }
            return games;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
