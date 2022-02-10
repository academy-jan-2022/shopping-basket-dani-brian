package codurance;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public class Basket {
    private final String currentTime;
    private List<BasketItem> basketItems;

    public Basket(List<BasketItem> basketItems, String currentTime) {
        this.basketItems = basketItems;
        this.currentTime = currentTime;
    }

    public String getDate() {
        return currentTime;
    }

    public int getQuantity(ProductId productId) {
        return basketItems.stream()
            .filter(basketItem -> basketItem.sameProduct(productId))
            .findFirst()
            .map(BasketItem::quantity)
            .orElse(0);

    }

    public void addProduct(BasketItem basketItem) {
        var productId = basketItem.product().id();
        var itemExists = basketItems.stream().anyMatch(item -> item.sameProduct(productId));

        if (itemExists) {
            basketItems = basketItems.stream()
                .map(item -> item.sameProduct(productId) ?
                    item.updateQuantity(basketItem)
                    : item)
                .toList();
        } else {
            basketItems.add(basketItem);
        }
    }

    public int getTotal() {
        return basketItems.stream()
            .map(basketItem -> basketItem.quantity() * basketItem.product().price())
            .reduce(0, Integer::sum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(currentTime, basket.currentTime) && Objects.equals(basketItems, basket.basketItems);
    }

    @Override
    public String toString() {
        return "Basket{" +
            "currentTime='" + currentTime + '\'' +
            ", basketItems=" + basketItems +
            '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentTime, basketItems);
    }
}
