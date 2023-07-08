package com.example.GameApp.service;

import com.example.GameApp.model.Game;
import org.springframework.stereotype.Component;

import java.util.List;

public interface GameService {
    Game createGame(Game game);
    Game getGame(String name);
    List<Game> getAllGames();
    Game updateGame(String name, Game game);
    void deleteGame(String name);
}
