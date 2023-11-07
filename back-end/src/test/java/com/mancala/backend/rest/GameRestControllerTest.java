package com.mancala.backend.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.mancala.backend.model.PlayerNumber;
import com.mancala.backend.service.GameService;
import com.mancala.backend.view.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;


class GameRestControllerTest {

    @Mock
    private GameService gameService;

    private GameRestController gameRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        gameRestController = new GameRestController(gameService);
    }

    // Test the createGame() method
    @Test
    void testCreateGame() {
        // Create a mock GameState object
        GameState gameState = new GameState();

        // When the gameService.createGame() method is called, return the mock GameState object
        when(gameService.createGame()).thenReturn(gameState);

        // Call the createGame() method
        var responseEntity = gameRestController.createGame();

        // Assert that the response status code is 200 OK
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Assert that the response body is the mock GameState object
        assertEquals(gameState, responseEntity.getBody());
    }

    // Test the getGame() method
    @Test
    void testGetGame() {
        // Create a mock GameState object
        GameState gameState = new GameState();

        // When the gameService.getGame() method is called with the specified ID, return the mock GameState object
        when(gameService.getGame("1234567890")).thenReturn(gameState);

        // Call the getGame() method with the specified ID
        ResponseEntity<GameState> responseEntity = gameRestController.getGame("1234567890");

        // Assert that the response status code is 200 OK
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Assert that the response body is the mock GameState object
        assertEquals(gameState, responseEntity.getBody());
    }

    // Test the pickPot() method
    @Test
    void testPickPot() {
        // Create a mock GameState object
        GameState gameState = new GameState();

        // When the gameService.pickPot() method is called with the specified game ID, player number, and house number, return the mock GameState object
        when(gameService.pickPot("1234567890", PlayerNumber.PLAYER_1, 1)).thenReturn(gameState);

        // Call the pickPot() method with the specified game ID, player number, and house number
        ResponseEntity<GameState> responseEntity = gameRestController.pickPot("1234567890", PlayerNumber.PLAYER_1, 1);

        // Assert that the response status code is 200 OK
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Assert that the response body is the mock GameState object
        assertEquals(gameState, responseEntity.getBody());
    }


    // Test the getExistingGame() method
    @Test
    void testGetExistingGame() {
        // Create a mock GameState object
        GameState gameState = new GameState();

        // When the gameService.getExistingGame() method is called, return the mock GameState object of existing game
        when(gameService.getExistingGame()).thenReturn(gameState);

        // Call the getGame() method with the specified ID
        ResponseEntity<GameState> responseEntity = gameRestController.getExistingGame();

        // Assert that the response status code is 200 OK
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Assert that the response body is the mock GameState object
        assertEquals(gameState, responseEntity.getBody());
    }
}