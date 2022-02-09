package codurance;

import java.util.List;
import java.util.Objects;

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
            .filter(basketItem -> basketItem.product()
                .id() == productId)
            .findFirst();

        if (item.isPresent()) {
            var basketItem = item.get();
            return basketItem.quantity();
        } else {
            return 0;
        }
    }

    public int getTotal() {
        throw new UnsupportedOperationException();
    }

    public void addProduct(Product product, int quantity) {

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
