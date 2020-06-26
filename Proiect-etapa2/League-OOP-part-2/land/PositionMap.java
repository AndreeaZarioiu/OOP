package land;

import java.util.ArrayList;
import java.util.HashMap;

public final class PositionMap {
    private static PositionMap instance = null;
    private HashMap<ArrayList<Integer>, String> map = new HashMap<>();

    private PositionMap(final HashMap<ArrayList<Integer>, String> m) {
        map = m;
    }

    public static PositionMap getInstance(final HashMap<ArrayList<Integer>, String> m) {
        if (instance == null) {
            instance = new PositionMap(m);
        }
        return instance;
    }
    public HashMap<ArrayList<Integer>, String> getMap() {
        return map;
    }
}
