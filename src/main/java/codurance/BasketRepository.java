package codurance;

public interface BasketRepository {
    void add(UserId userId, ProductId productId, int quantity);
}
