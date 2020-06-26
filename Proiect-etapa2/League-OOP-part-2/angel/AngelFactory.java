package angel;

import heroes.HeroesType;

import java.util.HashMap;
import java.util.Map;

public final
class AngelFactory {
    private
    static AngelFactory instance = null;

    private
    static class DamageAngel {
        public
        static final float KNIGHT = 0.15f;
        public
        static final float PYROMANCER = 0.2f;
        public
        static final float ROGUE = 0.3f;
        public
        static final float WIZARD = 0.4f;
    } private static class DarkAngel {
        public
        static final int KNIGHT = -40;
        public
        static final int PYROMANCER = -30;
        public
        static final int ROGUE = -10;
        public
        static final int WIZARD = -20;
    } private static class Dracula {
        public
        static final float KNIGHT_DAMAGE = -0.2f;
        public
        static final float PYROMANCER_DAMAGE = -0.3f;
        public
        static final float ROGUE_DAMAGE = -0.1f;
        public
        static final float WIZARD_DAMAGE = -0.4f;
        public
        static final int KNIGHT_HP = -60;
        public
        static final int PYROMANCER_HP = -40;
        public
        static final int ROGUE_HP = -35;
        public
        static final int WIZARD_HP = -20;
    } private static class GoodBoy {
        public
        static final float KNIGHT_DAMAGE = 0.6f;
        public
        static final float PYROMANCER_DAMAGE = 0.5f;
        public
        static final float ROGUE_DAMAGE = 0.4f;
        public
        static final float WIZARD_DAMAGE = 0.3f;
        public
        static final int KNIGHT_HP = 20;
        public
        static final int PYROMANCER_HP = 40;
        public
        static final int ROGUE_HP = 40;
        public
        static final int WIZARD_HP = 50;
    } private static class LevelUpAngel {
        public
        static final float KNIGHT = 0.1f;
        public
        static final float PYROMANCER = 0.2f;
        public
        static final float ROGUE = 0.15f;
        public
        static final float WIZARD = 0.25f;
    } private static class LifeGiver {
        public
        static final int KNIGHT = 100;
        public
        static final int PYROMANCER = 80;
        public
        static final int ROGUE = 90;
        public
        static final int WIZARD = 120;
    } private static class SmallAngel {
        public
        static final float KNIGHT_DAMAGE = 0.1f;
        public
        static final float PYROMANCER_DAMAGE = 0.15f;
        public
        static final float ROGUE_DAMAGE = 0.05f;
        public
        static final float WIZARD_DAMAGE = 0.1f;
        public
        static final int KNIGHT_HP = 10;
        public
        static final int PYROMANCER_HP = 15;
        public
        static final int ROGUE_HP = 20;
        public
        static final int WIZARD_HP = 25;
    } private static class Spawner {
        public
        static final int KNIGHT = 200;
        public
        static final int PYROMANCER = 150;
        public
        static final int ROGUE = 180;
        public
        static final int WIZARD = 120;
    } private static class XPAngel {
        public
        static final int KNIGHT = 45;
        public
        static final int PYROMANCER = 50;
        public
        static final int ROGUE = 40;
        public
        static final int WIZARD = 60;
    } private final Map<Enum, Float> damageAngel;
    private
    final Map<Enum, Integer> darkAngel;
    private
    final Map<Enum, Float> draculaDamage;
    private
    final Map<Enum, Integer> draculaHp;
    private
    final Map<Enum, Float> goodBoyDamage;
    private
    final Map<Enum, Integer> goodBoyHp;
    private
    final Map<Enum, Float> levelUpAngel;
    private
    final Map<Enum, Integer> lifeGiver;
    private
    final Map<Enum, Float> smallAngelDamage;
    private
    final Map<Enum, Integer> smallAngelHp;
    private
    final Map<Enum, Integer> spawner;
    private
    final Map<Enum, Integer> xpAngel;
    private
    AngelFactory() {
        damageAngel = new HashMap<Enum, Float>();
        darkAngel = new HashMap<Enum, Integer>();
        draculaDamage = new HashMap<Enum, Float>();
        draculaHp = new HashMap<Enum, Integer>();
        goodBoyDamage = new HashMap<Enum, Float>();
        goodBoyHp = new HashMap<Enum, Integer>();
        levelUpAngel = new HashMap<>();
        lifeGiver = new HashMap<>();
        spawner = new HashMap<>();
        smallAngelDamage = new HashMap<>();
        smallAngelHp = new HashMap<>();
        xpAngel = new HashMap<>();
        initModifiers();
    }
    private
    void initModifiers() {
        damageAngel.put(HeroesType.Knight, DamageAngel.KNIGHT);
        damageAngel.put(HeroesType.Pyromancer, DamageAngel.PYROMANCER);
        damageAngel.put(HeroesType.Rogue, DamageAngel.ROGUE);
        damageAngel.put(HeroesType.Wizard, DamageAngel.WIZARD);

        darkAngel.put(HeroesType.Knight, DarkAngel.KNIGHT);
        darkAngel.put(HeroesType.Pyromancer, DarkAngel.PYROMANCER);
        darkAngel.put(HeroesType.Rogue, DarkAngel.ROGUE);
        darkAngel.put(HeroesType.Wizard, DarkAngel.WIZARD);

        draculaDamage.put(HeroesType.Knight, Dracula.KNIGHT_DAMAGE);
        draculaDamage.put(HeroesType.Pyromancer, Dracula.PYROMANCER_DAMAGE);
        draculaDamage.put(HeroesType.Rogue, Dracula.ROGUE_DAMAGE);
        draculaDamage.put(HeroesType.Wizard, Dracula.WIZARD_DAMAGE);

        draculaHp.put(HeroesType.Knight, Dracula.KNIGHT_HP);
        draculaHp.put(HeroesType.Pyromancer, Dracula.PYROMANCER_HP);
        draculaHp.put(HeroesType.Rogue, Dracula.ROGUE_HP);
        draculaHp.put(HeroesType.Wizard, Dracula.WIZARD_HP);

        goodBoyDamage.put(HeroesType.Knight, GoodBoy.KNIGHT_DAMAGE);
        goodBoyDamage.put(HeroesType.Pyromancer, GoodBoy.PYROMANCER_DAMAGE);
        goodBoyDamage.put(HeroesType.Rogue, GoodBoy.ROGUE_DAMAGE);
        goodBoyDamage.put(HeroesType.Wizard, GoodBoy.WIZARD_DAMAGE);

        goodBoyHp.put(HeroesType.Knight, GoodBoy.KNIGHT_HP);
        goodBoyHp.put(HeroesType.Pyromancer, GoodBoy.PYROMANCER_HP);
        goodBoyHp.put(HeroesType.Rogue, GoodBoy.ROGUE_HP);
        goodBoyHp.put(HeroesType.Wizard, GoodBoy.WIZARD_HP);

        levelUpAngel.put(HeroesType.Knight, LevelUpAngel.KNIGHT);
        levelUpAngel.put(HeroesType.Pyromancer, LevelUpAngel.PYROMANCER);
        levelUpAngel.put(HeroesType.Rogue, LevelUpAngel.ROGUE);
        levelUpAngel.put(HeroesType.Wizard, LevelUpAngel.WIZARD);

        lifeGiver.put(HeroesType.Knight, LifeGiver.KNIGHT);
        lifeGiver.put(HeroesType.Pyromancer, LifeGiver.PYROMANCER);
        lifeGiver.put(HeroesType.Rogue, LifeGiver.ROGUE);
        lifeGiver.put(HeroesType.Wizard, LifeGiver.WIZARD);

        smallAngelDamage.put(HeroesType.Knight, SmallAngel.KNIGHT_DAMAGE);
        smallAngelDamage.put(HeroesType.Pyromancer, SmallAngel.PYROMANCER_DAMAGE);
        smallAngelDamage.put(HeroesType.Rogue, SmallAngel.ROGUE_DAMAGE);
        smallAngelDamage.put(HeroesType.Wizard, SmallAngel.WIZARD_DAMAGE);

        smallAngelHp.put(HeroesType.Knight, SmallAngel.KNIGHT_HP);
        smallAngelHp.put(HeroesType.Pyromancer, SmallAngel.PYROMANCER_HP);
        smallAngelHp.put(HeroesType.Rogue, SmallAngel.ROGUE_HP);
        smallAngelHp.put(HeroesType.Wizard, SmallAngel.WIZARD_HP);

        spawner.put(HeroesType.Knight, Spawner.KNIGHT);
        spawner.put(HeroesType.Pyromancer, Spawner.PYROMANCER);
        spawner.put(HeroesType.Rogue, Spawner.ROGUE);
        spawner.put(HeroesType.Wizard, Spawner.WIZARD);

        xpAngel.put(HeroesType.Knight, XPAngel.KNIGHT);
        xpAngel.put(HeroesType.Pyromancer, XPAngel.PYROMANCER);
        xpAngel.put(HeroesType.Rogue, XPAngel.ROGUE);
        xpAngel.put(HeroesType.Wizard, XPAngel.WIZARD);
    }
    public
    static AngelFactory getInstance() {
        if (instance == null) {
            instance = new AngelFactory();
        }
        return instance;
    }

    public
    float getDamageAngel(final HeroesType type) {
        return damageAngel.get(type);
    }
    public
    int getDarkAngel(final HeroesType type) {
        return darkAngel.get(type);
    }
    public
    float getDraculaDamage(final HeroesType type) {
        return draculaDamage.get(type);
    }
    public
    int getDraculaHp(final HeroesType type) {
        return draculaHp.get(type);
    }
    public
    float getGoodBoyDamage(final HeroesType type) {
        return goodBoyDamage.get(type);
    }
    public
    int getGoodBoyHp(final HeroesType type) {
        return goodBoyHp.get(type);
    }
    public
    float getLevelUpAngel(final HeroesType type) {
        return levelUpAngel.get(type);
    }
    public
    int getLifeGiver(final HeroesType type) {
        return lifeGiver.get(type);
    }
    public
    float getSmallAngelDamage(final HeroesType type) {
        return smallAngelDamage.get(type);
    }
    public
    int getSmallAngelHp(final HeroesType type) {
        return smallAngelHp.get(type);
    }
    public
    int getSpawner(final HeroesType type) {
        return spawner.get(type);
    }
    public
    int getXpAngel(final HeroesType type) {
        return xpAngel.get(type);
    }
}
