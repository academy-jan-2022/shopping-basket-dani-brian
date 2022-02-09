package codurance;

public class ShoppingBasketService {
    private BasketRepository basketRepository;

    public ShoppingBasketService(ProductRepository inMemoryProductRepository, BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public void addItem(UserId userId, ProductId productId, int quantity) {
        basketRepository.add(userId, productId, quantity);
    }

    public Basket basketFor(UserId user) {
        return null;
    }
}
