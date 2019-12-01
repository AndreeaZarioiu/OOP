package game;

import fileio.FileSystem;
import heroes.Heroes;
import heroes.Knight;
import heroes.Wizard;
import heroes.Pyromancer;
import heroes.Rogue;
import heroes.HeroesType;
import land.ApplyLandModifiers;
import land.Map;

import java.util.ArrayList;
import java.util.HashMap;

public
final class GameCourse {
    private
    FileSystem fs;
    private
    ArrayList<Heroes> heroes;
    private
    HashMap<ArrayList<Integer>, String> map = new HashMap<>();
    private
    Map gameMap;

    public
    GameCourse(final FileSystem input, final HashMap<ArrayList<Integer>, String> map,
               final Map gameMap) {
        fs = input;
        this.map = map;
        this.gameMap = gameMap;
    }

    private
    void getHeroes() {  // reading src.heroes
        try {
            heroes = new ArrayList<>();
            int numberOfPlayers = fs.nextInt();
            gameMap.setMap(map);
            gameMap.newLocations();
            for (int i = 0; i < numberOfPlayers; i++) {
                Heroes hero;
                String heroType = fs.nextWord();
                int x = fs.nextInt();
                int y = fs.nextInt();
                ArrayList<Integer> position = new ArrayList<>();
                position.add(x);
                position.add(y);
                if (heroType.equals("W")) {
                    hero = new Wizard(map.get(position), HeroesType.Wizard, x, y);
                } else if (heroType.equals("R")) {
                    hero = new Rogue(map.get(position), HeroesType.Rogue, x, y);
                } else if (heroType.equals("P")) {
                    hero = new Pyromancer(map.get(position), HeroesType.Pyromancer, x, y);
                } else {
                    hero = new Knight(map.get(position), HeroesType.Knight, x, y);
                }
                heroes.add(hero);
                gameMap.addHero(position, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public
    ArrayList<Heroes> startGame() {
        getHeroes();
        ArrayList<ArrayList<Integer>> conflicts;
        try {
            int rounds = fs.nextInt();
            ApplyLandModifiers landModifiers = new ApplyLandModifiers(map);
            while (rounds > 0) {
                for (Heroes iter : heroes) { // apply dot
                    if (!iter.isDot()) {
                        continue;
                    }
                    if (iter.getRoundOfIgnite() == 0
                            && iter.getRoundOfParalysys() == 0) {
                        iter.setDot(false);
                        iter.setDamageThisRound(0);
                    }
                   iter.setHp(iter.getHp() - iter.getDamageThisRound());


                }
                gameMap.newLocations();
                String move = fs.nextWord();
                for (Heroes iter : heroes) {  // next moves
                    if (iter.getHp() <= 0) {  // if is dead
                        continue;
                    }
                    if (!iter.getCanMove()) {
                        iter.setCanMove(true);
                        gameMap.addHero(iter.getCoordonates(), heroes.indexOf(iter));
                        continue;
                    }
                    if (iter.getRoundOfParalysys() > 0) {
                        iter.setRoundOfParalysys(iter.getRoundOfParalysys() - 1);
                        gameMap.addHero(iter.getCoordonates(), heroes.indexOf(iter));
                        continue;
                    }
                    if (iter.getRoundOfIgnite() > 0) {
                        iter.setRoundOfIgnite(iter.getRoundOfIgnite() - 1);
                    }
                    String nextMove =
                            move.substring(heroes.indexOf(iter), heroes.indexOf(iter) + 1);
                    if (nextMove.equals("U")) {
                        iter.setCoordonates(iter.getCoordonates().get(0) - 1,
                                iter.getCoordonates().get(1));
                    } else if (nextMove.equals("D")) {
                        iter.setCoordonates(iter.getCoordonates().get(0) + 1,
                                iter.getCoordonates().get(1));
                    } else if (nextMove.equals("L")) {
                        iter.setCoordonates(iter.getCoordonates().get(0),
                                iter.getCoordonates().get(1) - 1);
                    } else if (nextMove.equals("R")) {
                        iter.setCoordonates(iter.getCoordonates().get(0),
                                iter.getCoordonates().get(1) + 1);
                    }
                    iter.setTypeOfLand(map.get(iter.getCoordonates()));
                    gameMap.addHero(iter.getCoordonates(), heroes.indexOf(iter));
                }
                conflicts = gameMap.getConflicts();

                if (gameMap.getNumberOfConflicts() != 0) {
                    for (int i = 0; i < gameMap.getNumberOfConflicts(); i++) {
                        heroes.get(conflicts.get(i).get(0)).accept(landModifiers);
                        heroes.get(conflicts.get(i).get(1)).accept(landModifiers);
                        if (heroes.get(conflicts.get(i).get(0)).getHp() <= 0) {
                            continue;
                        }
                        if (heroes.get(conflicts.get(i).get(1)).getHp() <= 0) {
                            continue;
                        }
                        heroes.get(conflicts.get(i).get(0))
                                .getTotalDamage(heroes.get(conflicts.get(i).get(1)));
                        heroes.get(conflicts.get(i).get(1))
                                .getTotalDamage(heroes.get(conflicts.get(i).get(0)));

                        heroes.get(conflicts.get(i).get(0))
                                .conflictResult(heroes.get(conflicts.get(i).get(1)));
                        heroes.get(conflicts.get(i).get(1))
                                .conflictResult(heroes.get(conflicts.get(i).get(0)));
                    }
                }
                rounds--;
            }

            return heroes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
