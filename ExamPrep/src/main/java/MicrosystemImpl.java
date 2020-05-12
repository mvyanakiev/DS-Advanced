import java.util.*;
import java.util.stream.Collectors;

public class MicrosystemImpl implements Microsystem {
    private Map<Integer, Computer> computers;
    private TreeMap<Brand, SortedSet<Computer>> computersByBrand;
    private TreeMap<Double, SortedSet<Computer>> computersByPrice;
    private TreeMap<Double, SortedSet<Computer>> computersByScreenSize;
    private TreeMap<String, SortedSet<Computer>> computersByColor;


    public MicrosystemImpl() {
        this.computers = new HashMap<>();
        this.computersByBrand = new TreeMap<>();
        this.computersByPrice = new TreeMap<>();
        this.computersByScreenSize = new TreeMap<>();
        this.computersByColor = new TreeMap<>();
    }

    @Override
    public void createComputer(Computer computer) {

        if (this.computers.containsKey(computer.getNumber())) {
            throw new IllegalArgumentException();
        }

        this.computers.put(computer.getNumber(), computer);

        this.computersByBrand.putIfAbsent(computer.getBrand(), new TreeSet<>(Computer::compareTo));
        this.computersByBrand.get(computer.getBrand()).add(computer);

        this.computersByPrice.putIfAbsent(computer.getPrice(), new TreeSet<>(Computer::compareTo));
        this.computersByPrice.get(computer.getPrice()).add(computer);

        this.computersByScreenSize.putIfAbsent(computer.getScreenSize(), new TreeSet<>(Computer::compareTo));
        this.computersByScreenSize.get(computer.getScreenSize()).add(computer);

        this.computersByColor.putIfAbsent(computer.getColor(), new TreeSet<>(Computer::compareTo));
        this.computersByColor.get(computer.getColor()).add(computer);
    }

    @Override
    public boolean contains(int number) {
        return this.computers.containsKey(number);
    }

    @Override
    public int count() {
        return this.computers.size();
    }

    @Override
    public Computer getComputer(int number) {

        if (!this.computers.containsKey(number)) {
            throw new IllegalArgumentException();
        }

        return this.computers.get(number);
    }

    @Override
    public void remove(int number) {
        if (!this.computers.containsKey(number)) {
            throw new IllegalArgumentException();
        }

        Computer toRemove = this.computers.remove(number);

        if (toRemove != null) {
            this.computersByBrand.get(toRemove.getBrand()).remove(toRemove);
            this.computersByPrice.get(toRemove.getPrice()).remove(toRemove);
            this.computersByScreenSize.get(toRemove.getScreenSize()).remove(toRemove);
            this.computersByColor.get(toRemove.getColor()).remove(toRemove);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void removeWithBrand(Brand brand) {
        if (!this.computersByBrand.containsKey(brand) || this.computersByBrand.get(brand).size() == 0) {
            throw new IllegalArgumentException();
        }

        SortedSet<Computer> toRemove = this.computersByBrand.remove(brand);

        for (Computer computer : toRemove) {
            this.computers.remove(computer.getNumber());
            this.computersByPrice.get(computer.getPrice()).remove(computer);
            this.computersByScreenSize.get(computer.getScreenSize()).remove(computer);
            this.computersByColor.get(computer.getColor()).remove(computer);
        }
    }

    @Override
    public void upgradeRam(int ram, int number) {
        if (!this.computers.containsKey(number)) {
            throw new IllegalArgumentException();
        }
        Computer computer = this.computers.get(number);

        if (computer.getRAM() < ram) {
            computer.setRAM(ram);
            this.computers.put(number, computer);
        }
    }

    @Override
    public Iterable<Computer> getAllFromBrand(Brand brand) {

        Iterable<Computer> computersSortedByBrand = new ArrayList<>();

        if (this.computersByBrand.containsKey(brand)) {
            computersSortedByBrand = this.computersByBrand.get(brand)
                    .stream()
                    .sorted(Comparator.comparingDouble(Computer::getPrice)
                            .reversed())
                    .collect(Collectors.toList());
        }
        return computersSortedByBrand;
    }

    @Override
    public Iterable<Computer> getAllWithScreenSize(double screenSize) {

        Iterable<Computer> computersSortedByScreenSize = new ArrayList<>();

        if (this.computersByScreenSize.containsKey(screenSize)){
            computersSortedByScreenSize = this.computersByScreenSize.get(screenSize);
        }

        return computersSortedByScreenSize;
    }

    @Override
    public Iterable<Computer> getAllWithColor(String color) {

        Iterable<Computer> computersSortedByColor = new ArrayList<>();

        if(this.computersByColor.containsKey(color)){
            computersSortedByColor = this.computersByColor.get(color);
        }

        return computersSortedByColor;
    }

    @Override
    public Iterable<Computer> getInRangePrice(double minPrice, double maxPrice) {

        List<Computer> sortedComputers = new ArrayList<>();

        Collection<SortedSet<Computer>> sets = this.computersByPrice
                .subMap(minPrice, true, maxPrice, true).values();

        if (sets.size() > 0) {
            for (SortedSet<Computer> set : sets) {
                sortedComputers.addAll(set);
            }
            sortedComputers.sort(Comparator.comparingDouble(Computer::getPrice).reversed());
        }
        return sortedComputers;
    }
}