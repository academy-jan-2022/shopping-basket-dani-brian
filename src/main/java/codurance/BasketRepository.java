package codurance;

public interface BasketRepository {
    void add(UserId userId, Product productId, int quantity);
    void add(Basket basket);

    Basket basketFor(UserId userId);
}
