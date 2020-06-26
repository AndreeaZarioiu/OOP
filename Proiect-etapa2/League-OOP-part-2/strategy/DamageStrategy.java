package strategy;

import heroes.Heroes;

public final class DamageStrategy implements GameStrategy {
    @Override
    public void attack(final Heroes hero) {

        hero.setHp((hero.getHp() - hero.getHp() / (int) VALUES.getDecHp(hero.getType())));
        hero.setExternModifiers(hero.getExternModifiers() + VALUES.getIncCoef(hero.getType()));
    }
}
