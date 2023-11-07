package com.mancala.backend.model;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Game {

        public record Result(GameStatus status, PlayerNumber next, Board board) {}

        private String gameId;

        private Board board;

        private Player player;

        private GameStatus status;

        public Game() {
        }

        public static Game create(Board board) {
                UUID uuid = UUID.randomUUID();
                String gameId = uuid.toString();
                Game game = new Game();
                game.board = board;
                game.player = board.getPlayers().player1();
                game.status = GameStatus.ACTIVE;
                game.gameId = gameId;
                return game;
        }

        /**
         * Pick seeds as player
         */
        public Result move(PlayerNumber playerNumber, int house) {
                // Check that the active player is making the move
                if (!player.playerNumber().equals(playerNumber)) {
                        throw new IllegalStateException(String.format("Player %s cannot take their turn yet", playerNumber));
                }

                // Perform the move
                Pit landed = player.turn(house);

                // Check if the current player has moves left, otherwise end game
                if (player.isOutOfMoves()) {
                        otherPlayer().finish();
                        status = declareWinner();
                }

                // Switch active player
                player = nextPlayer(landed);
                return new Result(status, player.playerNumber(), board);
        }

        private GameStatus declareWinner() {
                Board.Players players = board.getPlayers();
                int score1 = players.player1().score();
                int score2 = players.player2().score();
                if (score1 > score2) {
                        return GameStatus.PLAYER_ONE_WIN;
                }
                if (score2 > score1) {
                        return GameStatus.PLAYER_TWO_WIN;
                }
                return GameStatus.DRAW;
        }

        public Player nextPlayer(Pit landed) {
                // If last seed landed in player's store don't switch active player
                if (landed.equals(player.store())) {
                        return player;
                }
                return otherPlayer();
        }

        private Player otherPlayer() {
                Board.Players players = board.getPlayers();
                return switch (player.playerNumber()) {
                        case PLAYER_1 -> players.player2();
                        case PLAYER_2 -> players.player1();
                };
        }

        public Player getActivePlayer() {
                return player;
        }

        public Board getBoard() {
                return board;
        }

        public Player getPlayer() {
                return player;
        }

        public void setPlayer(Player player) {
                this.player = player;
        }

        public GameStatus getStatus() {
                return status;
        }

        public String getGameId() {
                return gameId;
        }

}

