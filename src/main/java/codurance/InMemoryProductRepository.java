package codurance;

public class InMemoryProductRepository implements ProductRepository{
    @Override
    public Product getById(ProductId productId) {
        if(productId.equals(new ProductId(10002))){
            return new Product("The Hobbit",5, productId);
        }

        if(productId.equals(new ProductId(20001))){
            return new Product("Game of Thrones",9, productId);
        }
        return new Product("Lord of the Rings", 10, new ProductId(10001));
    }
}
