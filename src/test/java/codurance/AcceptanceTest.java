package codurance;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AcceptanceTest {

    public static final String CURRENT_DATE = "2022-02-14";

    @Test
    void should_add_items_to_shopping_basket() {
        var output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        TimeProvider timeProvider = mock(TimeProvider.class);
        when(timeProvider.now()).thenReturn(CURRENT_DATE);
        InMemoryBasketRepository basketRepository = new InMemoryBasketRepository(new HashMap<>(), timeProvider, new Logger());
        var shoppingBasketService = new ShoppingBasketService(new InMemoryProductRepository(), basketRepository);
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

        var expected1 = "[BASKET CREATED]: Created[" + CURRENT_DATE + "], User[1]";
        var expected2 = "[ITEM ADDED TO SHOPPING CART]: Added[" + CURRENT_DATE + "], User[1], Product[The Hobbit], Quantity[2], Price[<£5.00>]";
        var expected3 = "[ITEM ADDED TO SHOPPING CART]: Added[" + CURRENT_DATE + "], User[1], Product[Breaking Bad], Quantity[5], Price[<£7.00>]";

        var finalOutput = output.toString();
        assertTrue(finalOutput.contains(expected1), "expected: " + expected1 + "actual: " + finalOutput);
        assertTrue(finalOutput.contains(expected2), "expected: " + expected2 + "actual: " + finalOutput);
        assertTrue(finalOutput.contains(expected3), "expected: " + expected3 + "actual: " + finalOutput);
    }

}
