package codurance;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AcceptanceTest {

    @Test
    void should_add_items_to_shopping_basket() {
        var shoppingBasketService = new ShoppingBasketService(new InMemoryProductRepository(), new InMemoryBasketRepository(new HashMap<>()));

        UserId user = new UserId();

        ProductId hobbitProduct = new ProductId(10002);
        shoppingBasketService.addItem(user, hobbitProduct, 2);


        ProductId breakingBadProduct = new ProductId(20110);
        shoppingBasketService.addItem(user, hobbitProduct, 5);


        Basket basket = shoppingBasketService.basketFor(user);
        assertEquals("2022-02-14", basket.getDate());

        assertEquals(2, basket.getQuantity(hobbitProduct));
        assertEquals(5, basket.getQuantity(breakingBadProduct));
        assertEquals(45, basket.getTotal());
    }
}
