package codurance;
import org.junit.jupiter.api.Test;

public class BasketRepositoryShould {
    @Test
    void add_item_to_basket() {
        var basketRepository = new InMemoryBasketRepository();
        var product = new Product("The Hobbit", 5, new ProductId(10001));
        UserId userId = new UserId();
        basketRepository.add(userId, product, 2);
        var basket = basketRepository.basketFor(userId);
    }
}
