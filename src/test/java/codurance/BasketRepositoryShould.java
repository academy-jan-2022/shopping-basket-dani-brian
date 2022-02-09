package codurance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BasketRepositoryShould {

    @Test
    void add_basket_to_map() {
        Map<UserId, Basket> baskets = new HashMap<>();
        var basketRepository = new InMemoryBasketRepository(baskets);
        var product = new Product("The Hobbit", 5, new ProductId(10001));
        UserId userId = new UserId();
        basketRepository.add(userId, product, 2);

        Assertions.assertEquals(1, baskets.entrySet().size());
    }

    @Test
    void should_add_correct_basket() {
        Map<UserId, Basket> baskets = new HashMap<>();
        var basketRepository = new InMemoryBasketRepository(baskets);
        var product = new Product("The Hobbit", 5, new ProductId(10001));
        var item = new BasketItem(product, 2);
        UserId userId = new UserId();
        Basket basket = new Basket(userId, List.of(item));

        basketRepository.add(userId, product, 2);

        Assertions.assertTrue(basket.equals(baskets.get(userId)));
    }
}
