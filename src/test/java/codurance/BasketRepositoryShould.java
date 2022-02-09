package codurance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

        Assertions.assertEquals(basket, baskets.get(userId));
    }

    @Test
    void add_multiple_items_to_one_basket() {
        Map<UserId, Basket> baskets = new HashMap<>();
        var basketRepository = new InMemoryBasketRepository(baskets);
        var product = new Product("The Hobbit", 5, new ProductId(10001));
        UserId userId = new UserId();
        basketRepository.add(userId, product, 2);
        basketRepository.add(userId, product, 3);

        Assertions.assertEquals(1, baskets.entrySet().size());
    }

    @Test
    void update_existing_basket() {
        Map<UserId, Basket> baskets = new HashMap<>();
        Basket basket = mock(Basket.class);

        UserId userId = new UserId();
        baskets.put(userId, basket);

        var basketRepository = new InMemoryBasketRepository(baskets);
        ProductId productId = new ProductId(10001);
        var product = new Product("The Hobbit", 5, productId);

        basketRepository.add(userId, product, 3);

        verify(basket).addProduct(product, 3);
    }

    @Test
    void create_one_basket_per_user() {
        Map<UserId, Basket> baskets = new HashMap<>();
        var basketRepository = new InMemoryBasketRepository(baskets);

        ProductId productId = new ProductId(10001);
        var product = new Product("The Hobbit", 5, productId);

        UserId user1 = new UserId();
        UserId user2 = new UserId();

        basketRepository.add(user1, product, 1);
        basketRepository.add(user2, product, 1);

        Assertions.assertEquals(2, baskets.entrySet().size());
    }
}
