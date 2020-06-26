package angel;

import heroes.Constants;
import heroes.Heroes;
import land.Map;

import java.util.ArrayList;

public final
class LevelUpAngel extends Angel {
    public
    LevelUpAngel(final AngelType type) {
        super(type); }

    @Override public void action(final Heroes hero) {
        if (hero.getHp() <= 0) {
            return;
        }
        int xp = Constants.LEVEL
                + hero.getLevel()
                * Constants.LEVEL_COMPUTE;
        hero.setXp(xp);
        hero.setExternModifiers(hero.getExternModifiers()
                + bonus.getLevelUpAngel(hero.getType()));
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
