package codurance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryBasketRepository implements BasketRepository {
    private final Map<UserId, Basket> baskets;
    private final TimeProvider timeProvider;
    private final Logger logger;

    public InMemoryBasketRepository(Map<UserId, Basket> baskets, TimeProvider timeProvider, Logger logger) {
        this.baskets = baskets;
        this.timeProvider = timeProvider;
        this.logger = logger;
    }

    @Override
    public void add(UserId userId, Product product, int quantity) {
        if (baskets.containsKey(userId)) {
            Basket oldBasket = baskets.get(userId);
            oldBasket.addProduct(product, quantity);
        } else {
            String now = timeProvider.now();
            BasketItem basketItem = new BasketItem(product, quantity);
            List<BasketItem> items = new ArrayList<>(List.of(basketItem));
            logger.print("[BASKET CREATED]: Created[" + now + "], User["
                + userId.id() + "]");
            baskets.put(userId, new Basket(userId, items, now));
        }
    }

    @Override
    public Basket basketFor(UserId userId) {
        return baskets.get(userId);
    }
}
