package heroes;

import land.Visitor;

public
final class Rogue extends Heroes {
    private
    HeroesFactory modifiers = HeroesFactory.getInstance();
    private boolean hit;
    private
    boolean canHit;
    private
    int initialHp;
    private
    final int hpeachLevel = 40;
    private
    int backstabHit;
    private
    float damageWithNoModifiers;
    public
    Rogue(final String land, final HeroesType type, final int positionX, final int positionY) {
        super(land, type, positionX, positionY);
        initialHp = Constants.ROGUE_HP;
        backstabHit = 0;
        canHit = true;
        hit = false;
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
        damageWithNoModifiers = Constants.BACKSTAB + Constants.BACKSTAB_LEVEL * getLevel();
        damageWithNoModifiers *= getLandModifiers().get(0);

        if (getTypeOfLand().equals("W")
                && (canHit || hit)) {
            damageWithNoModifiers = Math.round(damageWithNoModifiers * Constants.BACKSTAB_HIT);
        }
        damageWithNoModifiers = Math.round(damageWithNoModifiers);
        damageWithNoModifiers +=
                Math.round((Constants.PARALYSIS
                        + Constants.PARALYSIS_LEVEL * getLevel()) * getLandModifiers().get(0));
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
        float dot = Constants.PARALYSIS + Constants.PARALYSIS_LEVEL * getLevel();
        dot *= getLandModifiers().get(1);
        dot = Math.round(dot);
        dot = Math.round(dot * (modifiers.getParalysis(enemy.getType())
                + getExternModifiers()));
        enemy.setDamageThisRound(Math.round(dot));
        enemy.setDot(true);
        enemy.setOpponent(this);
        setDamage(backstab(enemy) + paralysis(enemy));
        enemy.setHp(enemy.getHp() - getDamage());
        return getDamage();
    }

    public
    int backstab(final Heroes enemy) {
        backstabHit++;
        if (hit) {
            hit = false;
        }
        float damage = Constants.BACKSTAB + Constants.BACKSTAB_LEVEL * getLevel();

        damage *= getLandModifiers().get(0);
        damage = Math.round(damage);
        damage = Math.round(damage * (modifiers.getBackstab(enemy.getType())
                + getExternModifiers()));
        if (getTypeOfLand().equals("W")
                && canHit) {
            damage = Math.round(damage * Constants.BACKSTAB_HIT);
            canHit = false;
            hit = true;
        }
        if (backstabHit == Constants.BACKSTAB_ROUNDS) {
            canHit = true;
            backstabHit = 0;
        }
        return Math.round(damage);
    }
    public
    int paralysis(final Heroes enemy) {
        float damage = Constants.PARALYSIS + Constants.PARALYSIS_LEVEL * getLevel();
        damage *= getLandModifiers().get(1);
        damage = Math.round(damage);
        damage = Math.round(damage * (modifiers.getParalysis(enemy.getType())
        - Constants.EXTERN + getExternModifiers()));

        if (getTypeOfLand().equals("W")) {
            enemy.setRoundOfParalysys(2 * Constants.BACKSTAB_ROUNDS);
        } else {
            enemy.setRoundOfParalysys(Constants.BACKSTAB_ROUNDS);
        }
        return Math.round(damage);
    }
}
