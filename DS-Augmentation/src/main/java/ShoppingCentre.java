import java.util.*;
import java.util.stream.Collectors;

public class ShoppingCentre {
    private Set<Product> products;
    private Map<String, SortedSet<Product>> productsByProducer;
    private Map<String, SortedSet<Product>> productsByName;
    private TreeMap<Double, List<Product>> productsByPrice;


    public ShoppingCentre() {
        this.products = new HashSet<>();
        this.productsByProducer = new HashMap<>();
        this.productsByName = new HashMap<>();
        this.productsByPrice = new TreeMap<>();
    }

    public String addProduct(String name, double price, String producer) {

        Product product = new Product(name, price, producer);

        if (this.products.add(product)) {
            this.productsByProducer.putIfAbsent(producer, new TreeSet<>(Comparator.comparingDouble(Product::getPrice)));
            this.productsByProducer.get(producer).add(product);

            this.productsByName.putIfAbsent(name, new TreeSet<>(Comparator.comparingDouble(Product::getPrice)));
            this.productsByName.get(name).add(product);

            this.productsByPrice.putIfAbsent(price, new ArrayList<>());
            this.productsByPrice.get(price).add(product);

            return "Product added" + System.lineSeparator();
        } else {
            return "";
        }
    }

    //todo delete from productsByPrice

    public String delete(String name, String producer) {
        Set<Product> removeByName = this.productsByName.remove(name);
        Set<Product> removeByProducer = this.productsByProducer.remove(producer);

        int size = this.products.size();

        if (removeByName != null && removeByProducer != null) {
            this.products.removeAll(removeByName);
            this.products.removeAll(removeByProducer);
        }

        size = size - this.products.size();

        if (size > 0) {
            return size + " products deleted" + System.lineSeparator();
        } else {
            return "No products found" + System.lineSeparator();
        }
    }

    public String delete(String producer) {
        Set<Product> productsToRemove = this.productsByProducer.remove(producer);
        int size = productsToRemove.size();

        if (size > 0) {
            this.products.removeAll(productsToRemove);

            for (Product product : productsToRemove) {
                Set<Product> productsByNameToRemove = this.productsByName.get(product.getName());
                productsByNameToRemove.removeAll(productsToRemove);
            }

            return size + " products deleted" + System.lineSeparator();
        }
        return "No products found" + System.lineSeparator();
    }

    public String findProductsByName(String name) {
        Set<Product> productsByNameSet = this.productsByName.get(name);
        return generateProductsList(productsByNameSet);
    }

    public String findProductsByProducer(String producer) {
        Set<Product> productsByProducerSet = this.productsByProducer.get(producer);
        return generateProductsList(productsByProducerSet);
    }

    public String findProductsByPriceRange(double priceFrom, double priceTo) {

//        double start = Double.valueOf(priceFrom);
//        double end = Double.valueOf(priceTo);


//        SortedMap<Double, SortedSet<Product>> doubleSortedSetSortedMap = this.productsByPrice
//                .subMap(priceFrom, true, priceTo, true);

        SortedMap<Double, List<Product>> doubleSortedSetSortedMap = this.productsByPrice
                .subMap(priceFrom, true, priceTo, true);


        StringBuilder sb = new StringBuilder();

        if (doubleSortedSetSortedMap != null) {

            for (List<Product> set : doubleSortedSetSortedMap.values()) {
//                generateProductsList(set);

                for (Product product : set) {
                    sb.append(product.toString());
                    sb.append(System.lineSeparator());
                }
            }
        } else {
            sb.append("No products found").append(System.lineSeparator());
        }
        return sb.toString();
    }

    private String generateProductsList(Set<Product> set) {
        StringBuilder sb = new StringBuilder();

        if (set != null) {
            for (Product product : set) {
                sb.append(product.toString()).append(System.lineSeparator());
            }
        } else {
            sb.append("No products found").append(System.lineSeparator());
        }
        return sb.toString();
    }
}
