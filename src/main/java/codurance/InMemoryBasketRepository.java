package codurance;

import java.util.List;
import java.util.Map;

public class InMemoryBasketRepository implements BasketRepository {
    private final Map<UserId, Basket> baskets;

    public InMemoryBasketRepository(Map<UserId, Basket> baskets) {
        this.baskets = baskets;
    }

    @Override
    public void add(UserId userId, Product product, int quantity) {
        if (baskets.containsKey(userId)) {
            Basket oldBasket = baskets.get(userId);
            oldBasket.addProduct(product, quantity);
        } else {
            BasketItem basketItem = new BasketItem(product, quantity);
            List<BasketItem> items = List.of(basketItem);
            baskets.put(userId, new Basket(userId, items));
        }
    }

    @Override
    public Basket basketFor(UserId userId) {
        throw new UnsupportedOperationException();
    }
}
