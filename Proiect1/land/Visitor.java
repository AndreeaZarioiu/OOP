package land;

import heroes.Heroes;
import heroes.Knight;
import heroes.Pyromancer;
import heroes.Wizard;
import heroes.Rogue;


public interface Visitor {
     void visit(Heroes hero);
     void visit(Knight hero);
     void visit(Pyromancer hero);
     void visit(Rogue hero);
     void visit(Wizard hero);
}
