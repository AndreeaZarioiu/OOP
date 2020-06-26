package land;

import heroes.Knight;
import heroes.Wizard;
import heroes.Pyromancer;
import heroes.Rogue;
import heroes.Heroes;

import java.util.ArrayList;
import java.util.HashMap;

public
final class ApplyLandModifiers implements Visitor {
    private
    HashMap<ArrayList<Integer>, String> map;
    private
    LandModifiers modifiers = LandModifiers.getInstance();
    public
    ApplyLandModifiers(final HashMap<ArrayList<Integer>, String> map) {
        this.map = map;
    }

    @Override public void visit(final Heroes hero) {
        hero.noLandModifiers();
    }

    @Override public void visit(final Knight hero) {
        if (map.get(hero.getCoordonates()).equals("L")) {
            hero.setLandModifiers(modifiers.getForKnight("L"));
            return;
        }
       hero.noLandModifiers();
    }

    @Override public void visit(final Pyromancer hero) {
        if (map.get(hero.getCoordonates()).equals("V")) {
            hero.setLandModifiers(modifiers.getForPyromancer("V"));
            return;
        }
       hero.noLandModifiers();
    }
    @Override public void visit(final Rogue hero) {

        if (map.get(hero.getCoordonates()).equals("W")) {
            hero.setLandModifiers(modifiers.getForRogue("W"));
            return;
        }
       hero.noLandModifiers();
    }

    @Override public void visit(final Wizard hero) {
        if (map.get(hero.getCoordonates()).equals("D")) {
            hero.setLandModifiers(modifiers.getForWizard("D"));
            return;
        }
        hero.noLandModifiers();
    }
}
