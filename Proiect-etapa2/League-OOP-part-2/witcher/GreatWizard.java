package witcher;

import angel.Angel;
import angel.AngelType;
import fileio.FileSystem;
import heroes.Heroes;
import land.Map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public final
class GreatWizard implements Observer {
    private
    ArrayList<Heroes> heroes;
    private Integer whichHero = 0;
    private
    Map positions;
    private
    FileSystem fs;
    public void setWhichHero(final Integer x) {
        whichHero = x;
    }
    public
    GreatWizard(final FileSystem output, final ArrayList<Heroes> heroes) {
        fs = output;
        this.heroes = heroes;
    }
    private
    void forTheDoomer(final ArrayList updates, final Angel character) throws IOException {
        if ((heroes.get((Integer) (positions.getHeroesOnPosition(updates))
                .get(whichHero)))
                .getHp() <= 0 && !(heroes.get((Integer) (positions.getHeroesOnPosition(updates))
                .get(whichHero)))
                .isKilledByAngel()) {
            return;
        }
        fs.writeWord(
                character.getType().toString()
                        + " hit "
                        + (heroes.get((Integer) (positions.getHeroesOnPosition(updates))
                                .get(whichHero)))
                                .getType()
                                .toString() + " "
                        + (positions.getHeroesOnPosition(updates)).get(whichHero)
                        + "\n");
        fs.writeWord(
                "Player "
                        + heroes.get((Integer) (positions.getHeroesOnPosition(updates)
                        .get(whichHero)))
                                .getType()
                                .toString()
                        + " "
                        + positions.getHeroesOnPosition(updates).get(whichHero)
                        + " was killed by an angel\n");

        (heroes.get((Integer) (positions.getHeroesOnPosition(updates))
                .get(whichHero)))
                .setKilledByAngel(false);
        System.out.println((heroes.get((Integer) (positions.getHeroesOnPosition(updates))
                .get(whichHero))).isKilledByAngel());

    }
    private void forSpawner(final ArrayList updates, final Angel character) throws IOException {
        if (heroes.get((Integer) (positions.getHeroesOnPosition(updates).
                get(whichHero))).getHp() <= 0) {
            fs.writeWord(
                    character.getType().toString()
                            + " helped "
                            + (heroes.get((Integer) (positions.getHeroesOnPosition(updates))
                            .get(whichHero)))
                            .getType()
                            .toString() + " "
                            + (positions.getHeroesOnPosition(updates)).get(whichHero)
                            + "\n");
            fs.writeWord(
                    "Player "
                            + heroes.get((Integer) (positions.getHeroesOnPosition(updates).
                            get(whichHero)))
                            .getType()
                            .toString()
                            + " "
                            + positions.getHeroesOnPosition(updates).get(whichHero)
                            + " was brought to life by an angel\n");
        }
        if (positions.getHeroesOnPosition(updates).size() == 1) {
            return;
        }
        if (heroes.get((Integer) (positions.getHeroesOnPosition(updates).get(1))).getHp() > 0) {
            return;
        }
        fs.writeWord(
                character.getType().toString()
                        + " helped "
                        + (heroes.get((Integer) (positions.getHeroesOnPosition(updates))
                        .get(1)))
                        .getType()
                        .toString() + " "
                        + (positions.getHeroesOnPosition(updates)).get(1)
                        + "\n");
        fs.writeWord(
                "Player "
                        + heroes.get((Integer) (positions.getHeroesOnPosition(updates).get(1)))
                        .getType()
                        .toString()
                        + " "
                        + positions.getHeroesOnPosition(updates).get(1)
                        + " was brought to life by an angel\n");
    }
    public
    void setMap(final Map gameMap) {
        positions = gameMap;
    }
    @Override public void update(final Observable character, final Object updates) {
        if (updates instanceof ArrayList<?>) {
            try {
                if (whichHero == 0) {
                    fs.writeWord("Angel "
                            + ((Angel) character).getType().toString()
                            + " was spawned at "
                            + ((ArrayList) updates).get(0)
                            + " "
                            + ((ArrayList) updates).get(0)
                            + "\n");
                }

                if (positions.getHeroesOnPosition((ArrayList) updates).size() == 0) {
                    return;
                }

                if (((Angel) character).getType() == AngelType.TheDoomer) {
                    forTheDoomer((ArrayList) updates, (Angel) character);
                    return;
                }
                if (((Angel) character).getType() == AngelType.Spawner) {
                    forSpawner((ArrayList) updates, (Angel) character);
                    return;
                }
                String theAct = " helped ";
                if (((Angel) character).getType() == AngelType.DarkAngel
                        || ((Angel) character).getType() == AngelType.Dracula
                        || ((Angel) character).getType() == AngelType.TheDoomer) {
                    theAct = " hit ";
                }

                if (heroes.get((Integer) (positions.getHeroesOnPosition(
                        (ArrayList) updates))
                        .get(whichHero))
                        .getHp() > 0 || heroes.get((Integer) (positions.getHeroesOnPosition(
                        (ArrayList) updates))
                        .get(whichHero))
                        .isKilledByAngel()) {
                    fs.writeWord(
                            ((Angel) character).getType().toString()
                                    + theAct
                                    + (heroes.get((Integer) (positions.getHeroesOnPosition(
                                            (ArrayList) updates))
                                            .get(whichHero)))
                                            .getType()
                                            .toString()
                                    + " "
                                    + (positions.getHeroesOnPosition((ArrayList) updates)).
                                    get(whichHero)
                                    + "\n");

                    if (heroes.get((Integer) (positions.getHeroesOnPosition(
                            (ArrayList) updates))
                            .get(whichHero))
                            .isKilledByAngel()
                            && heroes.get((Integer) (positions.getHeroesOnPosition(
                            (ArrayList) updates))
                            .get(whichHero))
                            .getHp() <= 0 || heroes.get((Integer) (positions.getHeroesOnPosition(
                            (ArrayList) updates))
                            .get(whichHero))
                            .getHp() + ((Angel) character).bonus.
                            getDraculaHp(heroes.get((Integer) (positions.getHeroesOnPosition(
                            (ArrayList) updates))
                            .get(whichHero))
                            .getType())
                            <= 0 && ((Angel) character).getType() == AngelType.Dracula) {
                        fs.writeWord(
                                "Player "
                                        + heroes.get((Integer) (positions.
                                        getHeroesOnPosition((ArrayList) updates).
                                        get(whichHero)))
                                        .getType()
                                        .toString()
                                        + " "
                                        + positions.getHeroesOnPosition((ArrayList) updates).
                                        get(whichHero)
                                        + " was killed by an angel\n");

                    }

                    heroes.get((Integer) (positions.getHeroesOnPosition(
                            (ArrayList) updates))
                            .get(whichHero))
                            .setKilledByAngel(false);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (updates instanceof Heroes) {
            try {
                fs.writeWord("Player "
                        + ((Heroes) character).getType().toString()
                        + " "
                        + heroes.indexOf(character)
                        + " was killed by "
                        + ((Heroes) updates).getType().toString()
                        + " "
                        + heroes.indexOf(updates)
                        + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (updates instanceof String) {
            try {
                fs.writeWord(((Heroes) character).getType().toString()
                        + " "
                        + heroes.indexOf(character)
                        + " reached level "
                        + ((Heroes) character).getLevel()
                        + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
