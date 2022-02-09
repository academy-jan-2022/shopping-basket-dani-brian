package codurance;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;


class ShoppingBasketServiceShould {

    public static final String CURRENT_TIME = "2022-01-01";

    @Test
    void add_item_to_basket() {
        var productRepository = mock(ProductRepository.class);
        var basketRepository = mock(BasketRepository.class);
        var timeProvider = mock(TimeProvider.class);

        ShoppingBasketService shoppingBasketService = new ShoppingBasketService(productRepository, basketRepository, timeProvider);

        UserId userId = new UserId();
        ProductId productId = new ProductId(10001);
        shoppingBasketService.addItem(userId, productId, 1);

        var product = productRepository.getById(productId);
        when(productRepository.getById(productId)).thenReturn(new Product("The Hobbit", 5, new ProductId(10001)));

        when(timeProvider.now()).thenReturn(CURRENT_TIME);
        verify(basketRepository).add(new Basket(userId, List.of(new BasketItem(product, 1)), CURRENT_TIME));
    }

    @Test
    void return_basket_for_specific_user() {
        var productRepository = mock(ProductRepository.class);
        var basketRepository = mock(BasketRepository.class);
        var timeProvider = mock(TimeProvider.class);

        ShoppingBasketService shoppingBasketService = new ShoppingBasketService(productRepository, basketRepository, timeProvider);
        UserId userId = new UserId();
        shoppingBasketService.basketFor(userId);
        verify(basketRepository).basketFor(userId);
    }
}
