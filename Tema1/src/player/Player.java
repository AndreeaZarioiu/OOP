package com.tema1.player;

import com.tema1.common.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public
abstract class Player {
    @SuppressWarnings("checkstyle:OperatorWrap")
    public int coins = Constants.DEFAULT_COINS;
    private int index;
    private int profit = 0;
    public List<Integer> cards = new ArrayList<>();
    public List<Integer> bag = new ArrayList<>();
    private List<Integer> declaredBag = new ArrayList<>();
    private List<Integer> illegalStuff = new ArrayList<>();
    private HashMap<Integer, Integer> tradedGoods = new HashMap<>();
    private int bribe = 0;
    private String strategy;
    public final List<Integer> getDeclaredBag() {
        return declaredBag;
    }
    public final void addDeclaredBag(final int good) {
        declaredBag.add(good);
    }
    public final void addAllDeclaredBag(final List<Integer> good) {
        declaredBag.addAll(good);
    }
    public final List<Integer> getIllegalStuff() {
        return illegalStuff;
    }
    public final void addIllegalStuff(final int illegal) {
         illegalStuff.add(illegal);
    }
    public final int getProfit() {
        return profit;
    }
    public final void setProfit(final int profit) {
        this.profit = profit;
    }
    public final int getIndex() {
        return index;
    }
    public final void setIndex(final int index) {
        this.index = index;
    }
    public final String getStrategy() {
        return strategy;
    }
    public final void setStrategy(final String strategy) {
        this.strategy = strategy;
    }
    public final int getBribe() {
        return bribe;
    }
    public final void setBribe(final int bribe) {
        this.bribe = bribe;
    }
    public final HashMap<Integer, Integer> getTradedGoods() {
        return tradedGoods;
    }
    public final void setTradedGoods(final HashMap<Integer, Integer> tradedGoods) {
        this.tradedGoods = tradedGoods;
    }
    public final int getCoins() {
        return coins;
    }
    public final void setCoins(final int coins) {
        this.coins = coins;
    }
    public Player() { }
    public
    Player(final List<Integer> cards, final String strategy, final int index) {
        this.cards = cards;
        this.strategy = strategy;
        this.index = index;
    }

}
