package angel;

import heroes.Heroes;
import land.Map;

import java.util.ArrayList;

public final
class Spawner extends Angel {
    public
    Spawner(final AngelType type) {
        super(type);
    }

    @Override public void action(final Heroes hero) {
        if (hero.getHp() <= 0) {
            hero.setHp(bonus.getSpawner(hero.getType()));
        }
    }

    @Override public void visit(final Map positions) {
        ArrayList<Integer> characters =
                positions.getHeroesOnPosition(getPosition());
        if (characters.size() == 0) {
            return;
        }
        action(heroes.get(characters.get(0)));
        if (characters.size() == 1) {
            return;
        }
        action(heroes.get(characters.get(1)));
    }
}
