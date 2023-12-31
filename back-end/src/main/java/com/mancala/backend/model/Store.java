package com.mancala.backend.model;

public class Store  extends Pit {

        Store(PlayerNumber owner) {
                super(owner, 0);
        }

        Store(PlayerNumber owner, int seeds) {
                super(owner, seeds);
        }

        public void sow(int i) {
                seeds += i;
        }

        boolean isSowable(PlayerNumber player) {
                return player.equals(owner);
        }

}
