package codurance;

import java.util.HashMap;
import java.util.Map;

public class InMemoryProductRepository implements ProductRepository{
    Map<ProductId, Product> productMap = Map.of(
        new ProductId(10002), new Product("The Hobbit",5, new ProductId(10002)),
        new ProductId(20001), new Product("Game of Thrones",9, new ProductId(20001)),
        new ProductId(10001), new Product("Lord of the Rings",10, new ProductId(10001)),
        new ProductId(20110), new Product("Breaking Bad",7, new ProductId(20110))
    );

    @Override
    public Product getById(ProductId productId) {
        return productMap.get(productId);
    }
}
