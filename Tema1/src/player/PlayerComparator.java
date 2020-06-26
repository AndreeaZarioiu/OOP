package com.tema1.player;

import java.util.Comparator;

public class  PlayerComparator implements Comparator<Player> {
    @Override
    public final int compare(final Player o1, final Player o2) {
        return (-1) * (o1.getCoins() - o2.getCoins());
    }
}
