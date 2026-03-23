package com.gamesheets.gamesheets;

import com.gamesheets.gamesheets.games.integration.RawgClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GamesheetsApplication {


    static void main(String[] args) {
        // SpringApplication.run(GamesheetsApplication.class, args);
        RawgClient rawgClient = new RawgClient("d7baaaff9cfa4d50a43661a5147b0e52");
        rawgClient.searchGames("batman", 1, 1);
    }

}
