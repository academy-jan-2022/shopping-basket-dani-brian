package codurance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class BasketShould {
    @Test
    void return_current_date() {
        var expected = "2022-02-14";
        var basket = new Basket(expected, new HashMap<>());
        Assertions.assertEquals(expected, basket.getDate());
    }

    @Test
    void get_quantity() {
        int expected = 5;
        Product product = new Product("title", 5, new ProductId(100012));
        var basket = new Basket( "",new HashMap<>(Map.of(product,5)));
        Assertions.assertEquals(expected, basket.getQuantity(new ProductId(100012)));
    }

    @Test
    void update_existing_product_in_basket() {
        var product = new Product("title", 5, new ProductId(1));
        HashMap<Product, Integer> items = new HashMap<>();
        var basket = new Basket("", items);
        basket.addProduct(product, 1);
        basket.addProduct(product, 1);
        Assertions.assertEquals(2, items.get(product));
    }

    @Test
    void add_second_item_to_existing_basket() {
        var product1 = new Product("title", 5, new ProductId(1));
        var product2 = new Product("title2", 6, new ProductId(2));
        HashMap<Product, Integer> items = new HashMap<>(Map.of(product1, 1));
        var basket = new Basket("", items);
        basket.addProduct(product2,1);
        Assertions.assertTrue(items.containsKey(product2));
    }

    @Test
    void get_total_amount() {
        Product product1 = new Product("1", 5, new ProductId(100012));
        Product product2 = new Product("2", 7, new ProductId(100013));
        var basket = new Basket("", new HashMap<>(Map.of(product1, 10, product2, 10)));
        Assertions.assertEquals(120, basket.getTotal());
    }
}
