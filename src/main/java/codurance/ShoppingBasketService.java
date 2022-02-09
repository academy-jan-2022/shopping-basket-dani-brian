package codurance;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketService {
    private final ProductRepository productRepository;
    private final TimeProvider timeProvider;
    private BasketRepository basketRepository;

    public ShoppingBasketService(ProductRepository inMemoryProductRepository, BasketRepository basketRepository, TimeProvider timeProvider) {
        this.basketRepository = basketRepository;
        this.productRepository = inMemoryProductRepository;
        this.timeProvider = timeProvider;
    }

    public void addItem(UserId userId, ProductId productId, int quantity) {
        var product = productRepository.getById(productId);
        BasketItem basketItem = new BasketItem(product, quantity);
        var basket = new Basket(userId, new ArrayList<>(List.of(basketItem)), timeProvider.now());
        basketRepository.add(basket);
    }

    public Basket basketFor(UserId user) {
        return basketRepository.basketFor(user);
    }
}
