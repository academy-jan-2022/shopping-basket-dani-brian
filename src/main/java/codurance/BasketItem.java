package codurance;

public record BasketItem(Product product, int quantity) {
    public boolean sameProduct(BasketItem basketItem) {
        return product.id().equals(basketItem.product.id());
    }
}
