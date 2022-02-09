package codurance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductRepositoryShould {

    @Test
    void get_lord_of_the_rings() {
        var productRepository = new InMemoryProductRepository();

        ProductId id = new ProductId(10001);

        Product lord_of_the_rings = new Product("Lord of the Rings", 10, id);

        assertEquals(lord_of_the_rings, productRepository.getById(id));
    }

    @Test
    void get_the_hobbit() {
        var productRepository = new InMemoryProductRepository();

        ProductId id = new ProductId(10002);

        Product the_hobbit = new Product("The Hobbit", 5, id);

        assertEquals(the_hobbit, productRepository.getById(id));
    }


    @Test
    void get_game_of_thrones() {
        var productRepository = new InMemoryProductRepository();

        ProductId id = new ProductId(20001);

        Product the_hobbit = new Product("Game of Thrones", 9, id);

        assertEquals(the_hobbit, productRepository.getById(id));
    }
}
