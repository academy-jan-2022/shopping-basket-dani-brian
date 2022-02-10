package codurance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserIdShould {

    @Test
    void create_a_user_with_id_1() {
        UserId userId = new UserId();
        Assertions.assertEquals(1, userId.id());
    }

    @Test
    void create_a_user_with_id_2() {
        UserId userId2 = new UserId(2);
        Assertions.assertEquals(2, userId2.id());
    }
}
