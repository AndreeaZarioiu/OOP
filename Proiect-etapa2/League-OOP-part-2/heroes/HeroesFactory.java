package heroes;

import java.util.HashMap;
import java.util.Map;

public
final class HeroesFactory {
    private
    static HeroesFactory instance = null;

    private
    static class RaceModifiersKnight {  // modificatori pentru victima knight
        public
        static final float FIREBLAST = 1.2f;
        public
        static final float IGNITE = 1.2f;
        public
        static final float EXECUTE = 1f;
        public
        static final float SLAM = 1.2f;
        public
        static final float DRAIN = 1.2f;
        public
        static final float DEFLECT = 1.4f;
        public
        static final float BACKSTAB = 0.9f;
        public
        static final float PARALYSIS = 0.8f;
    } private static class RaceModifiersRogue {  // modificatori pentru victima
        // rogue
        public
        static final float FIREBLAST = 0.8f;
        public
        static final float IGNITE = 0.8f;
        public
        static final float EXECUTE = 1.15f;
        public
        static final float SLAM = 0.8f;
        public
        static final float DRAIN = 0.8f;
        public
        static final float DEFLECT = 1.2f;
        public
        static final float BACKSTAB = 1.2f;
        public
        static final float PARALYSIS = 0.9f;
    } private static class RaceModifiersWizard {  // modificatori pentru victima
        // wizard
        public
        static final float FIREBLAST = 1.05f;
        public
        static final float IGNITE = 1.05f;
        public
        static final float EXECUTE = 0.8f;
        public
        static final float SLAM = 1.05f;
        public
        static final float DRAIN = 1.05f;
        public
        static final float DEFLECT = 1f;
        public
        static final float BACKSTAB = 1.25f;
        public
        static final float PARALYSIS = 1.25f;
    } private static class RaceModifiersPyromancer {  // modificatori pentru
        // victima pyromancer
        public
        static final float FIREBLAST = 0.9f;
        public
        static final float IGNITE = 0.9f;
        public
        static final float EXECUTE = 1.1f;
        public
        static final float SLAM = 0.9f;
        public
        static final float DRAIN = 0.9f;
        public
        static final float DEFLECT = 1.3f;
        public
        static final float BACKSTAB = 1.25f;
        public
        static final float PARALYSIS = 1.20f;
    }
    private static class KnightStrategy {
        public
        static final int LOW = 3;
        public
        static final int HIGH = 2;
        public
        static final int DECREASE_HP = 5;
        public
        static final float INCREASE_COEF = 0.5f;
        public
        static final float DECREASE_COEF = -0.2f;
        public
        static final int INCREASE_HP = 4;
    }
    private static class PyromancerStrategy {
        public
        static final int LOW = 4;
        public
        static final int HIGH = 3;
        public
        static final int DECREASE_HP = 4;
        public
        static final float INCREASE_COEF = 0.7f;
        public
        static final float DECREASE_COEF = -0.3f;
        public
        static final int INCREASE_HP = 3;
    }
    private static class RogueStrategy {
        public
        static final int LOW = 7;
        public
        static final int HIGH = 5;
        public
        static final int DECREASE_HP = 7;
        public
        static final float INCREASE_COEF = 0.4f;
        public
        static final float DECREASE_COEF = -0.1f;
        public
        static final int INCREASE_HP = 2;
    }
    private static class WizardStrategy {
        public
        static final int LOW = 4;
        public
        static final int HIGH = 2;
        public
        static final int DECREASE_HP = 10;
        public
        static final float INCREASE_COEF = 0.6f;
        public
        static final float DECREASE_COEF = -0.2f;
        public
        static final int INCREASE_HP = 5;
    }
    private final Map<Enum, Float> modifiersForFireblast;
    private
    final Map<Enum, Float> modifiersForIgnite;
    private
    final Map<Enum, Float> modifiersForExecute;
    private
    final Map<Enum, Float> modifiersForSlam;
    private
    final Map<Enum, Float> modifiersForDrain;
    private
    final Map<Enum, Float> modifiersForDeflect;
    private
    final Map<Enum, Float> modifiersForBackstab;
    private
    final Map<Enum, Float> modifiersForParalysis;
    private
    final Map<Enum, Float> lowLimit;
    private
    final Map<Enum, Float> highLimit;
    private
    final Map<Enum, Float> decHp;
    private
    final Map<Enum, Float> incCoef;
    private
    final Map<Enum, Float> incHp;
    private
    final Map<Enum, Float> decCoef;

    private
    HeroesFactory() {
        modifiersForFireblast = new HashMap<Enum, Float>();
        modifiersForIgnite = new HashMap<Enum, Float>();
        modifiersForExecute = new HashMap<Enum, Float>();
        modifiersForSlam = new HashMap<Enum, Float>();
        modifiersForDrain = new HashMap<Enum, Float>();
        modifiersForDeflect = new HashMap<Enum, Float>();
        modifiersForBackstab = new HashMap<Enum, Float>();
        modifiersForParalysis = new HashMap<Enum, Float>();
        lowLimit = new HashMap<>();
        highLimit = new HashMap<>();
        incCoef = new HashMap<>();
        incHp = new HashMap<>();
        decHp = new HashMap<>();
        decCoef = new HashMap<>();
        initModifiers();
    }
    private
    void initModifiers() {
        modifiersForFireblast.put(HeroesType.Knight, RaceModifiersKnight.FIREBLAST);
        modifiersForFireblast.put(HeroesType.Wizard, RaceModifiersWizard.FIREBLAST);
        modifiersForFireblast.put(HeroesType.Rogue, RaceModifiersRogue.FIREBLAST);
        modifiersForFireblast.put(HeroesType.Pyromancer,
                RaceModifiersPyromancer.FIREBLAST);

        modifiersForIgnite.put(HeroesType.Knight, RaceModifiersKnight.IGNITE);
        modifiersForIgnite.put(HeroesType.Wizard, RaceModifiersWizard.IGNITE);
        modifiersForIgnite.put(HeroesType.Rogue, RaceModifiersRogue.IGNITE);
        modifiersForIgnite.put(HeroesType.Pyromancer,
                RaceModifiersPyromancer.IGNITE);

        modifiersForExecute.put(HeroesType.Knight, RaceModifiersKnight.EXECUTE);
        modifiersForExecute.put(HeroesType.Wizard, RaceModifiersWizard.EXECUTE);
        modifiersForExecute.put(HeroesType.Rogue, RaceModifiersRogue.EXECUTE);
        modifiersForExecute.put(HeroesType.Pyromancer,
                RaceModifiersPyromancer.EXECUTE);

        modifiersForSlam.put(HeroesType.Knight, RaceModifiersKnight.SLAM);
        modifiersForSlam.put(HeroesType.Wizard, RaceModifiersWizard.SLAM);
        modifiersForSlam.put(HeroesType.Rogue, RaceModifiersRogue.SLAM);
        modifiersForSlam.put(HeroesType.Pyromancer, RaceModifiersPyromancer.SLAM);

        modifiersForDrain.put(HeroesType.Knight, RaceModifiersKnight.DRAIN);
        modifiersForDrain.put(HeroesType.Wizard, RaceModifiersWizard.DRAIN);
        modifiersForDrain.put(HeroesType.Rogue, RaceModifiersRogue.DRAIN);
        modifiersForDrain.put(HeroesType.Pyromancer, RaceModifiersPyromancer.DRAIN);

        modifiersForDeflect.put(HeroesType.Knight, RaceModifiersKnight.DEFLECT);
        modifiersForDeflect.put(HeroesType.Wizard, RaceModifiersWizard.DEFLECT);
        modifiersForDeflect.put(HeroesType.Rogue, RaceModifiersRogue.DEFLECT);
        modifiersForDeflect.put(HeroesType.Pyromancer,
                RaceModifiersPyromancer.DEFLECT);

        modifiersForParalysis.put(HeroesType.Knight, RaceModifiersKnight.PARALYSIS);
        modifiersForParalysis.put(HeroesType.Wizard, RaceModifiersWizard.PARALYSIS);
        modifiersForParalysis.put(HeroesType.Rogue, RaceModifiersRogue.PARALYSIS);
        modifiersForParalysis.put(HeroesType.Pyromancer,
                RaceModifiersPyromancer.PARALYSIS);

        modifiersForBackstab.put(HeroesType.Knight, RaceModifiersKnight.BACKSTAB);
        modifiersForBackstab.put(HeroesType.Wizard, RaceModifiersWizard.BACKSTAB);
        modifiersForBackstab.put(HeroesType.Rogue, RaceModifiersRogue.BACKSTAB);
        modifiersForBackstab.put(HeroesType.Pyromancer,
                RaceModifiersPyromancer.BACKSTAB);

        lowLimit.put(HeroesType.Knight, (float) KnightStrategy.LOW);
        lowLimit.put(HeroesType.Wizard, (float) WizardStrategy.LOW);
        lowLimit.put(HeroesType.Rogue, (float) RogueStrategy.LOW);
        lowLimit.put(HeroesType.Pyromancer,
                (float) PyromancerStrategy.LOW);

        highLimit.put(HeroesType.Knight, (float) KnightStrategy.HIGH);
        highLimit.put(HeroesType.Wizard, (float) WizardStrategy.HIGH);
        highLimit.put(HeroesType.Rogue, (float) RogueStrategy.HIGH);
        highLimit.put(HeroesType.Pyromancer,
                (float) PyromancerStrategy.HIGH);

        incCoef.put(HeroesType.Knight, KnightStrategy.INCREASE_COEF);
        incCoef.put(HeroesType.Wizard, WizardStrategy.INCREASE_COEF);
        incCoef.put(HeroesType.Rogue, RogueStrategy.INCREASE_COEF);
        incCoef.put(HeroesType.Pyromancer,
                PyromancerStrategy.INCREASE_COEF);
        decCoef.put(HeroesType.Knight, KnightStrategy.DECREASE_COEF);
        decCoef.put(HeroesType.Wizard, WizardStrategy.DECREASE_COEF);
        decCoef.put(HeroesType.Rogue, RogueStrategy.DECREASE_COEF);
        decCoef.put(HeroesType.Pyromancer,
                PyromancerStrategy.DECREASE_COEF);

        incHp.put(HeroesType.Knight, (float) KnightStrategy.INCREASE_HP);
        incHp.put(HeroesType.Wizard, (float) WizardStrategy.INCREASE_HP);
        incHp.put(HeroesType.Rogue, (float) RogueStrategy.INCREASE_HP);
        incHp.put(HeroesType.Pyromancer,
                (float) PyromancerStrategy.INCREASE_HP);
        decHp.put(HeroesType.Knight, (float) KnightStrategy.DECREASE_HP);
        decHp.put(HeroesType.Wizard, (float) WizardStrategy.DECREASE_HP);
        decHp.put(HeroesType.Rogue, (float) RogueStrategy.DECREASE_HP);
        decHp.put(HeroesType.Pyromancer,
                (float) PyromancerStrategy.DECREASE_HP);
    }
    public
    static HeroesFactory getInstance() {
        if (instance == null) {
            instance = new HeroesFactory();
        }
        return instance;
    }

    public
    float getFireblast(final HeroesType type) {
        return modifiersForFireblast.get(type);
    }
    public
    float getIgnite(final HeroesType type) {
        return modifiersForIgnite.get(type);
    }
    public
    float getExecute(final HeroesType type) {
        return modifiersForExecute.get(type);
    }
    public
    float getSlam(final HeroesType type) {
        return modifiersForSlam.get(type);
    }
    public
    float getDrain(final HeroesType type) {
        return modifiersForDrain.get(type);
    }
    public
    float getDeflect(final HeroesType type) {
        return modifiersForDeflect.get(type);
    }
    public
    float getParalysis(final HeroesType type) {
        return modifiersForParalysis.get(type);
    }
    public
    float getBackstab(final HeroesType type) {
        return modifiersForBackstab.get(type);
    }
    public
    float getLow(final HeroesType type) {
        return lowLimit.get(type);
    }
    public
    float getHigh(final HeroesType type) {
        return highLimit.get(type);
    }
    public
    float getIncCoef(final HeroesType type) {
        return incCoef.get(type);
    }
    public
    float getDecCoef(final HeroesType type) {
        return decCoef.get(type);
    }
    public
    float getIncHp(final HeroesType type) {
        return incHp.get(type);
    }
    public
    float getDecHp(final HeroesType type) {
        return decHp.get(type);
    }
}
