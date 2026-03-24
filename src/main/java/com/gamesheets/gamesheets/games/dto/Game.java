package com.gamesheets.gamesheets.games.dto;

// titulo,
// imagem de capa
// Data de lançamento
// Plataforma
// Metacritic

import java.util.List;

public record Game(String title,
                   String backgroundImage,
                   String released,
                   List<String> platforms,
                   Integer metacritic
) {
}
