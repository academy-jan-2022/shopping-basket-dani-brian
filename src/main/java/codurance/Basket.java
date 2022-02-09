package codurance;

import java.util.List;
import java.util.Objects;

public final class Basket {
    private UserId userId;
    private List<BasketItem> basketItems;

    public Basket(UserId userId, List<BasketItem> basketItems) {
        this.userId = userId;
        this.basketItems = basketItems;
    }

    public String getDate() {
        throw new UnsupportedOperationException();
    }

    public int getQuantity(ProductId productId) {
        throw new UnsupportedOperationException();
    }

    public int getTotal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Basket) obj;
        return Objects.equals(this.userId, that.userId) &&
            Objects.equals(this.basketItems, that.basketItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, basketItems);
    }
}
