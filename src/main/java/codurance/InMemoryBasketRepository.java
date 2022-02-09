package codurance;

public class InMemoryBasketRepository implements BasketRepository {
    @Override
    public void add(UserId userId, Product productId, int quantity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Basket basketFor(UserId userId) {
        throw new UnsupportedOperationException();
    }
}
