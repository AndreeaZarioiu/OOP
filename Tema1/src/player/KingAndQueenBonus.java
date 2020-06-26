package com.tema1.player;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;

import java.util.ArrayList;
import java.util.Map;

public class KingAndQueenBonus {
    private ArrayList<Trader> players;
    private int nrPlayers;
    public KingAndQueenBonus(final ArrayList<Trader> players) {
        this.players = players;
        nrPlayers = this.players.size();
    }
    public  final ArrayList<Trader> getBonus(final Map<Integer, Goods> allGoods) {
        int king = 0, idKing = -1, idQueen = -1, queen = 0;
        for (int j = 0; j < Constants.LEGAL_GOODS; j++) {
            king = 0;
            idKing = -1;
            idQueen = -1;
            queen = 0;
            for (int k = 0; k < nrPlayers; k++) {
                if (players.get(k).getTradedGoods().containsKey(j)) {
                    if (king < players.get(k).getTradedGoods().get(j)) {
                        idQueen = idKing;
                        queen = king;
                        idKing = k;
                        king = players.get(k).getTradedGoods().get(j);
                    } else if (queen < players.get(k).getTradedGoods().get(j)) {
                        idQueen = k;
                        queen = players.get(k).getTradedGoods().get(j);
                    }
                }
            }
            if (idKing != -1) {
                players.get(idKing).setCoins(players.get(idKing).getCoins()
                        + allGoods.get(j).getKingBonus());
            }
            if (idQueen != -1) {
                players.get(idQueen).setCoins(players.get(idQueen).getCoins()
                        + allGoods.get(j).getQueenBonus());
            }
        }
        return players;
    }

}
