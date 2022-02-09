package codurance;

public class InMemoryProductRepository implements ProductRepository{
    @Override
    public Product getById(ProductId productId) {
        throw new UnsupportedOperationException();
    }
}
