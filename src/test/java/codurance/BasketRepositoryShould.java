package codurance;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BasketRepositoryShould {

    @Test
    void add_basket_to_map() {
        Map<UserId, Basket> baskets = new HashMap<>();
        var basketRepository = new InMemoryBasketRepository(baskets, new TimeProvider());
        var product = new Product("The Hobbit", 5, new ProductId(10001));
        UserId userId = new UserId();
        basketRepository.add(userId, product, 2);

        assertEquals(1, baskets.entrySet().size());
    }

    @Test
    void should_add_correct_basket() {
        Map<UserId, Basket> baskets = new HashMap<>();
        var basketRepository = new InMemoryBasketRepository(baskets, new TimeProvider());
        var product = new Product("The Hobbit", 5, new ProductId(10001));
        var item = new BasketItem(product, 2);
        UserId userId = new UserId();
        Basket basket = new Basket(userId, List.of(item), "");

        basketRepository.add(userId, product, 2);

        assertEquals(basket, baskets.get(userId));
    }

    @Test
    void add_multiple_items_to_one_basket() {
        Map<UserId, Basket> baskets = new HashMap<>();
        var basketRepository = new InMemoryBasketRepository(baskets, new TimeProvider());
        var product = new Product("The Hobbit", 5, new ProductId(10001));
        UserId userId = new UserId();
        basketRepository.add(userId, product, 2);
        basketRepository.add(userId, product, 3);

        assertEquals(1, baskets.entrySet().size());
    }

    @Test
    void update_existing_basket() {
        Map<UserId, Basket> baskets = new HashMap<>();
        Basket basket = mock(Basket.class);

        UserId userId = new UserId();
        baskets.put(userId, basket);

        var basketRepository = new InMemoryBasketRepository(baskets, new TimeProvider());
        ProductId productId = new ProductId(10001);
        var product = new Product("The Hobbit", 5, productId);

        basketRepository.add(userId, product, 3);

        verify(basket).addProduct(product, 3);
    }

    @Test
    void create_one_basket_per_user() {
        Map<UserId, Basket> baskets = new HashMap<>();
        var basketRepository = new InMemoryBasketRepository(baskets, new TimeProvider());

        ProductId productId = new ProductId(10001);
        var product = new Product("The Hobbit", 5, productId);

        UserId user1 = new UserId();
        UserId user2 = new UserId();

        basketRepository.add(user1, product, 1);
        basketRepository.add(user2, product, 1);

        assertEquals(2, baskets.entrySet().size());
    }

    @Test
    void get_basket_for_current_user() {
        Map<UserId, Basket> baskets = new HashMap<>();
        var basketRepository = new InMemoryBasketRepository(baskets, new TimeProvider());

        UserId userId = new UserId();
        var basket = mock(Basket.class);
        baskets.put(userId, basket);

        assertEquals(basket, basketRepository.basketFor(userId));
    }

    @Test
    void create_basket_at_current_time() {
        Map<UserId, Basket> baskets = new HashMap<>();
        TimeProvider timeProviderMock = mock(TimeProvider.class);
        var basketRepository = new InMemoryBasketRepository(baskets, timeProviderMock);
        var currentTime = "2022-02-14";
        UserId userId = new UserId();
        Product product = new Product("", 0, new ProductId(1));
        var basketItem = new BasketItem(product, 1);
        List<BasketItem> basketItems = List.of(basketItem);
        basketRepository.add( userId, product, 1);

        var basket = new Basket(userId, basketItems, currentTime);

        assertEquals(basket, basketRepository.basketFor(userId));
    }
}
