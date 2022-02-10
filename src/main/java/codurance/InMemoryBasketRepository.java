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

        if (baskets.containsKey(userId)) {
            Basket oldBasket = baskets.get(userId);
            var basketItem = new BasketItem(product, quantity);
            oldBasket.addProduct(product,quantity);
            printItemAdded(userId, basketItem);
        } else {
            BasketItem basketItem = new BasketItem(product, quantity);
            Basket newBasket = new Basket(now, new HashMap<>());
            newBasket.addProduct(product, quantity);
            baskets.put(userId, newBasket);

            printBasketCreated(userId);
            printItemAdded(userId, basketItem);
        }
    }

    private void printBasketCreated(UserId userId) {
        logger.print("[BASKET CREATED]: Created[%s], User[%s]"
            .formatted(timeProvider.now(), userId.id()));
    }

    private void printItemAdded(UserId userId, BasketItem basketItem) {
        Product product = basketItem.product();
        logger.print("[ITEM ADDED TO SHOPPING CART]: Added[%s], User[%s], Product[%s], Quantity[%s], Price[<£%s.00>]"
            .formatted(timeProvider.now(), userId.id(), product.title(), basketItem.quantity(), product.price()));
    }

    @Override
    public Basket basketFor(UserId userId) {
        return baskets.get(userId);
    }
}
