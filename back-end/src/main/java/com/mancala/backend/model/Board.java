package com.mancala.backend.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static com.mancala.backend.model.PlayerNumber.PLAYER_1;
import static com.mancala.backend.model.PlayerNumber.PLAYER_2;

public class Board {

        public record Players(Player player1, Player player2) {}

        private List<House> houses;

        private List<Store> stores;

        private Players players;

        private Board() {}

        public static Board create() {
                return create(6, 6);
        }

        public static Board create(int numberOfSeeds, int length) {
                var seeds = Stream.generate(() -> numberOfSeeds).limit(length).toList();
                return seedBoard(seeds, 0, seeds, 0);
        }

        /**
         * Initialize board
         */
        public static Board seedBoard(List<Integer> p1Houses, int p1Store, List<Integer> p2Houses, int p2Store) {
                // Build houses
                LinkedList<House> housedPlayerOne = buildHouses(PLAYER_1, p1Houses);
                LinkedList<House> housesPlayerTwo = buildHouses(PLAYER_2, p2Houses);

                addOppositeRelations(housedPlayerOne, housesPlayerTwo);

                Store storeOne = new Store(PLAYER_1, p1Store);
                Store storeTwo = new Store(PLAYER_2, p2Store);

                makeHousesCircular(housedPlayerOne, storeOne, housesPlayerTwo, storeTwo);

                Player playerOne = new Player(PLAYER_1, housedPlayerOne, storeOne);
                Player playerTwo = new Player(PLAYER_2, housesPlayerTwo, storeTwo);

                Board board = new Board();
                board.houses = new ArrayList<>(housedPlayerOne);
                board.houses.addAll(housesPlayerTwo);
                board.stores = List.of(storeOne, storeTwo);
                board.players = new Players(playerOne, playerTwo);

                return board;
        }

        private static LinkedList<House> buildHouses(PlayerNumber playerNumber, List<Integer> seeds) {
                LinkedList<House> houses = new LinkedList<>();
                houses.addLast(new House(playerNumber, seeds.get(0)));

                while (houses.size() < seeds.size()) {
                        House house = new House(playerNumber, seeds.get(houses.size()));
                        houses.getLast().setNext(house);
                        houses.addLast(house);
                }
                return houses;
        }

        /**
         * Link opposite houses of each player
         */
        private static void addOppositeRelations(List<House> housesPlayerOne, List<House> housesPlayerTwo) {
                for (int i=0; i< housesPlayerOne.size(); i++) {
                        House one = housesPlayerOne.get(i);
                        House two = housesPlayerTwo.get(housesPlayerTwo.size() - i - 1);
                        one.setOpposite(two);
                        two.setOpposite(one);
                }
        }

        /**
         * Update houses of each player to point to the next house
         */
        private static void makeHousesCircular(LinkedList<House> housesPlayerOne, Store storeOne, LinkedList<House> housesPlayerTwo, Store storeTwo) {
                housesPlayerOne.getLast().setNext(storeOne);
                storeOne.setNext(housesPlayerTwo.getFirst());
                housesPlayerTwo.getLast().setNext(storeTwo);
                storeTwo.setNext(housesPlayerOne.getFirst());
        }

        public List<House> getHouses() {
                return houses;
        }

        public List<Store> getStores() {
                return stores;
        }

        public Players getPlayers() {
                return players;
        }
}

