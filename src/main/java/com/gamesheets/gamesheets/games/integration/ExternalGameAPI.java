package com.gamesheets.gamesheets.games.integration;

import com.gamesheets.gamesheets.games.dto.Game;

import java.util.List;

public interface ExternalGameAPI {
    List<Game> searchForGamesByTitle(String title, int page, int pageSize);
}
