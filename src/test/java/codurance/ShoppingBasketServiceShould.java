package codurance;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class ShoppingBasketServiceShould {

    @Test
    void add_item_to_basket() {
        var productRepository = mock(ProductRepository.class);
        var basketRepository = mock(BasketRepository.class);

        ShoppingBasketService shoppingBasketService = new ShoppingBasketService(productRepository, basketRepository);

        UserId userId = new UserId();
        ProductId productId = new ProductId(10001);
        shoppingBasketService.addItem(userId, productId, 1);

        verify(productRepository).add(userId, productId, 1);
    }
}
