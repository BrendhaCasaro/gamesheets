package com.gamesheets.gamesheets.games.model;

// titulo,
// imagem de capa
// Data de lançamento
// Plataforma
// Metacritic

public record Game(String title,
                   String backgroundImage,
                   String released,
                   String[] plataforms,
                   Integer metacritic
) {
}
