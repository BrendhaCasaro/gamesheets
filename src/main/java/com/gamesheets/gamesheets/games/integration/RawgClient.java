package com.gamesheets.gamesheets.games.integration;

import com.gamesheets.gamesheets.games.model.Game;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RawgClient {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String apiKey;

    public RawgClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public Game[] searchGames(String title, int page, int pageSize) {
        String baseURL = "https://api.rawg.io/api/games";
        title = title.replaceAll(" ", "%20");

        String params = "?key=" + apiKey + "&search=" + title + "&page=" + page + "&page_size=" + pageSize;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseURL + params)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
