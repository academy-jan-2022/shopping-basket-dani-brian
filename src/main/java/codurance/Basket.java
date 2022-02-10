package codurance;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public class Basket {
    private final String currentTime;
    private UserId userId;
    private List<BasketItem> basketItems;

    public Basket(UserId userId, List<BasketItem> basketItems, String currentTime) {
        this.userId = userId;
        this.basketItems = basketItems;
        this.currentTime = currentTime;
    }

    public String getDate() {
        return currentTime;
    }

    public int getQuantity(ProductId productId) {
        var item = basketItems.stream()
            .filter(basketItem -> Objects.equals(basketItem.product().id(), productId))
            .findFirst();

        if (item.isPresent()) {
            var basketItem = item.get();
            return basketItem.quantity();
        } else {
            return 0;
        }
    }

    public void addProduct(BasketItem basketItem) {
        basketItems.add(basketItem);
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
        return Objects.equals(currentTime, basket.currentTime) && Objects.equals(userId, basket.userId) && Objects.equals(basketItems, basket.basketItems);
    }

    @Override
    public String toString() {
        return "Basket{" +
            "currentTime='" + currentTime + '\'' +
            ", userId=" + userId +
            ", basketItems=" + basketItems +
            '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentTime, userId, basketItems);
    }
}
