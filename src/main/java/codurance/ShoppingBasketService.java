package codurance;

public class ShoppingBasketService {
    private final ProductRepository productRepository;
    private BasketRepository basketRepository;

    public ShoppingBasketService(ProductRepository inMemoryProductRepository, BasketRepository basketRepository, TimeProvider timeProvider) {
        this.basketRepository = basketRepository;
        this.productRepository = inMemoryProductRepository;
    }

    public void addItem(UserId userId, ProductId productId, int quantity) {
        var product = productRepository.getById(productId);
        basketRepository.add(userId, product, quantity);
    }

    public Basket basketFor(UserId user) {
        return basketRepository.basketFor(user);
    }
}
