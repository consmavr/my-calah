package com.mancala.backend.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {
    @Test
    void playerShouldSowSeedsOnTurn() {
        House last = new House(PlayerNumber.PLAYER_1, 0);
        House middle = new House(PlayerNumber.PLAYER_1,0);
        House first = new House(PlayerNumber.PLAYER_1,2);
        Store end = new Store(PlayerNumber.PLAYER_1,0);

        first.setNext(middle).setNext(last).setNext(end);

        Player player = new Player(PlayerNumber.PLAYER_1, List.of(first, middle, last), end);
        Pit landed = player.turn(0);

        assertEquals(landed, last);
        assertEquals(first.count(), 0);
        assertEquals(middle.count(), 1);
        assertEquals(last.count(), 1);
    }

    @Test
    void playerCannotChooseEmptyHouse() {
        assertThrows(IllegalArgumentException.class, () -> {
            Player player = new Player(PlayerNumber.PLAYER_1, List.of(new House(PlayerNumber.PLAYER_1, 0)), new Store(PlayerNumber.PLAYER_1));
            player.turn(1);
        });
    }

    @Test
    void playerCannotChooseHouseBelowRange() {
        assertThrows(IllegalArgumentException.class, () -> {
            Player player = new Player(PlayerNumber.PLAYER_1, List.of(new House(PlayerNumber.PLAYER_1, 0)), new Store(PlayerNumber.PLAYER_1));
            player.turn(0);
        });
    }

    @Test
    void playerSkipsOpponentsStore() {
        House myHouse = new House(PlayerNumber.PLAYER_1,3);
        Store myStore = new Store(PlayerNumber.PLAYER_1);
        House opponentHouse = new House(PlayerNumber.PLAYER_2,0);
        Store opponentStore = new Store(PlayerNumber.PLAYER_2);

        myHouse.setNext(myStore)
                .setNext(opponentHouse)
                .setNext(opponentStore)
                .setNext(myHouse);

        Player player = new Player(PlayerNumber.PLAYER_1, List.of(myHouse), myStore);
        Pit landed = player.turn(0);

        assertEquals(landed, myHouse);
        assertEquals(myHouse.count(), 1);
        assertEquals(myStore.count(), 1);
        assertEquals(opponentHouse.count(), 1);
        assertEquals(opponentStore.count(), 0);
    }

}
