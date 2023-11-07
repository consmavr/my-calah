package com.mancala.backend.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {

    @Test
    void boardShouldHaveTwelveHouses() {
        Board board = Board.create();
        List<House> houses = board.getHouses();
        Map<PlayerNumber, List<House>> sides = houses.stream().collect(Collectors.groupingBy(House::getOwner));
        assertEquals(sides.get(PlayerNumber.PLAYER_1).size(), 6);
        assertEquals(sides.get(PlayerNumber.PLAYER_2).size(), 6);
    }

    @Test
    void boardShouldHaveTwoStores() {
        Board board = Board.create();
        List<Store> stores = board.getStores();
        Map<PlayerNumber, List<Store>> sides = stores.stream().collect(Collectors.groupingBy(Store::getOwner));
        assertEquals(sides.get(PlayerNumber.PLAYER_1).size(), 1);
        assertEquals(sides.get(PlayerNumber.PLAYER_2).size(),1 );
    }

    @Test
    void boardShouldHaveTwoPlayers() {
        Board board = Board.create();
        Board.Players players = board.getPlayers();
        assertEquals(players.player1().playerNumber(), PlayerNumber.PLAYER_1);
        assertEquals(players.player2().playerNumber(), PlayerNumber.PLAYER_2);
    }

    @Test
    void housesShouldHaveMutualOpposites() {
        Board board = Board.create();
        Board.Players players = board.getPlayers();
        List<House> housesOne = players.player1().houses();
        List<House> housesTwo = players.player2().houses();
        assertEquals(housesOne.get(0).getOpposite().get(), housesTwo.get(5));
        assertEquals(housesOne.get(1).getOpposite().get(), housesTwo.get(4));
        assertEquals(housesOne.get(2).getOpposite().get(), housesTwo.get(3));
        assertEquals(housesOne.get(3).getOpposite().get(), housesTwo.get(2));
        assertEquals(housesOne.get(4).getOpposite().get(), housesTwo.get(1));
        assertEquals(housesOne.get(5).getOpposite().get(), housesTwo.get(0));
        assertEquals(housesTwo.get(0).getOpposite().get(), housesOne.get(5));
        assertEquals(housesTwo.get(1).getOpposite().get(), housesOne.get(4));
        assertEquals(housesTwo.get(2).getOpposite().get(), housesOne.get(3));
        assertEquals(housesTwo.get(3).getOpposite().get(), housesOne.get(2));
        assertEquals(housesTwo.get(4).getOpposite().get(), housesOne.get(1));
        assertEquals(housesTwo.get(5).getOpposite().get(), housesOne.get(0));
    }

    @Test
    void allPitsShouldFormACycle() {
        Board board = Board.create();
        Pit first = board.getHouses().get(0);
        Pit pit = first;

        Set<Pit> all = new HashSet<>();
        all.add(pit);

        for (int i=0; i<14; i++) {
            pit = pit.next();
            all.add(pit);
        }

        assertEquals(pit, first);
        assertEquals(all.size(), 14);
    }
}
