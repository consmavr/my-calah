package com.mancala.backend.service;

import com.mancala.backend.model.Board;
import com.mancala.backend.model.Game;
import com.mancala.backend.model.Pit;
import com.mancala.backend.model.Player;
import com.mancala.backend.model.PlayerNumber;
import com.mancala.backend.view.GameState;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {
        final Map<String, Game> cache = new ConcurrentHashMap<>();

        public GameState createGame() {
                cache.clear();
                Game game = Game.create(Board.create());
                cache.put(game.getGameId(), game);
                GameState gameState = new GameState(
                                boardToList(game.getBoard()),
                                game.getPlayer().playerNumber().toString(),
                                game.getStatus().toString(),
                                game.getGameId());
                return gameState;
        }

        public GameState pickPot(String gameId, PlayerNumber playerNumber, int house){
                Game game = getGameById(gameId);
                Game.Result result = game.move(playerNumber, house);
                GameState gameState = new GameState(
                                boardToList(result.board()),
                                game.getPlayer().playerNumber().toString(),
                                game.getStatus().toString(),
                                gameId);

                return gameState;
        }

        public GameState getExistingGame() {
                Optional<Game> gameOptional =  getGameFromCache();
                if(gameOptional.isEmpty()){
                        return null;
                }

                Game game = gameOptional.get();
                GameState gameState = new GameState(
                        boardToList(game.getBoard()),
                        game.getPlayer().playerNumber().toString(),
                        game.getStatus().toString(),
                        game.getGameId());
                return gameState;
        }

        public GameState getGame(String gameId) {
                Game game = getGameById(gameId);
                GameState gameState = new GameState(boardToList(
                                game.getBoard()),
                                game.getPlayer().playerNumber().toString(),
                                game.getStatus().toString(),
                                game.getGameId());
                return  gameState;
        }
        public Game getGameById(String id) {
                return cache.get(id);
        }

        private List<Integer> boardToList(Board board){
                Player player1 = board.getPlayers().player1();
                Player player2 = board.getPlayers().player2();

                List<Pit> pits = new ArrayList<>(player1.houses());
                pits.add(player1.store());
                pits.addAll(player2.houses());
                pits.add(player2.store());

                return pits.stream()
                                .map(Pit::count)
                                .toList();
        }

        private Optional<Game> getGameFromCache(){
                if (!cache.isEmpty()) {
                        // Get an iterator over the map's entries.
                        Iterator<Map.Entry<String, Game>> iterator = cache.entrySet().iterator();

                        // Get the first entry.
                        Map.Entry<String, Game> entry = iterator.next();
                        Game game = entry.getValue();
                        return Optional.of(game);

                } else {
                        return Optional.empty();
                }
        }
}
