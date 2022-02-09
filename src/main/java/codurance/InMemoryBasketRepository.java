package codurance;

import java.util.Map;

public class InMemoryBasketRepository implements BasketRepository {
    private final Map<UserId, Basket> baskets;
    private final Logger logger;

    public InMemoryBasketRepository(Map<UserId, Basket> baskets, Logger logger) {
        this.baskets = baskets;
        this.logger = logger;
    }

    @Override
    public void add(Basket basket) {
        if (baskets.containsKey(basket.getUserId())) {
            Basket oldBasket = baskets.get(basket.getUserId());
            oldBasket.update(basket);
        } else {
            logger.print("[BASKET CREATED]: Created[" + basket.getDate() + "], User[" + basket.getUserId().uuid() + "]");
            baskets.put(basket.getUserId(), basket);
        }
    }

    @Override
    public Basket basketFor(UserId userId) {
        return baskets.get(userId);
    }
}
