package codurance;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Basket {
    private final String currentTime;
    private final Map<Product, Integer> items;

    public Basket(String currentTime, Map<Product, Integer> items) {
        this.currentTime = currentTime;
        this.items = items;
    }

    public String getDate() {
        return currentTime;
    }

    public int getQuantity(ProductId productId) {
        return items.entrySet().stream()
            .filter(item -> item.getKey().id().equals(productId))
            .findFirst()
            .map(Map.Entry::getValue)
            .orElse(0);
    }

    public void addProduct(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    public int getTotal() {
        return items.entrySet().stream()
            .map(item -> item.getValue() * item.getKey().price())
            .reduce(0, Integer::sum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(currentTime, basket.currentTime);
    }

    @Override
    public String toString() {
        return "Basket{" +
            "currentTime='" + currentTime + '\'' +
            '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentTime);
    }
}
