package codurance;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
        var shoppingBasketService = new ShoppingBasketService(
            new InMemoryProductRepository(),
            new InMemoryBasketRepository(new HashMap<>(), new Logger()),
            timeProvider);

        UserId user = new UserId();

        ProductId hobbitProduct = new ProductId(10002);
        shoppingBasketService.addItem(user, hobbitProduct, 2);

        ProductId breakingBadProduct = new ProductId(20110);
        shoppingBasketService.addItem(user, breakingBadProduct, 5);

        Basket basket = shoppingBasketService.basketFor(user);
        assertEquals(CURRENT_DATE, basket.getDate());

        assertEquals(2, basket.getQuantity(hobbitProduct));
        assertEquals(5, basket.getQuantity(breakingBadProduct));
        assertEquals(45, basket.getTotal());
    }

    @Test
    void should_print_when_basket_is_created_or_item_added_to_cart() {
        var output =  new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        TimeProvider timeProvider = mock(TimeProvider.class);
        when(timeProvider.now()).thenReturn(CURRENT_DATE);
        var shoppingBasketService = new ShoppingBasketService(
            new InMemoryProductRepository(),
            new InMemoryBasketRepository(new HashMap<>(), new Logger()),
            timeProvider);

        UserId user = new UserId();

        ProductId hobbitProduct = new ProductId(10002);
        shoppingBasketService.addItem(user, hobbitProduct, 2);

        var finalOutput = output.toString().trim();
        var expected1 = "[BASKET CREATED]: Created[" + CURRENT_DATE + "], User[]";
        var expected2 = "[ITEM ADDED TO SHOPPING CART]: Added[" + CURRENT_DATE +"], User[], Product[The Hobbit], Quantity[2], Price[<£5.00>]";

        assertEquals(expected1, finalOutput);
        assertEquals(expected2, finalOutput);
    }
}
