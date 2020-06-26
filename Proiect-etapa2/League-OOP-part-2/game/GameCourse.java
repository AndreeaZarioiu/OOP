package game;

import angel.Angel;
import angel.AngelType;
import angel.DamageAngel;
import angel.DarkAngel;
import angel.Dracula;
import angel.GoodBoy;
import angel.LevelUpAngel;
import angel.LifeGiver;
import angel.SmallAngel;
import angel.TheDoomer;
import angel.Spawner;
import angel.XpAngel;
import fileio.FileSystem;
import heroes.Heroes;
import heroes.HeroesFactory;
import heroes.HeroesType;
import heroes.Knight;
import heroes.Wizard;
import heroes.Pyromancer;
import heroes.Rogue;
import land.ApplyLandModifiers;
import land.Map;
import land.PositionMap;
import strategy.DamageStrategy;
import strategy.DefenseStrategy;
import witcher.GreatWizard;

import java.util.ArrayList;
import java.util.HashMap;

public
final class GameCourse {
    private
    HeroesFactory modifiers = HeroesFactory.getInstance();
    private
    GreatWizard greatWizard;
    private
    FileSystem fs;
    private
    ArrayList<Heroes> heroes;
    private
    HashMap<ArrayList<Integer>, String> map = new HashMap<>();
    private
    Map gameMap;
    private
    PositionMap positionMap = PositionMap.getInstance(null);
    private
    ArrayList<String> moves;
    private
    int numberOfPlayers;
    public
    GameCourse(final FileSystem input, final Map gameMap) {
        fs = input;
        this.map = positionMap.getMap();
        this.gameMap = gameMap;
    }

    private
    void getHeroes() {  // reading heroes
        try {
            heroes = new ArrayList<>();
            numberOfPlayers = fs.nextInt();
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
    private
    void getMoves(final int r) { // reading moves
        try {
            moves = new ArrayList<>();
            for (int i = 0; i < r; i++) {
                String move = fs.nextWord();
                moves.add(move);
            }
            for (Heroes iter : heroes) {
                iter.addObserver(greatWizard);
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
            greatWizard = new GreatWizard(fs, heroes);
            getMoves(rounds);
            int nrRounds = rounds + 1;
            while (rounds > 0) {
                fs.writeWord("~~ Round " + (nrRounds - rounds) + " ~~\n");

                for (Heroes iter : heroes) {  // apply dot
                    if (!iter.isDot()) {
                        continue;
                    }
                    if (iter.getRoundOfIgnite() == 0 && iter.getRoundOfParalysys() == 0) {
                        iter.setDot(false);
                        iter.setDamageThisRound(0);
                    }
                    iter.setHp(iter.getHp() - iter.getDamageThisRound());
                }
                for (Heroes iter : heroes) { // apply strategy
                    if (iter.getRoundOfParalysys() > 0) {
                        continue;
                    }
                    if (!iter.getCanMove()) {
                        continue;
                    }
                    if (iter.getHp() <= 0) {
                        continue;
                    }
                    if (iter.getPossibleLifeThisLevel()
                            / modifiers.getLow(iter.getType())
                            < iter.getHp()
                            && iter.getHp()
                            < iter.getPossibleLifeThisLevel()
                            / modifiers.getHigh(iter.getType())) {
                        DamageStrategy strategy1 = new DamageStrategy();
                        strategy1.attack(iter);
                    } else if (iter.getHp() < iter.getPossibleLifeThisLevel()
                            / modifiers.getLow(iter.getType())) {
                        DefenseStrategy strategy2 = new DefenseStrategy();
                        strategy2.attack(iter);
                    }
                }
                gameMap.newLocations();
                String move = moves.get(0);
                moves.remove(0);
                for (Heroes iter : heroes) {  // next moves
                    if (iter.getHp() <= 0) {    // if is dead
                        iter.setTypeOfLand(map.get(iter.getCoordonates()));
                        gameMap.addHero(iter.getCoordonates(), heroes.indexOf(iter));
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
                if (gameMap.getNumberOfConflicts() != 0) { // conflicts
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
                        heroes.get(conflicts.get(i).get(1)).newState();
                        heroes.get(conflicts.get(i).get(0)).newState();

                        heroes.get(conflicts.get(i).get(0))
                                .conflictResult(heroes.get(conflicts.get(i).get(1)));
                        heroes.get(conflicts.get(i).get(1))
                                .conflictResult(heroes.get(conflicts.get(i).get(0)));
                    }
                }
                // read angels
                greatWizard.setMap(gameMap);
                ArrayList<Angel> angels = new ArrayList<>();
                int numberOfAngels = fs.nextInt();
                for (int i = 0; i < numberOfAngels; i++) {
                    String theAngel = fs.nextWord();
                    String type = theAngel.substring(0, theAngel.indexOf(","));
                    Angel angel;
                    if (type.equals("DamageAngel")) {
                        angel = new DamageAngel(AngelType.DamageAngel);
                    } else if (type.equals("DarkAngel")) {
                        angel = new DarkAngel(AngelType.DarkAngel);
                    } else if (type.equals("Dracula")) {
                        angel = new Dracula(AngelType.Dracula);
                    } else if (type.equals("GoodBoy")) {
                        angel = new GoodBoy(AngelType.GoodBoy);
                    } else if (type.equals("LevelUpAngel")) {
                        angel = new LevelUpAngel(AngelType.LevelUpAngel);
                    } else if (type.equals("LifeGiver")) {
                        angel = new LifeGiver(AngelType.LifeGiver);
                    } else if (type.equals("SmallAngel")) {
                        angel = new SmallAngel(AngelType.SmallAngel);
                    } else if (type.equals("Spawner")) {
                        angel = new Spawner(AngelType.Spawner);
                    } else if (type.equals("TheDoomer")) {
                        angel = new TheDoomer(AngelType.TheDoomer);
                    } else {
                        angel = new XpAngel(AngelType.XPAngel);
                    }

                    ArrayList<Integer> positions = new ArrayList<>();
                    theAngel = theAngel.substring(theAngel.indexOf(",") + 1, theAngel.length());
                    int x =
                            Integer.parseInt(theAngel.substring(0, theAngel.indexOf(",")));
                    theAngel = theAngel.substring(theAngel.indexOf(",") + 1, theAngel.length());
                    int y = Integer.parseInt(theAngel);
                    greatWizard.setWhichHero(0);
                    angel.setHeroes(heroes);
                    angel.addObserver(greatWizard);
                    angel.setPosition(x, y);
                    angel.newState();
                    gameMap.accept(angel);
                    if (gameMap.getHeroesOnPosition(angel.getPosition()).size() > 0) {
                        heroes.get(gameMap.getHeroesOnPosition(angel.getPosition()).
                                get(0)).newLevel();
                        heroes.get(gameMap.getHeroesOnPosition(angel.getPosition()).
                                get(0)).setKilledByAngel(false);
                        if (gameMap.getHeroesOnPosition(angel.getPosition()).size() > 1) {
                            greatWizard.setWhichHero(1);
                            angel.newState();
                            heroes.get(gameMap.getHeroesOnPosition(angel.getPosition()).
                                    get(1)).newLevel();
                        }
                    }

                }

                rounds--;

                fs.writeWord("\n");
            }

            return heroes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
