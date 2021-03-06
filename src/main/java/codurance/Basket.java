package codurance;

import java.util.*;


public class Basket {
    private final String currentTime;
    private UserId userId;
    private ArrayList<BasketItem> basketItems;

    public Basket(UserId userId, ArrayList<BasketItem> basketItems, String currentTime) {
        this.userId = userId;
        this.basketItems = basketItems;
        this.currentTime = currentTime;
    }

    public String getDate() {
        return currentTime;
    }

    public int getQuantity(ProductId productId) {
        return getByProductId(productId).quantity();
    }

    public UserId getUserId() {
        return userId;
    }

    private BasketItem getByProductId(ProductId productId) {
        return basketItems.stream()
            .filter(basketItem -> Objects.equals(basketItem.product().id(), productId))
            .toList()
            .get(0);
    }

    public int getTotal() {
        return basketItems.stream()
            .map(basketItem -> basketItem.quantity() * basketItem.product().price())
            .reduce(0, Integer::sum);
    }

    public void update(Basket newBasket) {
        var newBasketItem = newBasket.basketItems.get(0);

        int indexOfItem = basketItems.indexOf(newBasketItem);

        if (indexOfItem != -1) {
            BasketItem previousBasketItem = basketItems.get(indexOfItem);
            basketItems.set(
                indexOfItem,
                new BasketItem(newBasketItem.product(), newBasketItem.quantity() + previousBasketItem.quantity()));
        } else {
            basketItems.add(newBasketItem);
        }
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
