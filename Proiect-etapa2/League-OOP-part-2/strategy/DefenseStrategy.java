package strategy;

import heroes.Heroes;

public final class DefenseStrategy implements GameStrategy {
    @Override
    public void attack(final Heroes hero) {
        hero.setHp((hero.getHp() + hero.getHp() / (int) VALUES.getIncHp(hero.getType())));
        hero.setExternModifiers(hero.getExternModifiers() + VALUES.getDecCoef(hero.getType()));
    }
}
