package codurance;

public record BasketItem(Product product, int quantity) {
    public boolean sameProduct(ProductId productId) {
        return product.id().equals(productId);
    }

    public BasketItem updateQuantity(BasketItem basketItem) {
        return new BasketItem(product, basketItem.quantity() + quantity);
    }
}
