package land;

import java.util.ArrayList;
import java.util.HashMap;

public final class Map {
    private int length;
    private int high;
    private HashMap<ArrayList<Integer>, String> map = new HashMap<>();
    private HashMap<ArrayList<Integer>, ArrayList<Integer>> located = new HashMap<>();
    private ArrayList<ArrayList<Integer>> conflicts = new ArrayList<>();
    private int numberOfConflicts;
    public Map(final int high, final int length) {
        this.length = length;
        this.high = high;
        numberOfConflicts = 0;
    }
    public HashMap<ArrayList<Integer>, String> getMap() {
        return map;
    }

    public void setMap(final HashMap<ArrayList<Integer>, String> map) {
        this.map = map;
    }

    public HashMap<ArrayList<Integer>, ArrayList<Integer>> getLocated() {
        return located;
    }
    public void addHero(final ArrayList<Integer> position, final int id) {
        located.get(position).add(id);
    }
    public void newLocations() {
        for (int i = 0; i < high; i++) {
            for (int j = 0; j < length; j++) {
                ArrayList<Integer> coordinates = new ArrayList<>();
                ArrayList<Integer> heroes = new ArrayList<>();
                coordinates.add(i);
                coordinates.add(j);
                located.put(coordinates, heroes);
            }
        }
    }
    private void setConflicts() {
        numberOfConflicts = 0;
        conflicts.clear();
        for (int i = 0; i < high; i++) {
           for (int j = 0; j < length; j++) {
               ArrayList<Integer> coordinates = new ArrayList<>();
               coordinates.add(i);
               coordinates.add(j);
               if (located.get(coordinates).size() > 1) {
                   conflicts.add(located.get(coordinates));
                   numberOfConflicts++;
               }
           }
        }
    }
    public int getNumberOfConflicts() {
        return numberOfConflicts;
    }
    public ArrayList<ArrayList<Integer>> getConflicts() {
        setConflicts();
        return conflicts;
    }
}
