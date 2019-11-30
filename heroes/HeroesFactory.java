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
    } private final Map<Enum, Float> modifiersForFireblast;
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
    HeroesFactory() {
        modifiersForFireblast = new HashMap<Enum, Float>();
        modifiersForIgnite = new HashMap<Enum, Float>();
        modifiersForExecute = new HashMap<Enum, Float>();
        modifiersForSlam = new HashMap<Enum, Float>();
        modifiersForDrain = new HashMap<Enum, Float>();
        modifiersForDeflect = new HashMap<Enum, Float>();
        modifiersForBackstab = new HashMap<Enum, Float>();
        modifiersForParalysis = new HashMap<Enum, Float>();
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
}
