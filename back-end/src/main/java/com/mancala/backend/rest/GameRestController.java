package com.mancala.backend.rest;

import com.mancala.backend.model.PlayerNumber;
import com.mancala.backend.service.GameService;
import com.mancala.backend.view.GameState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping("/api/game")
public class GameRestController {
        private final GameService gameService;

        public GameRestController(GameService gameService) {
                this.gameService = gameService;
        }

        @PostMapping
        public ResponseEntity<GameState> createGame() {
                // Save the new user to the database
                GameState gameState = gameService.createGame();
                return ResponseEntity.ok(gameState);
        }

        @GetMapping("/{id}")
        public ResponseEntity<GameState> getGame(@PathVariable String id){
                GameState gameState = gameService.getGame(id);
                return ResponseEntity.ok(gameState);
        }

        @GetMapping
        public ResponseEntity<GameState> getExistingGame(){
                GameState gameState = gameService.getExistingGame();
                return ResponseEntity.ok(gameState);
        }

        @PutMapping
        public ResponseEntity<GameState> pickPot(@RequestParam String gameId, @RequestParam PlayerNumber playerNumber, @RequestParam int house) {
                GameState gameState = gameService.pickPot(gameId, playerNumber, house);
                return ResponseEntity.ok(gameState);
        }
}
