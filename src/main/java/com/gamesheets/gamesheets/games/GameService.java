package com.gamesheets.gamesheets.games;

import com.gamesheets.gamesheets.games.dto.Game;
import com.gamesheets.gamesheets.games.integration.ExternalGameAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final ExternalGameAPI externalGameAPI;

    public List<Game> getGames(String title, int page, int pageSize) {
        try {
            externalGameAPI.searchForGamesByTitle(title, page, pageSize);
        } catch (ExternalGameAPIException e) {

        }
    }
}
