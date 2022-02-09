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

    @Test
    void add_second_item_to_existing_basket() {
        ProductId id = new ProductId(1);
        ProductId id2 = new ProductId(2);
        var product1 = new Product("title", 5, id);
        var product2 = new Product("title2", 6, id2);
        BasketItem basketItem = new BasketItem(product1, 1);
        var basket = new Basket(new UserId(),List.of(basketItem),"");
        basket.addProduct(product2, 1);

        Assertions.assertEquals(1, basket.getQuantity(id2));
    }
}
