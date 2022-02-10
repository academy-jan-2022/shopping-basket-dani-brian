package codurance;

import java.util.HashMap;
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
        String now = timeProvider.now();

        if (!baskets.containsKey(userId))
            logBasketCreated(userId);

        baskets.putIfAbsent(userId, new Basket(now, new HashMap<>()));
        Basket basket = baskets.get(userId);
        basket.addProduct(product, quantity);
        logItemAdded(userId, product, quantity);
    }

    private void logBasketCreated(UserId userId) {
        logger.print("[BASKET CREATED]: Created[%s], User[%s]"
            .formatted(timeProvider.now(), userId.id()));
    }

    private void logItemAdded(UserId userId, Product product, int quantity) {
        logger.print("[ITEM ADDED TO SHOPPING CART]: Added[%s], User[%s], Product[%s], Quantity[%s], Price[<£%s.00>]"
            .formatted(timeProvider.now(), userId.id(), product.title(), quantity, product.price()));
    }

    @Override
    public Basket basketFor(UserId userId) {
        return baskets.get(userId);
    }
}
