package heroes;

import land.Visitor;

public
final class Knight extends Heroes {
    private
    int initialHp;
    private
    float executeLimitHp;
    private
    float damageWithNoModifiers;
    private
    HeroesFactory modifiers = HeroesFactory.getInstance();
    private final int hpEachLevel = 80;
    public
    Knight(final String land, final HeroesType type, final int x, final int y) {
        super(land, type, x, y);
        initialHp = Constants.KNIGHT_HP;
        executeLimitHp = Constants.KNIGHT_EXECUTE;
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
        if (nextLevel < 0) {
            return;
        }
        nextLevel++;
        setLevel(nextLevel);
        setPossibleLifeThisLevel(initialHp + getLevel() * hpEachLevel);
        if (executeLimitHp < Constants.KNIGHT_LIMIT) {
            executeLimitHp = executeLimitHp + getLevel() * Constants.KNIGHT_LEVEL_EXECUTE;
        }
        setHp(initialHp + getLevel() * hpEachLevel);
    }

    @Override public int givenDamage() {
        damageWithNoModifiers = Constants.KNIGHT_SLAM + Constants.KNIGHT_SLAM_LEVEL * getLevel();
        if (getOpponent().getHp()
                <= executeLimitHp * getOpponent().getPossibleLifeThisLevel()
                && getOpponent().getHp() > 0) {
            damageWithNoModifiers += getOpponent().getHp();
        } else {
            damageWithNoModifiers += Constants.KNIGHT_EXECUTE_DAMAGE
                    + Constants.KNIGHT_EXECUTE_LEVEL * getLevel();
        }
        if (getLandModifiers() != null) {
            damageWithNoModifiers *= getLandModifiers().get(0);
        }
        return Math.round(damageWithNoModifiers);
    }

    @Override public int getTotalDamage(final Heroes enemy) {
        setDamage(slam(enemy) + execute(enemy));
        enemy.setOpponent(this);
        enemy.setHp(enemy.getHp() - getDamage());
        return getDamage();
    }

    @Override public void accept(final Visitor v) {
        v.visit(this);
    }

    public
    int execute(final Heroes enemy) {
        float damage = Constants.KNIGHT_EXECUTE_DAMAGE
                + Constants.KNIGHT_EXECUTE_LEVEL * getLevel();
        damage *= getLandModifiers().get(0);
        damage = Math.round(damage);
        damage = Math.round(damage * modifiers.getExecute(enemy.getType()));
        if (enemy.getHp() <= executeLimitHp * enemy.getPossibleLifeThisLevel()) {
            damage = enemy.getHp();
        }

        return Math.round(damage);
    }
    public
    int slam(final Heroes enemy) {
        enemy.setCanMove(false);
        float damage = Constants.KNIGHT_SLAM + Constants.KNIGHT_SLAM_LEVEL * getLevel();
        damage *= getLandModifiers().get(1);
        damage = Math.round(damage);
        damage = Math.round(damage * modifiers.getSlam(enemy.getType()));
        return Math.round(damage);
    }
}
