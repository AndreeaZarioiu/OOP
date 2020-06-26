package land;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public
final class LandModifiers {
    private
    static LandModifiers instance = null;
    private
    static class ForPyromancer {
        public
        static final float FIREBLAST = 1.25f;  // volcanic
        public
        static final float IGNITE = 1.25f;
    } private static class ForKnight {
        public
        static final float EXECUTE = 1.15f;  // src.land
        public
        static final float SLAM = 1.15f;
    } private static class ForWizard {  // desert
        public
        static final float DRAIN = 1.1f;
        public
        static final float DEFLECT = 1.1f;
    } private static class ForRogue {  // woods
        public
        static final float BACKSTAB = 1.15f;
        public
        static final float PARALYSIS = 1.15f;
    } private final Map<String, ArrayList<Float>> modifiersForKnight;
    private
    final Map<String, ArrayList<Float>> modifiersForWizard;
    private
    final Map<String, ArrayList<Float>> modifiersForRogue;
    private
    final Map<String, ArrayList<Float>> modifiersForPyromancer;
    private
    LandModifiers() {
        modifiersForKnight = new HashMap<>();
        modifiersForWizard = new HashMap<>();
        modifiersForRogue = new HashMap<>();
        modifiersForPyromancer = new HashMap<>();
        initModifiers();
    }
    private
    void initModifiers() {
        ArrayList<Float> modifiers = new ArrayList<>();
        modifiers.add(ForPyromancer.FIREBLAST);
        modifiers.add(ForPyromancer.IGNITE);
        modifiersForPyromancer.put("V", new ArrayList<>(modifiers));
        modifiers.clear();
        modifiers.add(1f);
        modifiers.add(1f);
        modifiersForPyromancer.put("L", modifiers);
        modifiersForPyromancer.put("D", modifiers);
        modifiersForPyromancer.put("W", modifiers);

        modifiers.clear();
        modifiers.add(ForKnight.EXECUTE);
        modifiers.add(ForKnight.SLAM);
        modifiersForKnight.put("L", new ArrayList<>(modifiers));
        modifiers.clear();
        modifiers.add(1f);
        modifiers.add(1f);
        modifiersForKnight.put("V", modifiers);
        modifiersForKnight.put("D", modifiers);
        modifiersForKnight.put("W", modifiers);

        modifiers.clear();
        modifiers.add(ForWizard.DRAIN);
        modifiers.add(ForWizard.DEFLECT);
        modifiersForWizard.put("D", new ArrayList<>(modifiers));
        modifiers.clear();
        modifiers.add(1f);
        modifiers.add(1f);
        modifiersForWizard.put("L", modifiers);
        modifiersForWizard.put("W", modifiers);
        modifiersForWizard.put("V", modifiers);

        modifiers.clear();
        modifiers.add(ForRogue.BACKSTAB);
        modifiers.add(ForRogue.PARALYSIS);
        modifiersForRogue.put("W", new ArrayList<>(modifiers));
        modifiers.clear();
        modifiers.add(1f);
        modifiers.add(1f);
        modifiersForRogue.put("L", modifiers);
        modifiersForRogue.put("D", modifiers);
        modifiersForRogue.put("V", modifiers);
    }
    public
    static LandModifiers getInstance() {
        if (instance == null) {
            instance = new LandModifiers();
        }
        return instance;
    }
    public
    ArrayList<Float> getForKnight(final String land) {
        return modifiersForKnight.get(land);
    }
    public
    ArrayList<Float> getForRogue(final String land) {
        return modifiersForRogue.get(land);
    }
    public
    ArrayList<Float> getForWizard(final String land) {
        return modifiersForWizard.get(land);
    }
    public
    ArrayList<Float> getForPyromancer(final String land) {
        return modifiersForPyromancer.get(land);
    }
}
