package codurance;

import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AcceptanceTest {

    public static final String CURRENT_DATE = "2022-02-14";

    @Test
    void should_add_items_to_shopping_basket() {
        TimeProvider timeProvider = mock(TimeProvider.class);
        when(timeProvider.now()).thenReturn(CURRENT_DATE);
        var shoppingBasketService = new ShoppingBasketService(new InMemoryProductRepository(), new InMemoryBasketRepository(new HashMap<>(), timeProvider));

        UserId user = new UserId();

        ProductId hobbitProduct = new ProductId(10002);
        shoppingBasketService.addItem(user, hobbitProduct, 2);


        ProductId breakingBadProduct = new ProductId(20110);
        shoppingBasketService.addItem(user, hobbitProduct, 5);


        Basket basket = shoppingBasketService.basketFor(user);
        assertEquals(CURRENT_DATE, basket.getDate());

        assertEquals(2, basket.getQuantity(hobbitProduct));
        assertEquals(5, basket.getQuantity(breakingBadProduct));
        assertEquals(45, basket.getTotal());
    }
}
