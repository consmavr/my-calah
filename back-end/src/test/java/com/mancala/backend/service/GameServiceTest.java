package com.mancala.backend.service;

import com.mancala.backend.model.GameStatus;
import com.mancala.backend.model.PlayerNumber;
import com.mancala.backend.view.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class GameServiceTest {

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService();
    }

    @Test
    void testCreateGame() {
        // Call the createGame() method
        GameState gameState = gameService.createGame();

        // Assert that the game state is not null and initial values are correct
        assertNotNull(gameState);
        assertEquals(gameState.getStatus(), GameStatus.ACTIVE.toString());
        assertEquals(gameState.getPlayer(), PlayerNumber.PLAYER_1.toString());
        assertEquals(gameState.getBoard().size(), 14);
    }

    @Test
    void testGetGame() {
        // Initialize game
        GameState initializedGame = gameService.createGame();
        String gameId = initializedGame.getGameId();

        GameState gameState = gameService.getGame(gameId);

        // Assert that the game state is not null
        assertNotNull(gameState);

        // Assert that the game state is not null and initial values are correct
        assertNotNull(gameState);
        assertEquals(gameState.getStatus(), GameStatus.ACTIVE.toString());
        assertEquals(gameState.getPlayer(), PlayerNumber.PLAYER_1.toString());
        assertEquals(gameState.getBoard().size(), 14);
    }

    @Test
    void testGetExistingGame() {
        // Initialize game
        GameState initializedGame = gameService.createGame();

        GameState gameState = gameService.getExistingGame();

        // Assert that the game state is not null
        assertNotNull(gameState);

        // Assert that the game state is not null and initial values are correct
        assertNotNull(gameState);
        assertEquals(gameState.getStatus(), GameStatus.ACTIVE.toString());
        assertEquals(gameState.getPlayer(), PlayerNumber.PLAYER_1.toString());
        assertEquals(gameState.getBoard().size(), 14);
    }

    @Test
    void testGetExistingGameWhenNoGameExists() {
        // Try to retrieve existing game
        GameState gameState = gameService.getExistingGame();

        // Assert that the game state is null
        assertNull(gameState);
     }

    @Test
    void testPickPot() {
        GameState initializedGame = gameService.createGame();

        // Call the pickPot() method
        GameState gameState = gameService.pickPot(initializedGame.getGameId(), PlayerNumber.PLAYER_1, 5);

        // Assert that the game state is not null
        assertNotNull(gameState);

        // Assert that the game state contains the updated values
        assertEquals(initializedGame.getGameId(), gameState.getGameId());
        assertEquals(gameState.getStatus(), GameStatus.ACTIVE.toString());
        assertEquals(gameState.getPlayer(), PlayerNumber.PLAYER_2.toString());
        assertEquals(gameState.getGameId(), initializedGame.getGameId());
        assertEquals(gameState.getBoard().size(), 14);
        assertEquals(gameState.getGameId(), initializedGame.getGameId());

        // Assert that the selected pit has now 0 seeds
        assertEquals(gameState.getBoard().get(5), 0);
        // Assert that the player store has 1 seed
        assertEquals(gameState.getBoard().get(6), 1);
        // Assert that the pit after the player 1 store has 7 seeds
        assertEquals(gameState.getBoard().get(7), 7);
    }

    @Test
    void testPickPotAndContinuesPlaying() {
        GameState initializedGame = gameService.createGame();

        // Call the pickPot() method
        GameState gameState = gameService.pickPot(initializedGame.getGameId(), PlayerNumber.PLAYER_1, 0);

        // Assert that the game state is not null
        assertNotNull(gameState);

        // Assert that the game state contains the updated values
        assertEquals(initializedGame.getGameId(), gameState.getGameId());
        assertEquals(gameState.getStatus(), GameStatus.ACTIVE.toString());
        assertEquals(gameState.getPlayer(), PlayerNumber.PLAYER_1.toString());
        assertEquals(gameState.getGameId(), initializedGame.getGameId());
        assertEquals(gameState.getBoard().size(), 14);
        assertEquals(gameState.getGameId(), initializedGame.getGameId());

        // Assert that the selected pit has now 0 seeds
        assertEquals(gameState.getBoard().get(0), 0);
        // Assert that the pit after the selected one has now 7 seeds
        assertEquals(gameState.getBoard().get(1), 7);
        // Assert that the player store has 1 seed
        assertEquals(gameState.getBoard().get(6), 1);
        // Assert that the pit after the player 1 store has 6 seeds
        assertEquals(gameState.getBoard().get(7), 6);
    }

    @Test
    void testPickPotPlayer2PlaysFirst() {
        GameState initializedGame = gameService.createGame();

        assertThrows(IllegalStateException.class, ()-> {
                    gameService.pickPot(initializedGame.getGameId(), PlayerNumber.PLAYER_2, 1);
                });
    }

    @Test
    void testPickPotOutOfRange() {
        GameState initializedGame = gameService.createGame();

        assertThrows(IllegalArgumentException.class,
                ()->{
                    gameService.pickPot(initializedGame.getGameId(), PlayerNumber.PLAYER_1, 20);
                });
    }
}