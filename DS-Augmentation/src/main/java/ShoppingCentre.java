import java.util.*;
import java.util.stream.Collectors;

public class ShoppingCentre {
    private List<Product> products;
    private Map<String, List<Product>> productsByProducer;

    public ShoppingCentre() {
        this.productsByProducer = new HashMap<>();
        this.products = new ArrayList<>();
    }

    public String addProduct(String name, double price, String producer) {
        Product product = new Product(name, price, producer);
        products.add(product);

        productsByProducer.putIfAbsent(producer, new ArrayList<>());
        productsByProducer.get(producer).add(product);
        return "Product added";
    }

    public String delete(String name, String producer) {
        int x1 = products.size();
        boolean b = products.removeIf(p -> p.getName().equals(name) && p.getProducer().equals(producer));
        if (!b) {
            return "No products found";
        }
        int x = x1 - products.size();
        productsByProducer.get(producer).removeIf(p -> p.getName().equals(name));

        return x + " products deleted";
    }

    public String delete(String producer) {
        int x1 = products.size();
        products.removeIf(p -> p.getProducer().equals(producer));
        int x = x1 - products.size();
        if (x == 0) {
            return "No products found";
        }
        productsByProducer.remove(producer);
        return x + " products deleted";

    }

    public String findProductsByName(String name) {
        List<Product> collect = products.stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList());
        return getStringResult(collect);
    }

    public String findProductsByProducer(String producer) {
        return getStringResult(productsByProducer.get(producer));
    }

    public String findProductsByPriceRange(double priceFrom, double priceTo) {
        List<Product> collect = products.stream()
                .filter(prod -> prod.getPrice() >= priceFrom && prod.getPrice() <= priceTo)
                .collect(Collectors.toList());
        return getStringResult(collect);
    }

    private String getStringResult(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return "No products found";
        }

        StringBuilder sb = new StringBuilder();
        products.sort(Product::compareTo);
        products.forEach(p -> sb.append(p.toString()).append(System.lineSeparator()));
        return sb.toString().trim();
    }

}