package codurance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BasketRepositoryShould {

    public static final String CURRENT_DATE = "2022-02-14";
    private Map<UserId, Basket> baskets;
    private InMemoryBasketRepository basketRepository;
    private Logger loggerMock;

    @BeforeEach
    void setUp() {
        baskets = new HashMap<>();
        TimeProvider timeProviderMock = mock(TimeProvider.class);
        when(timeProviderMock.now()).thenReturn(CURRENT_DATE);

        loggerMock = mock(Logger.class);

        basketRepository = new InMemoryBasketRepository(baskets, timeProviderMock, loggerMock);
    }

    @Test
    void add_basket_to_map() {
        UserId userId = new UserId();
        basketRepository.add(userId, getHobbit(), 2);

        assertEquals(1, baskets.entrySet().size());
    }

    private Product getHobbit() {
        return new Product("The Hobbit", 5, new ProductId(10001));
    }

    @Test
    void should_add_correct_basket() {
        UserId userId = new UserId();
        Basket basket = getBasket(getHobbit(), 2);

        basketRepository.add(userId, getHobbit(), 2);

        assertEquals(basket, baskets.get(userId));
    }

    private Basket getBasket(Product product, int productQuantity) {
        var item = new BasketItem(product, productQuantity);
        return new Basket(List.of(item), CURRENT_DATE);
    }

    @Test
    void update_existing_basket() {
        Basket basket = mock(Basket.class);
        UserId userId = new UserId();
        baskets.put(userId, basket);
        var product = getHobbit();

        var basketItem = new BasketItem(product, 3);

        basketRepository.add(userId, product, 3);

        verify(basket).addProduct(basketItem);
    }

    @Test
    void create_one_basket_per_user() {
        var product = getHobbit();

        UserId user1 = new UserId();
        UserId user2 = new UserId(2);

        basketRepository.add(user1, product, 1);
        basketRepository.add(user2, product, 1);

        assertEquals(2, baskets.entrySet().size());
    }

    @Test
    void get_basket_for_current_user() {
        UserId userId = new UserId();

        var basket = mock(Basket.class);

        baskets.put(userId, basket);

        assertEquals(basket, basketRepository.basketFor(userId));
    }

    @Test
    void create_basket_at_current_time() {
        UserId userId = new UserId();

        var basket = getBasket(getHobbit(), 1);

        basketRepository.add(userId, getHobbit(), 1);

        assertEquals(basket, basketRepository.basketFor(userId));
    }

    @Test
    void log_basket_created() {
        UserId userId = new UserId();

        basketRepository.add(userId, getHobbit(), 1);

        verify(loggerMock).print("[BASKET CREATED]: Created[" + CURRENT_DATE + "], User[1]");
    }

    @Test
    void log_item_added() {
        UserId userId = new UserId();

        basketRepository.add(userId, getHobbit(), 1);

        verify(loggerMock).print(
            "[ITEM ADDED TO SHOPPING CART]: Added[" + CURRENT_DATE + "], User[1], Product[The Hobbit], Quantity[1], Price[<£5.00>]");
    }

    @Test
    void only_log_item_added_on_update() {
        UserId userId = new UserId();

        basketRepository.add(userId, getHobbit(), 1);
        basketRepository.add(userId, getHobbit(), 1);

        verify(loggerMock, times(2)).print(
            "[ITEM ADDED TO SHOPPING CART]: Added[" + CURRENT_DATE + "], User[1], Product[The Hobbit], Quantity[1], Price[<£5.00>]");
        verify(loggerMock, times(1)).print("[BASKET CREATED]: Created[" + CURRENT_DATE + "], User[1]");
    }
}
