package codurance;

public class InMemoryProductRepository implements ProductRepository{
    @Override
    public Product getById(ProductId productId) {
        return new Product("Lord of the Rings", 10, new ProductId(10001));
    }
}
