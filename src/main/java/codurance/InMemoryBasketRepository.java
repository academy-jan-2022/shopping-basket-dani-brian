package codurance;

import java.util.Map;

public class InMemoryBasketRepository implements BasketRepository {
    private final Map<UserId, Basket> baskets;

    public InMemoryBasketRepository(Map<UserId, Basket> baskets) {
        this.baskets = baskets;
    }

    @Override
    public void add(UserId userId, Product productId, int quantity) {
        baskets.put(userId,new Basket(null,null));
    }

    @Override
    public Basket basketFor(UserId userId) {
        throw new UnsupportedOperationException();
    }
}
