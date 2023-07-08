package com.example.GameApp.service;

import com.example.GameApp.model.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private Map<String, Game> gameCache;

    @InjectMocks
    private GameServiceImpl gameServiceImpl;

    static Game game1 = new Game("Jitunu", "08/07/2023 12:23:30", true);
    static Game game2 = new Game("Santanu", "03/07/2023 12:23:30", true);
    static Game game3 = new Game("Swaroop", "09/07/2023 12:23:30", true);

    private static Map<String, Game> dataMap = new HashMap<String, Game>();

    @BeforeAll
    public static void setUp() {
        dataMap.put("Jitunu", game1);
        dataMap.put("Santanu", game2);
        dataMap.put("Swaroop", game3);
    }

    @Test
    void createGameTest() {
        String name = "Santanu";
        when(gameCache.put(any(String.class), any(Game.class))).thenReturn(game2);
        Game game = this.gameServiceImpl.createGame(game2);
        assertEquals(name, game.getName());
        verify(this.gameCache).put(name, game2);
    }

    @Test
    void findAllGameTest() {
        when(gameCache.values()).thenReturn(dataMap.values());
        List<Game> gameList = this.gameServiceImpl.getAllGames();

        assertEquals(3, gameList.size());
        verify(this.gameCache).values();
    }

    @Test
    void findGameBasedOnNameTest() {
        String name = "Santanu";
        when(gameCache.containsKey(any(String.class))).thenReturn(true);
        when(gameCache.get(any(String.class))).thenReturn(dataMap.get(name));
        Game game = this.gameServiceImpl.getGame(name);
        assertEquals(name, game.getName());
        verify(this.gameCache).get(name);
    }

    @Test
    void updateGameTest() {
        String name = "Jitunu Sahoo";
        game1.setName(name);
        when(gameCache.containsKey(any(String.class))).thenReturn(true);
        when(gameCache.put(any(String.class), any(Game.class))).thenReturn(game1);
        Game game = this.gameServiceImpl.updateGame(name, game1);
        assertEquals(name, game.getName());
        verify(this.gameCache).put(name, game1);
    }

    @Test
    void updateGameTestForInvalidName() {
        String name = "Jitunu Sahoo";
        game1.setName(name);
        when(gameCache.containsKey(any(String.class))).thenReturn(false);

        Game game = this.gameServiceImpl.updateGame(name, game1);
        assertNull(game);
        verify(this.gameCache, never()).put(name, game1);
    }

    @Test
    void deleteGameTest() {
        String name = "Jitunu";
        this.gameServiceImpl.deleteGame(name);
        verify(this.gameCache).remove(name);
    }
}
