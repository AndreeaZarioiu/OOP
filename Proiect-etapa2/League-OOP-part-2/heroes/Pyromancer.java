package heroes;

import land.Visitor;


public
final class Pyromancer extends Heroes {
    private
    float damageWithNoModifiers;
    private
    HeroesFactory modifiers = HeroesFactory.getInstance();
    private
    int initialHp;
    private
    final int hpeachLevel = 50;
    public
    Pyromancer(final String typeOfLand, final HeroesType type, final int x, final int y) {
        super(typeOfLand, type, x, y);
        initialHp = Constants.PYROMANCER_HP;
        setHp(initialHp);
        setPossibleLifeThisLevel(initialHp);
    }

    @Override public void conflictResult(final Heroes enemy) {
        if (enemy.getHp() <= 0 && getHp() > 0) {
            xpAfterWinning(enemy.getLevel());
            newLevel();
        }
    }

    @Override public void newLevel() {
        int nextLevel;
        nextLevel = (getXp() - Constants.LEVEL) / Constants.LEVEL_COMPUTE;
        if (getLevel() == 0 && getXp() >= Constants.XP_UP) {
            nextLevel++;
        } else if (nextLevel > 0) {
            nextLevel++;
        } else {
            return;
        }

        if (nextLevel == getLevel()) {
            return;
        }
        for (int i = getLevel() + 1; i <= nextLevel; i++) {
            setLevel(i);
            String update = "level";
            setChanged();
            notifyObservers(update);
        }
        setPossibleLifeThisLevel(initialHp + getLevel() * hpeachLevel);
        setHp(initialHp + getLevel() * hpeachLevel);

    }

    @Override public int givenDamage() {
        damageWithNoModifiers = Constants.FIREBLAST + getLevel() * Constants.FIREBLAST_LEVEL
                + Constants.IGNITE + getLevel() * Constants.IGNITE_LEVEL;
        if (getLandModifiers() != null) {
            damageWithNoModifiers *= getLandModifiers().get(0);
        }
        return Math.round(damageWithNoModifiers);
    }

    @Override public int getTotalDamage(final Heroes enemy) {
        enemy.setDamageThisRound(igniteEachRound(enemy));
        setDamage(fireblast(enemy) + ignite(enemy));
        enemy.setRoundOfIgnite(2);
        enemy.setDot(true);
        enemy.setOpponent(this);
        enemy.setHp(enemy.getHp() - getDamage());
        return getDamage();
    }

    @Override public void accept(final Visitor v) {
        v.visit(this);
    }

    @Override
    public void newState() {
        if (getHp() <= 0) {
            setChanged();
            notifyObservers(getOpponent());
        }
    }

    public
    int fireblast(final Heroes enemy) {
        float damage = Constants.FIREBLAST + getLevel() * Constants.FIREBLAST_LEVEL;
        damage *= getLandModifiers().get(0);
        damage = Math.round(damage);
        damage = Math.round(damage * (modifiers.getFireblast(enemy.getType())
                + getExternModifiers()));

        return Math.round(damage);
    }
    public
    int ignite(final Heroes enemy) {
        float damage = Constants.IGNITE + getLevel() * Constants.IGNITE_LEVEL;
        damage *= getLandModifiers().get(1);
        damage = Math.round(damage);
        damage = Math.round(damage * (modifiers.getIgnite(enemy.getType())
                + getExternModifiers()));

        return Math.round(damage);
    }
    public
    int igniteEachRound(final Heroes enemy) {
        float damage = Constants.SMALL_IGNITE + getLevel() * Constants.SMALL_IGNITE_LEVEL;
        damage *= getLandModifiers().get(1);
        damage = Math.round(damage);
        damage = Math.round(damage * modifiers.getIgnite(enemy.getType()));

        return Math.round(damage);
    }
}
