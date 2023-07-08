package com.example.GameApp.service;

import com.example.GameApp.exception.ErrorResponse;
import com.example.GameApp.exception.NameNotFoundException;
import com.example.GameApp.model.Game;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static com.example.GameApp.exception.ErrorCode.NAME_NOT_FOUND_ERROR_CODE;
import static com.example.GameApp.exception.ErrorMessage.NAME_NOT_FOUND_MESSAGE;

@Service
public class GameServiceImpl implements GameService {
    private Map<String, Game> gameCache = new ConcurrentHashMap<>();

    @Override
    public Game createGame(Game game) {
        gameCache.put(game.getName(), game);
        return game;
    }

    @Override
    public Game getGame(String name) {
        if(!gameCache.containsKey(name)) {
            ErrorResponse errorResponse = new ErrorResponse(NAME_NOT_FOUND_MESSAGE.getValue(), NAME_NOT_FOUND_ERROR_CODE.getValue());
            throw new NameNotFoundException(errorResponse);
        }
        return gameCache.get(name);

    }

    @Override
    public List<Game> getAllGames() {
        return new ArrayList<>(gameCache.values());
    }

    @Override
    public Game updateGame(String name, Game game) {
        if (gameCache.containsKey(name)) {
            gameCache.put(name, game);
            return game;
        }
        return null;
    }

    @Override
    public void deleteGame(String name) {
        gameCache.remove(name);
    }
}
