package codurance;

public interface BasketRepository {
    void add(UserId userId, Product productId, int quantity);

    Basket basketFor(UserId userId);
}
