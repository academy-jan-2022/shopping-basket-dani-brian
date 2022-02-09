package codurance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BasketRepositoryShould {

    public static final String CURRENT_TIME = "2022-02-14";
    private Map<UserId, Basket> baskets;
    private InMemoryBasketRepository basketRepository;
    private Basket mockBasket;

    private Basket getBasket(UserId userId, Product product, int productQuantity) {
        var item = new BasketItem(product, productQuantity);
        return new Basket(userId, new ArrayList<>(List.of(item)), CURRENT_TIME);
    }

    @BeforeEach
    void setUp() {
        baskets = new HashMap<>();
        TimeProvider timeProviderMock = mock(TimeProvider.class);
        when(timeProviderMock.now()).thenReturn(CURRENT_TIME);
        mockBasket = mock(Basket.class);

        basketRepository = new InMemoryBasketRepository(baskets, timeProviderMock);
    }

    @Test
    void add_basket_to_map() {
        basketRepository.add(mockBasket);

        assertEquals(1, baskets.entrySet().size());
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
