package com.tema1.main;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.player.KingAndQueenBonus;
import com.tema1.player.Trader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApplyStrategies {
    private int turns;
    private ArrayList<Trader> players;
    private int nrPlayers;
    private List<String> strategy;
    public ApplyStrategies(final ArrayList<Trader> players,
                           final int turns, final List<String> strategy) {
        this.players = players;
        nrPlayers = this.players.size();
        this.turns = turns;
        this.strategy = strategy;
    }
    public final ArrayList<Trader> startGame(final List<Integer> cards) {
        int sheriff = 0;
        int round = 0;
        GoodsFactory getData = GoodsFactory.getInstance();
        Map<Integer, Goods> allGoods = getData.getAllGoods();
        for (int i = 1; i <= turns * nrPlayers; i++) {
            if (sheriff == nrPlayers) {
                sheriff = 0;
            }
            if (i != 1) {
                players.get(sheriff).setTraders(players);
                players.get(sheriff).giveCards(cards);
            }
            for (int j = 0; j < nrPlayers; j++) {
                if (j != sheriff) {
                    round = i / nrPlayers;
                    if (i % nrPlayers != 0) {
                        round++;
                    }
                    players.get(j).getStrategy(round);
                }
            }
            players.get(sheriff).setTraders(players);
            players.get(sheriff).getSheriff();  // inspectie, taraba, dau carti, actualizez pachetul
            if (i == turns * nrPlayers) {  // calculam queen/king bonus
                KingAndQueenBonus bonus = new KingAndQueenBonus(players);
                players = bonus.getBonus(allGoods);
            }
            sheriff++;

        }
        return players;
    }

}
