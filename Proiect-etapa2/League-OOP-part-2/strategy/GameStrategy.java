package strategy;

import heroes.Heroes;
import heroes.HeroesFactory;

public interface GameStrategy {
    HeroesFactory VALUES = HeroesFactory.getInstance();
    void attack(Heroes hero);
}
