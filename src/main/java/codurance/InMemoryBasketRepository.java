package codurance;

import java.util.ArrayList;
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
    public void add(Basket basket) {
        if (baskets.containsKey(basket.getUserId())) {
            Basket oldBasket = baskets.get(basket.getUserId());
            oldBasket.update(basket);
        } else {
            baskets.put(basket.getUserId(), basket);
        }
    }

    @Override
    public Basket basketFor(UserId userId) {
        return baskets.get(userId);
    }
}
