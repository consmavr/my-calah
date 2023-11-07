package com.mancala.backend.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    @Test
    void playerOneShouldTakeFirstTurn() {
        Game game = Game.create(Board.create());
        Player player = game.getActivePlayer();
        assertEquals(player.playerNumber(), PlayerNumber.PLAYER_1);
    }

//    @Test
//    void shouldRejectMoveByInactivePlayer() {
//        Game game = Game.create(Board.create());
//        assertThrows(IllegalStateException.class, () -> game.move(TWO, 1));
//    }

    @Test
    void shouldAllowPlayerToSow() {
        Game game = Game.create(Board.create());
        Game.Result result = game.move(PlayerNumber.PLAYER_1, 0);
        var board =  result.board();
        var boardToString = boardToString(board);
        assertEquals(boardToString, "0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 6, 6, 0");
    }

    @Test
    void shouldAllowAlternateTurns() {
        Game game = Game.create(Board.create());

        Game.Result result = game.move(PlayerNumber.PLAYER_1, 1);
        var boardToString = boardToString(result.board());
        assertEquals(boardToString, "6, 0, 7, 7, 7, 7, 1, 7, 6, 6, 6, 6, 6, 0");


        assertEquals(result.next(), PlayerNumber.PLAYER_2);

        result = game.move(PlayerNumber.PLAYER_2, 0);
        boardToString = boardToString(result.board());
        assertEquals(boardToString, "7, 0, 7, 7, 7, 7, 1, 0, 7, 7, 7, 7, 7, 1");

        assertEquals(result.next(), PlayerNumber.PLAYER_1);

        assertEquals(result.status(), GameStatus.ACTIVE);
    }

    @Test
    void shouldGetAnotherTurnWhenLandingInOwnStore() {
        Game game = Game.create(Board.create());
        Game.Result result = game.move(PlayerNumber.PLAYER_1, 0);
        var boardToString = boardToString(result.board());
        assertEquals(boardToString, "0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 6, 6, 0");

        assertEquals(result.next(), PlayerNumber.PLAYER_1);
        assertEquals(result.status(), GameStatus.ACTIVE);
    }

    @Test
    void shouldCaptureOppositeWhenLandingInOwnEmptyHouse() {
        var board = Board.seedBoard(
                List.of(4, 4, 4, 4, 0, 5),
                1,
                List.of(5, 5, 4, 4, 4, 4),
                0
        );
        Game game = Game.create(board);
        var boardToString = boardToString(game.getBoard());
        assertEquals(boardToString, "4, 4, 4, 4, 0, 5, 1, 5, 5, 4, 4, 4, 4, 0");


        Game.Result result = game.move(PlayerNumber.PLAYER_1, 0);
        boardToString = boardToString(result.board());
        assertEquals(boardToString, "0, 5, 5, 5, 0, 5, 7, 5, 0, 4, 4, 4, 4, 0");

    }

    @Test
    void shouldFinishGameWhenOnePlayerHasNoSeeds() {
        var board = Board.seedBoard(
                List.of(0, 0, 0, 0, 0, 1),
                24,
                List.of(4, 4, 4, 4, 4, 3),
                0
        );
        Game game = Game.create(board);
        var boardToString = boardToString(game.getBoard());
        assertEquals(boardToString, "0, 0, 0, 0, 0, 1, 24, 4, 4, 4, 4, 4, 3, 0");


        Game.Result result = game.move(PlayerNumber.PLAYER_1, 5);
        Board.Players players = board.getPlayers();
        assertEquals(players.player1().score(), 25);
        assertEquals(players.player2().score(), 23);
        assertEquals(result.status(), GameStatus.PLAYER_ONE_WIN);
    }

    @Test
    void shouldAllowDrawWhenSameSeeds() {
        var board = Board.seedBoard(
                List.of(0, 0, 0, 0, 0, 1),
                23,
                List.of(4, 4, 4, 4, 4, 4),
                0
        );
        Game game = Game.create(board);
        var boardToString = boardToString(game.getBoard());
        assertEquals(boardToString, "0, 0, 0, 0, 0, 1, 23, 4, 4, 4, 4, 4, 4, 0");

        Game.Result result = game.move(PlayerNumber.PLAYER_1, 5);
        Board.Players players = board.getPlayers();
        assertEquals(players.player1().score(), 24);
        assertEquals(players.player2().score(), 24);
        assertEquals(result.status(), GameStatus.DRAW);
    }

    private String boardToString(Board board){
        Player player1 = board.getPlayers().player1();
        Player player2 = board.getPlayers().player2();

        List<Pit> pits = new ArrayList<>(player1.houses());
        pits.add(player1.store());
        pits.addAll(player2.houses());
        pits.add(player2.store());

        var boardList =  pits.stream()
                .map(Pit::count)
                .toList();
        return boardList.stream().map(i->i.toString()).collect(Collectors.joining(", "));
    }


}
