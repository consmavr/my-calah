package com.mancala.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HouseTest {
    @Test
    public void shouldStoreSeedsInHouse() {
        Pit house = new House(PlayerNumber.PLAYER_1, 4);
        assertEquals(house.count(), 4);
    }

    @Test
    public void storeShouldBeginEmpty() {
        Pit endZone = new Store(PlayerNumber.PLAYER_1);
        assertEquals(endZone.count(), 0);
    }

    @Test
    public void shouldPointToTheNextPit() {
        Pit a = new House(PlayerNumber.PLAYER_1, 4);
        Store b = new Store(PlayerNumber.PLAYER_1);
        House c = new House(PlayerNumber.PLAYER_1, 4);

        a.setNext(b);
        b.setNext(c);

        assertEquals(a.next(), b);
        assertEquals(b.next(), c);
    }

    @Test
    public void shouldBeAbleToTakeSeedsFromHouse() {
        House house = new House(PlayerNumber.PLAYER_1, 4);
        assertEquals(house.count(), 4);

        Integer taken = house.take();

        assertEquals(taken, 4);
        assertEquals(house.count(), 0);
    }

    @Test
    public void shouldBeAbleToSowSeedInHouse() {
        House house = new House(PlayerNumber.PLAYER_1, 0);
        house.sow();
        assertEquals(house.count(), 1);
    }

    @Test
    public void shouldBeAbleToSowSeedInStore() {
        Store store = new Store(PlayerNumber.PLAYER_1);
        store.sow();
        assertEquals(store.count(),1);
    }

    @Test
    public void shouldBeAbleToSowMultipleSeedsInStore() {
        Store store = new Store(PlayerNumber.PLAYER_1);
        store.sow(4);
        assertEquals(store.count(), 4);
    }
}
