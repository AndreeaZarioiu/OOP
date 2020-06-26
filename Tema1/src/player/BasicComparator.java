package com.tema1.player;
import java.util.Comparator;

import com.tema1.goods.GoodsFactory;
public class BasicComparator implements Comparator<Integer> {
    private GoodsFactory getData = GoodsFactory.getInstance();
    @Override
    public final int compare(final Integer o1, final Integer o2) {
        return o2 - o1;
    }
}

