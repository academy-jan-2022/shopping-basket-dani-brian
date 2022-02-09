package codurance;

import java.util.List;
import java.util.Map;

public class InMemoryBasketRepository implements BasketRepository {
    private final Map<UserId, Basket> baskets;
    private final TimeProvider timeProvider;

    public InMemoryBasketRepository(Map<UserId, Basket> baskets, TimeProvider timeProvider) {
        this.baskets = baskets;
        this.timeProvider = timeProvider;
    }

    @Override
    public void add(UserId userId, Product product, int quantity) {
        if (baskets.containsKey(userId)) {
            Basket oldBasket = baskets.get(userId);
            oldBasket.addProduct(product, quantity);
        } else {
            BasketItem basketItem = new BasketItem(product, quantity);
            List<BasketItem> items = List.of(basketItem);
            baskets.put(userId, new Basket(userId, items, timeProvider.now()));
        }
    }

    @Override
    public Basket basketFor(UserId userId) {
        return baskets.get(userId);
    }
}
