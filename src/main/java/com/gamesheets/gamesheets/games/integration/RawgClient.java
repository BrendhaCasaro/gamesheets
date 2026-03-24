package com.gamesheets.gamesheets.games.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesheets.gamesheets.games.model.Game;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RawgClient {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String apiKey;

    public RawgClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<Game> searchGames(String title, int page, int pageSize) {
        try {
            String baseURL = "https://api.rawg.io/api/games";
            String encodedTitle = URLEncoder.encode(title, "UTF-8");

            String params = "?key=" + apiKey + "&search=" + encodedTitle + "&page=" + page + "&page_size=" + pageSize;
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseURL + params)).build();


            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("The status code not is 200");
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode results = mapper.readTree(response.body()).path("results");
            List<Game> games = new ArrayList<>();

            for (JsonNode item : results) {
                String gameTitle = item.path("name").asText();
                String backgroundImage = item.path("background_image").asText();
                String released = item.path("released").asText();
                Integer metacritic = item.path("metacritic").isNull() ? null : item.path("metacritic").asInt();

                List<String> plataforms = new ArrayList<>();
                for (JsonNode p : item.path("plataforms")) {
                    String plataformName = p.path("plataform").path("name").asText();
                    plataforms.add(plataformName);
                }
                games.add(new Game(gameTitle, backgroundImage, released, plataforms, metacritic));
            }
            return games;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
