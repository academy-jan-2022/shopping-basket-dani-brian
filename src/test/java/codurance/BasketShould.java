package codurance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class BasketShould {
    @Test
    void return_current_date() {
        var expected = "2022-02-14";
        var basket = new Basket(new UserId(), List.of(),expected);

        Assertions.assertEquals(expected,basket.getDate());

    }
}
