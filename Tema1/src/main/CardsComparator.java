package com.tema1.main;
import java.util.Comparator;

import com.tema1.goods.GoodsFactory;
public class CardsComparator implements Comparator<Integer> {
    private GoodsFactory getData = GoodsFactory.getInstance();
    @Override
    public final int compare(final Integer o1, final Integer o2) {
        if (getData.getGoodsById(o2).getProfit() == getData.getGoodsById(o1).getProfit()) {
            return o2 - o1;
        }
        return getData.getGoodsById(o2).getProfit() - getData.getGoodsById(o1).getProfit();
    }
}

