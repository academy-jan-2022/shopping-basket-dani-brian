package codurance;

public interface ProductRepository {
    void add(UserId userId, ProductId productId, int quantity);
}
