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
        if (procentDeflect < Constants.DEFLECT_LIMIT) {
            procentDeflect = Constants.DEFLECT_BASE + Constants.DEFLECT_LEVEL * getLevel();
        }
        lifeDecreasing += 0.05;
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

    @Override
    public void newState() {
        if (getHp() <= 0) {
            setChanged();
            notifyObservers(getOpponent());
        }
    }

    @Override public int getTotalDamage(final Heroes enemy) {
        enemy.setOpponent(this);
        setDamage(drain(enemy) + deflect(enemy));
        enemy.setHp(enemy.getHp() - getDamage());
        return getDamage();
    }
    public
    int drain(final Heroes enemy) {
        float procent = lifeDecreasing;
        procent *= getLandModifiers().get(0);
        Math.round(procent);
        procent *= (modifiers.getDrain(enemy.getType())
                + getExternModifiers());

        float damage = Math.min(0.3f * enemy.getPossibleLifeThisLevel(), enemy.getHp());
        Math.round(damage);
        damage = damage * procent;
        return Math.round(damage);
    }
    public
    int deflect(final Heroes enemy) {
        float damage = enemy.givenDamage();
        damage *= getLandModifiers().get(1);
        damage = Math.round(damage);
        damage *= procentDeflect;
        System.out.println(procentDeflect);
        damage  *= (modifiers.getDeflect(enemy.getType())
                + getExternModifiers());

        if (enemy.getType() == HeroesType.Wizard) {
            return 0;
        }
        return Math.round(damage);
    }
}
