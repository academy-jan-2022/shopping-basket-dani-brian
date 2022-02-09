package codurance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class BasketShould {
    @Test
    void return_current_date() {
        var expected = "2022-02-14";
        var basket = new Basket(new UserId(), List.of(),expected);

        Assertions.assertEquals(expected,basket.getDate());
    }

    @Test
    void get_quantity() {
        int expected = 5;
        ProductId id = new ProductId(1);
        BasketItem basketItem = new BasketItem(new Product("title", 5, id), expected);
        var basket = new Basket(new UserId(),List.of(basketItem),"");

        Assertions.assertEquals(expected,basket.getQuantity(id));
    }
}
