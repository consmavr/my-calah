package com.mancala.backend.view;

import com.mancala.backend.model.Board;
import com.mancala.backend.model.Game;
import com.mancala.backend.model.Player;

import java.util.List;

public class GameState {
        private List<Integer> board;

        private String player;

        private String status;

        private String gameId;

        public GameState() {
        }

        public GameState(List<Integer> board, String player, String status, String gameId) {
                this.board = board;
                this.player = player;
                this.status = status;
                this.gameId = gameId;
        }

        public List<Integer> getBoard() {
                return board;
        }

        public void setBoard(List<Integer> board) {
                this.board = board;
        }

        public String getPlayer() {
                return player;
        }

        public void setPlayer(String player) {
                this.player = player;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public String getGameId() {
                return gameId;
        }

        public void setGameId(String gameId) {
                this.gameId = gameId;
        }
}
