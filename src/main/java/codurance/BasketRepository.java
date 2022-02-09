package codurance;

public interface BasketRepository {
    void add(Basket basket);
    Basket basketFor(UserId userId);
}
