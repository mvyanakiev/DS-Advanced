package core;

import models.Hero;
import models.HeroType;

import java.util.*;
import java.util.stream.Collectors;

public class HeroDatabaseImpl implements HeroDatabase {
    private Map<String, Hero> heroes;
    private Map<HeroType, List<Hero>> heroesByType;


    public HeroDatabaseImpl() {
        this.heroes = new HashMap<>();
        this.heroesByType = new HashMap<>();
    }

    @Override
    public void addHero(Hero hero) {
        if (this.contains(hero)) {
            throw new IllegalArgumentException();
        }

        this.heroes.put(hero.getName(), hero);
        this.heroesByType.putIfAbsent(hero.getHeroType(), new ArrayList<>());
        this.heroesByType.get(hero.getHeroType()).add(hero);
    }

    @Override
    public boolean contains(Hero hero) {
        return this.heroes.containsKey(hero.getName());
    }

    @Override
    public int size() {
        return this.heroes.size();
    }

    @Override
    public Hero getHero(String name) {
        if (!this.heroes.containsKey(name)) {
            throw new IllegalArgumentException();
        }
        return this.heroes.get(name);
    }

    @Override
    public Hero remove(String name) {
        Hero toRemove = this.getHero(name);

        this.heroesByType.remove(toRemove.getHeroType());
        return this.heroes.remove(toRemove.getName());
    }

    @Override
    public Iterable<Hero> removeAllByType(HeroType type) {
        List<Hero> result = new ArrayList<>();

        if (this.heroesByType.containsKey(type)) {
            result = this.heroesByType.remove(type);

            if (result.size() > 0) {
                for (Hero hero : result) {
                    this.heroes.remove(hero.getName());
                }
            }
        }

        return result;
    }

    @Override
    public void levelUp(String name) {
        Hero toIncrease = this.getHero(name);

        int newLevel = toIncrease.getLevel() + 1;
        toIncrease.setLevel(newLevel);

        this.heroes.put(toIncrease.getName(), toIncrease);
        this.heroesByType.get(toIncrease.getHeroType()).remove(toIncrease);
        this.heroesByType.get(toIncrease.getHeroType()).add(toIncrease);
    }

    @Override
    public void rename(String oldName, String newName) {
        if (!this.heroes.containsKey(oldName) || this.heroes.containsKey(newName)) {
            throw new IllegalArgumentException();
        }

        Hero toRename = this.heroes.remove(oldName);

        toRename.setName(newName);
        this.addHero(toRename);
    }

    @Override
    public Iterable<Hero> getAllByType(HeroType type) {

        List<Hero> result = new ArrayList<>();

        result = this.heroesByType.get(type)
                .stream()
                .sorted(Comparator.comparing(Hero::getName))
                .collect(Collectors.toList());

        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Iterable<Hero> getAllByLevel(int level) {
        List<Hero> result = new ArrayList<>();

        result = this.heroes.values().stream()
                .filter(hero -> hero.getLevel() == level)
                .sorted(Comparator.comparing(Hero::getName))
                .collect(Collectors.toList());


        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Iterable<Hero> getInPointsRange(int lowerBound, int upperBound) {
        List<Hero> result = new ArrayList<>();

        result = this.heroes.values()
                .stream()
                .filter(hero -> hero.getPoints() >= lowerBound && hero.getPoints() < upperBound)
                .sorted((l,r) ->{
                    int subPoints = Integer.compare(r.getPoints(), l.getPoints());

                    if (subPoints == 0){
                        return Integer.compare(l.getLevel(), r.getLevel());
                    } else {
                        return subPoints;
                    }
                })
                .collect(Collectors.toList());

        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Iterable<Hero> getAllOrderedByLevelDescendingThenByName() {
        List<Hero> result = new ArrayList<>();

        result = this.heroes.values()
                .stream()
                .sorted((l,r) ->{
                    int levelCompare = Integer.compare(r.getLevel(), l.getLevel());

                    if (levelCompare == 0){
                        return l.getName().compareTo(r.getName());
                    } else {
                        return levelCompare;
                    }
                })
                .collect(Collectors.toList());

        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<>();
        }
    }
}