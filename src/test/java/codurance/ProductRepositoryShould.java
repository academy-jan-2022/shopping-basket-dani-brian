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
}
