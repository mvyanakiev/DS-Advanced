import java.util.*;
import java.util.stream.Collectors;

public class ComputerImpl implements Computer {
    private int energy;
    private Set<Invader> invaders;
    private Map<Integer, List<Invader>> invadersByDistance;
//    private List<Invader> invaderList;


    public ComputerImpl(int energy) {
        if (energy > 0) {
            this.energy = energy;
            this.invaders = new LinkedHashSet<>();
//                    new TreeSet<>(Comparator.comparingInt(Invader::getDistance)
//                    .thenComparing(Invader::getDamage).reversed());
            this.invadersByDistance = new TreeMap<>();

//            this.invaderList = new ArrayList<>();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getEnergy() {
        if (this.energy < 0) {
            return 0;
        } else {
            return this.energy;
        }
    }

    public void addInvader(Invader invader) {
        this.invaders.add(invader);
        this.invadersByDistance.putIfAbsent(invader.getDistance(), new ArrayList<>());
        this.invadersByDistance.get(invader.getDistance()).add(invader);
    }

    public void skip(int turns) {

        List<Invader> toRemove = new ArrayList<>();

        for (Invader invader : invaders) {
            int currentDistance = invader.getDistance();
            int newDistance = currentDistance - turns;
            invader.setDistance(newDistance);

            if (newDistance <= 0) {
                this.energy = this.energy - invader.getDamage();
                toRemove.add(invader);
            } else {
                this.invadersByDistance.putIfAbsent(newDistance, new ArrayList<>());
                this.invadersByDistance.get(newDistance).add(invader);
            }

        }

        for (Invader invader : toRemove) {
            this.invaders.remove(invader);
        }
    }

    public void destroyTargetsInRadius(int radius) {

        List<Integer> keysToRemove = new ArrayList<>();

        for (Map.Entry<Integer, List<Invader>> pair : invadersByDistance.entrySet()) {
            if (pair.getKey() <= radius) {
                keysToRemove.add(pair.getKey());
                for (Invader invader : pair.getValue()) {
                    this.invaders.remove(invader);
                }
            } else {
                break;
            }
        }

        for (Integer key : keysToRemove) {
            this.invadersByDistance.remove(key);
        }

// too slow
//        this.invadersByDistance.forEach((k, v) -> {
//            if (k <= radius) {
//                this.invaders.removeAll(v);
//                v.clear();
//            }
//        });
    }

    public void destroyHighestPriorityTargets(int n) {

//        if (this.invaders.size() > 0) {
//
//            List<Invader> toRemove = this.invaders
//                    .stream()
//                    .sorted((a,b) -> {
//                        int byDistance = Integer.compare(a.getDistance(), b.getDistance());
//
//                        if (byDistance == 0){
//                            return Integer.compare(b.getDamage(), a.getDamage());
//                        } else {
//                            return byDistance;
//                        }
//                    })
//                    .limit(n)
//                    .collect(Collectors.toList());
//
//            this.invaders.removeAll(toRemove);
//        }


        int removedCount = 0;
        for (Map.Entry<Integer, List<Invader>> pair : invadersByDistance.entrySet()) {

            pair.getValue().sort(Comparator.comparingInt(Invader::getDamage));

            for (int i = pair.getValue().size() - 1; i >= 0; i--) {

                this.invaders.remove(pair.getValue().remove(i));
                removedCount++;
                if (removedCount == n) {
                    return;
                }
            }
        }
    }

    public Iterable<Invader> invaders() {
//        List<Invader> result = this.invaders
//                .stream()
//                .sorted((a,b) -> {
//                    int byDistance = Integer.compare(a.getDistance(), b.getDistance());
//
//                    if (byDistance == 0){
//                        return Integer.compare(b.getDamage(), a.getDamage());
//                    } else {
//                        return byDistance;
//                    }
//                })
//                .collect(Collectors.toList());
//
//        return result;

        return this.invaders;
    }
}
