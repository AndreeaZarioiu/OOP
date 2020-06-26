package heroes;

import land.Visitable;
import land.Visitor;

import java.util.ArrayList;

public
abstract class Heroes implements Visitable {
    private int damageThisRound;
    private
    int hp;
    private
    int xp;
    private
    int damage;
    private
    int possibleLifeThisLevel;
    private
    boolean dot;
    private
    ArrayList<Integer> coordonates;
    private
    ArrayList<Float> landModifiers;
    private
    int roundOfIgnite;
    private
    HeroesType type;
    private
    int level;
    private
    boolean canMove;
    private
    Heroes opponent;
    private
    int roundOfParalysys;  // cat timp se aplica paralysys
    private
    String typeOfLand;
    public
    Heroes(final String typeOfLand, final HeroesType type,
           final int positionX, final int positionY) {
        this.typeOfLand = typeOfLand;
        this.type = type;
        damageThisRound = 0;
        xp = 0;
        level = 0;
        canMove = true;
        roundOfIgnite = 0;
        roundOfParalysys = 0;
        coordonates = new ArrayList<>();
        coordonates.add(positionX);
        coordonates.add(positionY);
        landModifiers = new ArrayList<>(2);
        dot = false;
        opponent = null;
    }

    public final
    void setCoordonates(final int x, final int y) {
        coordonates.clear();
        coordonates.add(x);
        coordonates.add(y);
    }
    public final
    ArrayList<Integer> getCoordonates() {
        return coordonates;
    }

    public final
    int getHp() {
        return hp;
    }
    public final
    void setHp(final int hp) {

        this.hp = hp;
    }
    public final
    HeroesType getType() {

        return type;
    }

    public final
    int getXp() {

        return xp;
    }
    public final
    void setXp(final int xp) {

        this.xp = xp;
    }
    public final
    int getLevel() {

        return level;
    }
    public final
    void setLevel(final int level) {

        this.level = level;
    }
    public final
    void xpAfterWinning(final int levelLoser) {
        int xpWinner;
        xpWinner = getXp() + Math.max(0, (Constants.XP - (getLevel()
                - levelLoser) * Constants.XP_COMPUTE));
        setXp(xpWinner);
    }
    public
    abstract void conflictResult(Heroes enemy);
    public
    abstract void newLevel();
    public
    abstract int givenDamage();
    public
    abstract int getTotalDamage(Heroes enemy);
    public abstract
    void accept(Visitor v);

    public final
    int getPossibleLifeThisLevel() {
        return possibleLifeThisLevel;
    }

    public final
    void setPossibleLifeThisLevel(final int possibleLifeThisLevel) {
        this.possibleLifeThisLevel = possibleLifeThisLevel;
    }

    public final
    boolean getCanMove() {
        return canMove;
    }

    public final
    void setCanMove(final boolean canMove) {
        this.canMove = canMove;
    }

    public final
    String getTypeOfLand() {
        return typeOfLand;
    }

    public final
    void setTypeOfLand(final String typeOfLand) {
        this.typeOfLand = typeOfLand;
    }

    public final
    int getRoundOfParalysys() {
        return roundOfParalysys;
    }

    public final
    void setRoundOfParalysys(final int roundOfParalysys) {

        this.roundOfParalysys = roundOfParalysys;
    }
    public final
    void setDamage(final int damage) {
        this.damage = damage;
    }
    public final
    int getDamage() {
        return damage;
    }

    public final
    ArrayList<Float> getLandModifiers() {
        return landModifiers;
    }
    public final
    void noLandModifiers() {
        landModifiers.clear();
        landModifiers.add(1f);
        landModifiers.add(1f);
    }
    public final
    void setLandModifiers(final ArrayList<Float> landModifiers) {
        this.landModifiers.clear();
        this.landModifiers = new ArrayList<>(landModifiers);
    }

    public final
    boolean isDot() {
        return dot;
    }

    public final
    void setDot(final boolean dot) {
        this.dot = dot;
    }

    public final
    Heroes getOpponent() {
        return opponent;
    }

    public final
    void setOpponent(final Heroes opponent) {
        this.opponent = opponent;
    }

    public final
    int getRoundOfIgnite() {
        return roundOfIgnite;
    }

    public final
    void setRoundOfIgnite(final int roundOfIgnite) {
        this.roundOfIgnite = roundOfIgnite;
    }

    public final int getDamageThisRound() {
        return damageThisRound;
    }

    public final void setDamageThisRound(final int damageThisRound) {

        this.damageThisRound = damageThisRound;
    }
}
