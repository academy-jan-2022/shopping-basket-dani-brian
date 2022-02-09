package codurance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BasketShould {
    @Test
    void return_current_date() {
        var expected = "2022-02-14";
        var basket = new Basket(new UserId(), new ArrayList<>(), expected);

        Assertions.assertEquals(expected, basket.getDate());
    }

    @Test
    void get_quantity() {
        int expected = 5;

        BasketItem basketItem = new BasketItem(new Product("title", 5, new ProductId(100012)), expected);
        var basket = new Basket(new UserId(), new ArrayList<>(List.of(basketItem)), "");

        Assertions.assertEquals(expected, basket.getQuantity(new ProductId(100012)));
    }

    @Test
    void add_second_item_to_existing_basket() {
        ProductId id = new ProductId(1);
        ProductId id2 = new ProductId(2);
        var product1 = new Product("title", 5, id);
        var product2 = new Product("title2", 6, id2);
        BasketItem basketItem1 = new BasketItem(product1, 1);
        BasketItem basketItem2 = new BasketItem(product2, 1);
        var basket = new Basket(new UserId(), new ArrayList<>(List.of(basketItem1)), "");
        basket.update(new Basket(new UserId(), new ArrayList<>(List.of(basketItem2)),""));

        Assertions.assertEquals(1, basket.getQuantity(id2));
    }

    @Test
    void update_quantity_of_existing_product_in_basket() {
        ProductId id = new ProductId(1);
        var product = new Product("title", 5, id);
        BasketItem basketItem = new BasketItem(product, 1);
        var basket = new Basket(new UserId(), new ArrayList<>(List.of(basketItem)), "");
        var basket2 = new Basket(new UserId(), new ArrayList<>(List.of(basketItem)), "");
        basket.update(basket2);

        Assertions.assertEquals(2, basket.getQuantity(id));
    }

    @Test
    void get_total_amount() {
        BasketItem basketItem1 = new BasketItem(new Product("1", 5, new ProductId(100012)), 10);
        BasketItem basketItem2 = new BasketItem(new Product("2", 7, new ProductId(13)), 10);
        var basket = new Basket(new UserId(), new ArrayList<>(List.of(basketItem1, basketItem2)), "");

        Assertions.assertEquals(120, basket.getTotal());


    }
}
