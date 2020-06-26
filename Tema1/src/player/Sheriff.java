package com.tema1.player;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Collections;


public
abstract class Sheriff extends Player {
    private GoodsFactory getData = GoodsFactory.getInstance();
    private
    ArrayList<Trader> traders = new ArrayList<>();
    private
    List<Integer> confiscated = new ArrayList<>();
    public
    Sheriff(final List<Integer> cards, final String strategy, final int index) {
        super(cards, strategy, index);
    }
    public
    final void setTraders(final ArrayList<Trader> traders) {
        this.traders = traders; }
    public
    final void getSheriff() {
        if (getStrategy().equals("basic")) {
            baseStrategyS();
        }
        if (getStrategy().equals("bribed")) {
            bribeStrategyS();
        }
        if (getStrategy().equals("greedy")) {
            greedyStrategyS();
        }
        turnCardsToMoney();
    }
    public
    final void calculateIllegalBonus() {
        Map<Goods, Integer> illegalBonus;
        for (Trader iter : traders) {
            if (iter.bag.isEmpty()) {
                continue;
            }
            for (Integer good : iter.getIllegalStuff()) {
                if (getData.getGoodsById(good).getType() == GoodsType.Illegal) {
                    illegalBonus = getData.getGoodsById(good).getIllegalBonus();
                    for (int i = 0; i < Constants.LEGAL_GOODS; i++) {
                        if (illegalBonus.containsKey(getData.getGoodsById(i))) {
                            var result = new ArrayList<Integer>(Arrays.asList(
                                    new Integer[illegalBonus.get(getData.getGoodsById(i))]));
                            Collections.fill(result, i);
                            iter.bag.addAll(result);
                        }
                    }
                }
            }
        }
    }
    public
    final void turnCardsToMoney() {
        calculateIllegalBonus();
        for (Trader iter : traders) {
            if (iter.bag.size() != 0) {
                for (Integer good : iter.bag) {
                    iter.setProfit(iter.getProfit() + getData.getGoodsById(good).getProfit());
                    if (iter.getTradedGoods().containsKey(good)) {
                        HashMap<Integer, Integer> traded = new HashMap<>();
                        traded = iter.getTradedGoods();
                        traded.replace(good, iter.getTradedGoods().get(good) + 1);
                        iter.setTradedGoods(traded);
                    } else {
                        HashMap<Integer, Integer> traded = new HashMap<>();
                        traded = iter.getTradedGoods();
                        traded.put(good, 1);
                        iter.setTradedGoods(traded);
                    }
                }
            }
            iter.bag.clear();
            iter.getDeclaredBag().clear();
            iter.getIllegalStuff().clear();
            iter.setBribe(0);
        }
    }
    public
    final List<Integer> giveCards(final List<Integer> pack) {
        if (pack == null || pack.size() < (traders.size() - 1) * Constants.LEGAL_GOODS) {
            return pack;
        }
        if (pack.isEmpty()) {
            pack.addAll(confiscated); return pack;
        }
        ArrayList<Integer> sublist = new ArrayList<>();
        for (Player iter : traders) {
            if (iter.cards != null) {
                iter.cards.clear();
            }
            if (iter.getIndex() != getIndex()) {
                sublist = new ArrayList<>();
                sublist.addAll(pack.subList(0, Constants.LEGAL_GOODS));
                iter.cards.addAll(sublist);
                pack.subList(0, Constants.LEGAL_GOODS).clear();
                sublist.clear();
            }
        }
        if (cards != null) {
            sublist.addAll(pack.subList(0, 0));
            cards.addAll(sublist);
        } else {
            sublist.addAll(pack.subList(0, 0));
            cards = sublist;
        }
        pack.subList(0, 0).clear();
        pack.addAll(confiscated);
        return pack;
    }
    public
    final void baseStrategyS() {
        int penalty = 0;
        int bagSize;
        for (Player iter : traders) {
            bagSize = iter.getDeclaredBag().size();
            if (iter.getDeclaredBag().size() == 0) {
                continue;
            }
            if (iter.getIllegalStuff().size() != 0) {
                ArrayList<Integer> toRemove = new ArrayList<>();
                for (Integer stuff : iter.getIllegalStuff()) {
                    coins += getData.getGoodsById(stuff).getPenalty();
                    iter.coins -= getData.getGoodsById(stuff).getPenalty();
                    confiscated.add(stuff);
                    iter.bag.remove(stuff);
                    toRemove.add(stuff);
                }
                iter.getIllegalStuff().removeAll(toRemove);
            } else {  // honest trader
                penalty = getData.getGoodsById(iter.getDeclaredBag().get(0)).getPenalty()
                        * bagSize;
                coins -= penalty;
                iter.coins += penalty;
            }
            if (coins < Constants.MINIMUM_COINS) {
                return;
            }
        }
    }
    public
    final void greedyStrategyS() {
        int bagSize;
        int penalty = 0;
        boolean hasMoney = true;
        for (Player iter
                : traders) {  // if he has no money he accepts the bribe
            if (iter.getDeclaredBag().size() == 0) {
                continue;
            }
            if (iter.getBribe() > 0) {
                coins += iter.getBribe();
                iter.coins -= iter.getBribe();
                iter.setBribe(0);
                if (coins >= Constants.MINIMUM_COINS) {
                    hasMoney = true;
                }
            } else if (hasMoney) {
                bagSize = iter.getDeclaredBag().size();
                if (iter.getIllegalStuff().size() != 0) {
                    ArrayList<Integer> toRemove = new ArrayList<>();
                    for (Integer stuff : iter.getIllegalStuff()) {
                        coins += getData.getGoodsById(stuff).getPenalty();
                        iter.coins -= getData.getGoodsById(stuff).getPenalty();
                        confiscated.add(stuff);
                        iter.bag.remove(stuff);
                        toRemove.add(stuff);
                    }
                    iter.getIllegalStuff().removeAll(toRemove);
                    toRemove.clear();
                } else {
                    penalty = getData.getGoodsById(iter.getDeclaredBag().get(0)).getPenalty()
                            * bagSize;
                    coins -= penalty;
                    iter.coins += penalty;
                }
                if (coins < Constants.MINIMUM_COINS) {
                    hasMoney = false;
                }
            }
        }
    }
    public
    final void bribeStrategyS() {
        int left;
        int right;
        if (getIndex() == 0) {
            left = traders.size() - 1;
            right = 1;
        } else if (getIndex() == traders.size() - 1) {
            left = getIndex() - 1;
            right = 0;
        } else {
            left = getIndex() - 1;
            right = getIndex() + 1;
        }
        int bagSize;
        int penalty = 0;
        boolean hasMoney = false;
        if (coins >= Constants.MINIMUM_COINS) {
            hasMoney = true;
        }
        int aux = left;
        while (hasMoney) { // checking left and right
            bagSize = traders.get(aux).getDeclaredBag().size();
            if (traders.get(aux).getIllegalStuff().size() != 0) {
                ArrayList<Integer> toRemove = new ArrayList<>();
                for (Integer stuff : traders.get(aux).getIllegalStuff()) {
                    coins += getData.getGoodsById(stuff).getPenalty();
                    traders.get(aux).coins -= getData.getGoodsById(stuff).getPenalty();
                    confiscated.add(stuff);
                    traders.get(aux).bag.remove(stuff);
                    toRemove.add(stuff);
                }
                traders.get(aux).getIllegalStuff().removeAll(toRemove);
                toRemove.clear();
            } else {
                penalty = getData.getGoodsById(traders.
                        get(aux).getDeclaredBag().get(0)).getPenalty()
                        * bagSize;
                if (coins - penalty > 0) {
                    coins -= penalty;
                    traders.get(aux).coins += penalty;
                }
            }
            if (coins < Constants.MINIMUM_COINS) {
                hasMoney = false;
            }
            if (aux != right) {
                aux = right;
            } else {
                break;
            }
        }
        for (Player iter
                : traders) {  // getting bribe
            if ((iter.getDeclaredBag().size() == 0)
                    || iter.getIndex() == left || iter.getIndex() == right) {
                continue;
            }
            if (iter.getBribe() != 0) {
                coins += iter.getBribe();
                iter.coins -= iter.getBribe();
                iter.setBribe(0);
                if (coins > 0) {
                    hasMoney = true;
                }
            }
        }
    }
}
