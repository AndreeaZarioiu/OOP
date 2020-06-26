package com.tema1.player;

import com.tema1.common.Constants;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.main.CardsComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public
class Trader extends Sheriff {
    private GoodsFactory getData = GoodsFactory.getInstance();
    public
    Trader(final List<Integer> cards, final String strategy, final int index) {
        super(cards, strategy, index);
    }
    private int illegalCard;  // the most profitable illegal card
    private int secondIllegal;
    public
    final void getStrategy(final int turn) {
        if (getStrategy().equals("basic")) {
            baseStrategy();
        }
        if (getStrategy().equals("bribed")) {
            bribeStrategy();
        }
        if (getStrategy().equals("greedy")) {
            greedyStrategy(turn);
        }
    }
    public
    final int baseStrategy() {
        BasicComparator sortCards = new BasicComparator();
        Collections.sort(cards, sortCards);
        int frequency = 0;
        int max = 0;
        int idMax = -1, profitMax = 0, profitIllegal = 0, idIllegal = -1, second = -1;
        for (int i = 0; i < cards.size(); i++) {
            int card = cards.get(i);
            if (getData.getGoodsById(card).getType() == GoodsType.Illegal) {
                if (getData.getGoodsById(card).getProfit() >= profitIllegal) {
                    secondIllegal = idIllegal;
                    second = profitIllegal;
                    idIllegal = getData.getGoodsById(card).getId();
                    profitIllegal = getData.getGoodsById(card).getProfit();
                } else if (getData.getGoodsById(card).getProfit() > second) {
                    secondIllegal = getData.getGoodsById(card).getId();
                    second = getData.getGoodsById(card).getProfit();
                }
            } else {
                if (i != 0 && card != cards.get(i - 1)) {
                    if (frequency > max) {
                        idMax = cards.get(i - 1);
                        max = frequency;
                        profitMax = getData.getGoodsById(cards.get(i - 1)).getProfit();
                    } else if (frequency == max) {
                        if (profitMax < getData.getGoodsById(cards.get(i - 1)).getProfit()) {
                            idMax = cards.get(i - 1);
                            profitMax = getData.getGoodsById(cards.get(i - 1)).getProfit();
                        } else if (profitMax
                                == getData.getGoodsById(cards.get(i - 1)).getProfit()) {
                            if (getData.getGoodsById(cards.get(i - 1)).getId() > idMax) {
                                idMax = getData.getGoodsById(cards.get(i - 1)).getId();
                            }
                        }
                    }
                    frequency = 0;
                } else if (i == 0) {
                    idMax = card;
                }
                frequency++;
                if (i == cards.size() - 1) {
                    if (frequency > max) {
                        idMax = cards.get(i);
                        max = frequency;
                        profitMax = getData.getGoodsById(cards.get(i)).getProfit();
                    } else if (frequency == max) {
                        if (profitMax < getData.getGoodsById(cards.get(i)).getProfit()) {
                            idMax = cards.get(i);
                            profitMax = getData.getGoodsById(cards.get(i)).getProfit();
                        }
                    }
                }
            }
        }
        frequency = max;
        illegalCard = idIllegal;
        if (idMax == -1) {
            if (idIllegal == -1 || coins - getData.getGoodsById(idIllegal).getPenalty() < 0) {
                return 0;
            }
            addIllegalStuff(idIllegal);
            bag.add(idIllegal);
            addDeclaredBag(0);
            return 1;
        } else {
            if (frequency > Constants.BAG_CAPACITY) {
                frequency = Constants.BAG_CAPACITY;
            }
            for (int i = 0; i < frequency; i++) {
                bag.add(idMax);
            }
            getDeclaredBag().clear();
            addAllDeclaredBag(bag);
        }
        return 0;
    }
    public
    final void greedyStrategy(final int turn) {
        int ok = 0;
        ok = baseStrategy();
        if (ok == 1) {
            if (turn % 2 == 0 && secondIllegal != -1
                    && bag.size() < Constants.BAG_CAPACITY) {
                if (coins - getData.getGoodsById(secondIllegal).getPenalty() < 0) {
                    return;
                }
                addIllegalStuff(secondIllegal);
                bag.add(secondIllegal);
                return;
            }
        }
        if (turn % 2 == 0 && illegalCard != -1
                && bag.size() < Constants.BAG_CAPACITY) {
            if (coins - getData.getGoodsById(illegalCard).getPenalty() < 0) {
                return;
            }
            addIllegalStuff(illegalCard);
            bag.add(illegalCard);
        }
    }
    public
    final void bribeStrategy() {
        ArrayList<Integer> illegalCards = new ArrayList<>();
        ArrayList<Integer> legalCards = new ArrayList<>();
        for (Integer iter : cards) {
            if (getData.getGoodsById(iter).getType() == GoodsType.Illegal) {
                illegalCards.add(iter);
            } else {
                legalCards.add(iter);
            }
        }
        if (coins <= Constants.BRIBE || illegalCards.isEmpty()) {
            baseStrategy();
            return;
        }
        int penalty = 0;
        setBribe(0);
        CardsComparator cardsComparator = new CardsComparator();
        Collections.sort(illegalCards, cardsComparator);
        Collections.sort(legalCards, cardsComparator);
        int numberOfIllegal = 0;
        for (Integer card : illegalCards) {
            if (coins - getData.getGoodsById(card).getPenalty()  - penalty
                    < 1) {
                continue;
            }
            if (bag.size() == Constants.BAG_CAPACITY) {
                break;
            }
            numberOfIllegal++;
           if (numberOfIllegal == 1
                    || (numberOfIllegal == Constants.MORE_BRIBE)) {
                setBribe(getBribe() + Constants.BRIBE);
            }
            bag.add(card);
            addIllegalStuff(card);
            addDeclaredBag(0);
            penalty += getData.getGoodsById(card).getPenalty();
        }
        for (Integer card : legalCards) {
            if (bag.size() == Constants.BAG_CAPACITY) {
                break;
            }
            if (coins - getData.getGoodsById(card).getPenalty() - penalty
                    < 1) {
                continue;
            }
            if (card == 0) {
                bag.add(card);
                addDeclaredBag(0);
                continue;
            }
            bag.add(card);
            addIllegalStuff(card);
            addDeclaredBag(0);
            penalty += getData.getGoodsById(card).getPenalty();
        }
    }
}
