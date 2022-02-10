package codurance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BasketRepositoryShould {

    public static final String CURRENT_TIME = "2022-02-14";
    private Map<UserId, Basket> baskets;
    private InMemoryBasketRepository basketRepository;
    private Logger loggerMock;

    @BeforeEach
    void setUp() {
        baskets = new HashMap<>();
        TimeProvider timeProviderMock = mock(TimeProvider.class);
        when(timeProviderMock.now()).thenReturn(CURRENT_TIME);

        loggerMock = mock(Logger.class);

        basketRepository = new InMemoryBasketRepository(baskets, timeProviderMock, loggerMock);
    }

    @Test
    void add_basket_to_map() {
        UserId userId = new UserId();
        basketRepository.add(userId, getProduct(), 2);

        assertEquals(1, baskets.entrySet().size());
    }

    private Product getProduct() {
        return new Product("The Hobbit", 5, new ProductId(10001));
    }

    @Test
    void should_add_correct_basket() {
        UserId userId = new UserId();
        Basket basket = getBasket(userId, getProduct(), 2);

        basketRepository.add(userId, getProduct(), 2);

        assertEquals(basket, baskets.get(userId));
    }

    private Basket getBasket(UserId userId, Product product, int productQuantity) {
        var item = new BasketItem(product, productQuantity);
        return new Basket(userId, List.of(item), CURRENT_TIME);
    }

    @Test
    void update_existing_basket() {
        Basket basket = mock(Basket.class);
        UserId userId = new UserId();
        baskets.put(userId, basket);
        var product = getProduct();

        basketRepository.add(userId, product, 3);

        verify(basket).addProduct(product, 3);
    }

    @Test
    void create_one_basket_per_user() {
        var product = getProduct();

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

        var basket = getBasket(userId, getProduct(), 1);

        basketRepository.add(userId, getProduct(), 1);

        assertEquals(basket, basketRepository.basketFor(userId));
    }

    @Test
    void log_basket_created() {
        UserId userId = new UserId();

        var basket = getBasket(userId, getProduct(), 1);

        basketRepository.add(userId, getProduct(), 1);

        verify(loggerMock).print("[BASKET CREATED]: Created[" + CURRENT_TIME + "], User[1]");

    }
}
