package main;
import fileio.FileSystem;
import game.GameCourse;
import heroes.Heroes;
import land.Map;


import java.util.ArrayList;
import java.util.HashMap;


public
final class Main {
    private Main() {
    }
    public
    static void main(final String[] args) {
        try {
            FileSystem fs = new FileSystem(args[0], args[1]);
            int n, m;
            n = fs.nextInt();
            m = fs.nextInt();
            Map gameMap = new Map(n, m);
            HashMap<ArrayList<Integer>, String> map = new HashMap<>();
            ArrayList<Heroes> heroes;
            for (int i = 0; i < n; i++) { // adding lands
                String line = fs.nextWord();
                for (int j = 0; j < m; j++) {
                    ArrayList<Integer> location = new ArrayList<>();
                    location.add(i);
                    location.add(j);
                    map.put(location, line.substring(j, j + 1));
                }
            }
            GameCourse game = new GameCourse(fs, map, gameMap);
            heroes = game.startGame(); // playing rounds
            for (Heroes iter : heroes) { // final results
                if (iter.getHp() <= 0) {
                    fs.writeWord(iter.getType().toString().substring(0, 1)
                            + " dead\n");
                } else {
                    fs.writeWord(iter.getType().toString().substring(0, 1)
                            + " "
                            +
                            iter.getLevel()
                            + " "
                            + iter.getXp()
                            + " "
                            +
                            iter.getHp()
                            + " "
                            + iter.getCoordonates().get(0)
                            + " "
                            + iter.getCoordonates().get(1) + "\n");
                }
            }
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
