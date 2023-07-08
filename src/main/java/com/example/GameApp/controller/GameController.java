package com.example.GameApp.controller;

import com.example.GameApp.model.Game;
import com.example.GameApp.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping
    public ResponseEntity<Game> createGame(@Valid @RequestBody Game game) {
        Game createdGame = gameService.createGame(game);
        return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Game> getGame(@PathVariable String name) {
        Game game = gameService.getGame(name);
        if (game != null) {
            return new ResponseEntity<>(game, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Game> updateGame(@PathVariable String name, @Valid @RequestBody Game game) {
        Game updatedGame = gameService.updateGame(name, game);
        if (updatedGame != null) {
            return new ResponseEntity<>(updatedGame, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteGame(@PathVariable String name) {
        gameService.deleteGame(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
