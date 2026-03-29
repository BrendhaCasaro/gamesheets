package com.gamesheets.gamesheets.games.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesheets.gamesheets.games.ExternalGameAPIException;
import com.gamesheets.gamesheets.games.dto.Game;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class RawgClient implements ExternalGameAPI {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String apiKey;
    ObjectMapper mapper = new ObjectMapper();
    String baseURL = "https://api.rawg.io/api/games";
    Logger logger = Logger.getLogger(RawgClient.class.getName());

    public RawgClient(@Value("${rawg.api.key}") String apiKey) {
        this.apiKey = apiKey;
    }

    public List<Game> searchForGamesByTitle(String title, int page, int pageSize) {
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);

        String params = "?key=" + apiKey + "&search=" + encodedTitle + "&page=" + page + "&page_size=" + pageSize;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseURL + params)).build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.severe("Error while sending request to URL" + request.bodyPublisher().toString());
            throw new ExternalGameAPIException("Error while sending request to URL: " + e.getMessage(), e);
        }

        if (response.statusCode() != 200) {
            logger.severe("Unexpected error in API response" + response.statusCode());
            logger.info(response.body());

            throw new ExternalGameAPIException("Unexpected error in API response");
        }

        JsonNode results;
        try {
            results = mapper.readTree(response.body()).path("results");
        } catch (IOException e) {
            throw new ExternalGameAPIException("Error while parsing response from URL: " + e.getMessage(), e);
        }
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
    }
}
