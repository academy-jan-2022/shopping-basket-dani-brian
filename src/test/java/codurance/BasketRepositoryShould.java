package codurance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BasketRepositoryShould {

    public static final String CURRENT_DATE = "2022-02-14";
    private Map<UserId, Basket> baskets;
    private InMemoryBasketRepository basketRepository;
    private Basket mockBasket;
    private Logger mockLogger;
    private UserId userId;

    @BeforeEach
    void setUp() {
        baskets = new HashMap<>();
        userId = new UserId();

        mockBasket = mock(Basket.class);
        when(mockBasket.getUserId()).thenReturn(userId);
        when(mockBasket.getDate()).thenReturn(CURRENT_DATE);
        mockLogger = mock(Logger.class);

        basketRepository = new InMemoryBasketRepository(baskets, mockLogger);
    }

    @Test
    void add_basket_to_map() {
        basketRepository.add(mockBasket);
        assertEquals(1, baskets.entrySet().size());
    }

    @Test
    void logger_is_called_when_basket_is_created() {
        basketRepository.add(mockBasket);
        verify(mockLogger).print("[BASKET CREATED]: Created[" + CURRENT_DATE + "], User[" + userId.uuid() + "]");
    }

    @Test
    void logger_is_called_when_first_product_is_added() {
        String title = "happy liars";
        var basketItem = new BasketItem(new Product(title, 20, new ProductId(1)), 2);
        var basket = new Basket(userId, new ArrayList<>(List.of(basketItem)), CURRENT_DATE);
        basketRepository.add(basket);

        verify(mockLogger).print("[ITEM ADDED TO SHOPPING CART]: Added[" + CURRENT_DATE + "], User[" + userId.uuid() + "], Product[happy liars], Quantity[2], Price[<£20.00>]");
    }

    private Product getProduct() {
        return new Product("The Hobbit", 5, new ProductId(10001));
    }

    @Test
    void should_add_correct_basket() {
        UserId userId = new UserId();
        when(mockBasket.getUserId()).thenReturn(userId);
        basketRepository.add(mockBasket);

        assertEquals(mockBasket, baskets.get(userId));
    }

    @Test
    void update_existing_basket() {
        UserId userId = new UserId();
        baskets.put(userId, mockBasket);
        var product = getProduct();
        ArrayList<BasketItem> basketItems = new ArrayList<>(List.of(new BasketItem(product, 3)));
        var newBasket = new Basket(userId, basketItems, "");
        basketRepository.add(newBasket);
        verify(mockBasket).update(newBasket);
    }

    @Test
    void create_one_basket_per_user() {
        var userId = new UserId();
        var userId2 = new UserId();
        Basket mockBasket2 = mock(Basket.class);

        when(mockBasket.getUserId()).thenReturn(userId);
        when(mockBasket2.getUserId()).thenReturn(userId2);

        basketRepository.add(mockBasket);
        basketRepository.add(mockBasket2);

        assertEquals(2, baskets.entrySet().size());
    }

    @Test
    void get_basket_for_current_user() {
        UserId userId = new UserId();
        baskets.put(userId, mockBasket);

        assertEquals(mockBasket, basketRepository.basketFor(userId));
    }
}
