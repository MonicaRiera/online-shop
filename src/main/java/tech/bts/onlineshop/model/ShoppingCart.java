package tech.bts.onlineshop.model;

import java.util.List;

/**
 * The items to be purchased.
 */
public class ShoppingCart {

    private List<CartItem> items;

    public ShoppingCart(List<CartItem> items) {
        this.items = items;
    }

    public List<CartItem> getItems() {
        return items;
    }
}
