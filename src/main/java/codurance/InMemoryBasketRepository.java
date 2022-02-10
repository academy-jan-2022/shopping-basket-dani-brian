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
        String now = timeProvider.now();

        if (baskets.containsKey(userId)) {
            Basket oldBasket = baskets.get(userId);
            logger.print("[ITEM ADDED TO SHOPPING CART]: Added[%s], User[%s], Product[%s], Quantity[%s], Price[<£%s.00>]"
                .formatted(now, userId.id(), product.title(), quantity, product.price()));
            oldBasket.addProduct(product, quantity);
        } else {
            BasketItem basketItem = new BasketItem(product, quantity);
            List<BasketItem> items = new ArrayList<>(List.of(basketItem));
            logger.print("[BASKET CREATED]: Created[%s], User[%s]"
                .formatted(now, userId.id()));


            logger.print("[ITEM ADDED TO SHOPPING CART]: Added[%s], User[%s], Product[%s], Quantity[%s], Price[<£%s.00>]"
                .formatted(now, userId.id(), product.title(), quantity, product.price()));

            baskets.put(userId, new Basket(userId, items, now));

        }
    }

    @Override
    public Basket basketFor(UserId userId) {
        return baskets.get(userId);
    }
}
