package codurance;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


class ShoppingBasketServiceShould {

    @Test
    void add_item_to_basket() {
        var productRepository = mock(ProductRepository.class);
        var basketRepository = mock(BasketRepository.class);

        ShoppingBasketService shoppingBasketService = new ShoppingBasketService(productRepository, basketRepository);

        UserId userId = new UserId();
        ProductId productId = new ProductId(10001);
        shoppingBasketService.addItem(userId, productId, 1);

        var product = productRepository.getById(productId);
        when(productRepository.getById(productId)).thenReturn(new Product("The Hobbit", 5, new ProductId(10001)));
        verify(basketRepository).add(userId, product, 1);
    }

    @Test
    void return_basket_for_specific_user() {
        var productRepository = mock(ProductRepository.class);
        var basketRepository = mock(BasketRepository.class);

        ShoppingBasketService shoppingBasketService = new ShoppingBasketService(productRepository, basketRepository);
        UserId userId = new UserId();
        shoppingBasketService.basketFor(userId);
        verify(basketRepository).basketFor(userId);
    }
}
