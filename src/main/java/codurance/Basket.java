package codurance;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

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
        var item = getByProduct(productId);

        if (item.isPresent()) {
            var basketItem = item.get();
            return basketItem.quantity();
        } else {
            return 0;
        }
    }

    public UserId getUserId() {
        return userId;
    }

    private Optional<BasketItem> getByProduct(ProductId productId) {
        return basketItems.stream()
            .filter(basketItem -> Objects.equals(basketItem.product().id(), productId))
            .findFirst();
    }

    public int getTotal() {
        return basketItems.stream()
            .map(basketItem -> basketItem.quantity() * basketItem.product().price())
            .reduce(0, Integer::sum);
    }

    public void addProduct(Product product, int quantity) {
        Optional<BasketItem> productExisting = getByProduct(product.id());

        productExisting.ifPresent(
            item -> basketItems.replaceAll(
                basketItem -> replacleIfMatch(quantity, item, basketItem)));

        basketItems.add(new BasketItem(product, quantity));
    }

    private BasketItem replacleIfMatch(int quantity, BasketItem item, BasketItem basketItem) {
        return basketItem.equals(item)
            ? new BasketItem(basketItem.product(), basketItem.quantity() + quantity)
            : basketItem;
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

    public void update(Basket newBasket) {
        throw new UnsupportedOperationException();
    }
}
