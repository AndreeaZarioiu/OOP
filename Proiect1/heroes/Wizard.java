package heroes;

import land.Visitor;

public
final class Wizard extends Heroes {
    private float damageWithNoModifiers;
    private
    int initialHp;
    private
    float lifeDecreasing;
    private
    float procentDeflect;
    private
    final int hpeachLevel = 30;
    private
    HeroesFactory modifiers = HeroesFactory.getInstance();
    public
    Wizard(final String land, final HeroesType type, final int positionX, final int positionY) {
        super(land, type, positionX, positionY);
        initialHp = Constants.WIZARD_HP;
        setHp(initialHp);
        lifeDecreasing = 0.2f;
        procentDeflect = 0.35f;
        setPossibleLifeThisLevel(initialHp);
    }

    @Override
    public void conflictResult(final Heroes enemy) {
        if (enemy.getHp() <= 0 && getHp() > 0) {
            xpAfterWinning(enemy.getLevel());
            newLevel();
        }
    }

    @Override public void newLevel() {
        int nextLevel;
        nextLevel = (getXp() - Constants.LEVEL) / Constants.LEVEL_COMPUTE;
        if (nextLevel < 0) {
            return;
        }
        nextLevel++;
        setLevel(nextLevel);
        if (procentDeflect < Constants.DEFLECT_LIMIT) {
            procentDeflect = Constants.DEFLECT_BASE + Constants.DEFLECT_LEVEL * getLevel();
        }
        lifeDecreasing += 0.05 * getLevel();
        setPossibleLifeThisLevel(initialHp + getLevel() * hpeachLevel);
        setHp(initialHp + getLevel() * hpeachLevel);
    }

    @Override public int givenDamage() {
        if (getLandModifiers() != null)  {
            damageWithNoModifiers *= getLandModifiers().get(0);
        }
        return Math.round(damageWithNoModifiers);
    }
    @Override public void accept(final Visitor v) {
        v.visit(this);
    }

    @Override public int getTotalDamage(final Heroes enemy) {
        enemy.setOpponent(this);
        setDamage(drain(enemy) + deflect(enemy));
        enemy.setHp(enemy.getHp() - getDamage());
        return getDamage();
    }
    public
    int drain(final Heroes enemy) {
        float procent = lifeDecreasing * modifiers.getDrain(enemy.getType());
        float damage = Math.min(0.3f * enemy.getPossibleLifeThisLevel(), enemy.getHp());
        damage = Math.round(damage * getLandModifiers().get(0));
        damage = damage * procent;
        return Math.round(damage);
    }
    public
    int deflect(final Heroes enemy) {
        float damage = Math.round(enemy.givenDamage() * procentDeflect);
        if (enemy.getType() == HeroesType.Wizard) {
            return 0;
        }
        if (enemy.getType() == HeroesType.Knight) {
            damage *= getLandModifiers().get(1);
            damage = Math.round(damage);
            damage = Math.round(damage * modifiers.getDeflect(enemy.getType()));
        } else {
            damage = Math.round(damage * modifiers.getDeflect(enemy.getType()));
            damage *= getLandModifiers().get(1);
        }
        return Math.round(damage);
    }
}
