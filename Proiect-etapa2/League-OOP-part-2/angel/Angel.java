package angel;

import heroes.Heroes;

import java.util.ArrayList;
import java.util.Observable;

public
abstract class Angel extends Observable implements Visitor {
    public
    ArrayList<Heroes> heroes;
    private
    AngelType type;
    public
    AngelFactory bonus = AngelFactory.getInstance();
    public
    Angel(final AngelType type) {
        this.type = type;
    }
    public final
    void setHeroes(final ArrayList<Heroes> heroes) {
        this.heroes = heroes;
    }
    private
    ArrayList<Integer> coordinates;
    public
    abstract void action(Heroes hero);
    public final
    void setPosition(final int x, final int y) {
        coordinates = new ArrayList<>();
        coordinates.add(x);
        coordinates.add(y);
    }
    public final
    void newState() {
        setChanged();
        notifyObservers(coordinates);
    }
    public final
    ArrayList<Integer> getPosition() {
        return coordinates;
    }

    public final
    AngelType getType() {
        return type;
    }

    public final
    void setType(final AngelType type) {
        this.type = type;
    }
}
