package com.gamesheets.gamesheets.games;

import com.gamesheets.gamesheets.games.dto.Game;
import com.gamesheets.gamesheets.games.integration.ExternalGameAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final ExternalGameAPI externalGameAPI;

    public List<Game> getGamesDetails(List<String> titles) {
        int page = 1;
        int pageSize = 1;
        List<Game> games = new ArrayList<>();

        for (var title : titles) {
            List<Game> gamesFromAPI = externalGameAPI.searchForGamesByTitle(title, page, pageSize);
            games.addAll(gamesFromAPI);
        }

        return games;
    }
}
