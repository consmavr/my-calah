package com.mancala.backend.model;

import java.util.List;

public record Player(PlayerNumber playerNumber, List<House> houses, Store store) {

        public Pit turn(int houseNum) {
                House house = getHouse(houseNum);
                checkHasSeeds(house);
                Pit pit = takeTurn(house);
                if (shouldCaptureOpposite(pit)) {
                        store.sow(pit.take());
                        store.sow(pit.capture());
                }
                return pit;
        }

        public boolean isOutOfMoves() {
                return houses.stream().allMatch(House::isEmpty);
        }

        public void finish() {
                for (House house: houses) {
                        store.sow(house.take());
                }
        }

        public int score() {
                return store.count();
        }

        private boolean shouldCaptureOpposite(Pit pit) {
                return pit.count() == 1 && pit.getOpposite().isPresent();
        }

        private void checkHasSeeds(House house) {
                if (house.isEmpty()) {
                        throw new IllegalArgumentException("House must have seeds to take turn");
                }
        }

        private Pit takeTurn(House house) {
                Integer seeds = house.take();
                Pit pit = house;
                while (seeds > 0) {
                        pit = pit.next();
                        if (pit.isSowable(playerNumber)) {
                                seeds--;
                                pit.sow();
                        };
                }
                return pit;
        }

        private House getHouse(int houseNum) {
                if (houseNum < 0 || houseNum > houses.size() - 1) {
                        throw new IllegalArgumentException("House number must be between 0 and " + (houses.size() - 1));
                }
                return this.houses.get(houseNum);
        }

}
