package com.tema1.main;  // import com.tema1.helpers.GameEngine;
import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.player.Player;
import com.tema1.player.PlayerComparator;
import com.tema1.player.Trader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class Main {
    @SuppressWarnings("checkstyle:OperatorWrap")
    private Main() { };
    public
    static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
        GameInput gameInput = gameInputLoader.load();
        int turns = gameInput.getRounds();
        List<String> strategy = gameInput.getPlayerNames();
        List<Integer> cards = gameInput.getAssetIds();
        ArrayList<Trader> players = new ArrayList<>(strategy.size());
        GoodsFactory getData = GoodsFactory.getInstance();
        Map<Integer, Goods> allGoods = getData.getAllGoods();
        int nrPlayers = strategy.size();
        for (int i = 0; i < nrPlayers; i++) {  // formez lista de jucatori
            if (i == 0) { //seriful nu primeste carti
                Trader t = new Trader(null, strategy.get(i), i);
                players.add(t);
            } else { //impart carti
              ArrayList<Integer> sublist = new ArrayList<>();
              sublist.addAll(cards.subList(0, Constants.LEGAL_GOODS));
              Trader t = new Trader(sublist, strategy.get(i), i);
              players.add(t);
              cards.subList(0, Constants.LEGAL_GOODS).clear();
            }
        }
        ArrayList<Integer> sublist = new ArrayList<>();
        sublist.addAll(cards.subList(0, 0));
        players.get(0).cards = sublist;
        players.get(0).setTraders(players);
        //incep jocul
        ApplyStrategies game = new ApplyStrategies(players, turns, strategy);
        players = game.startGame(cards);
        for (Player iterator:players) {
            iterator.coins += iterator.getProfit();
        }
        PlayerComparator playerComparator = new PlayerComparator();
        Collections.sort(players, playerComparator);

        //afisez topul
        for (Player player : players) {
            System.out.println(player.getIndex()
                    + " "
                    + player.getStrategy().toUpperCase()
                    + " "
                    + player.coins);
        }
    }
}
