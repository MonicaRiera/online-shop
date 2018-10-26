package tech.bts.onlineshop.business;

import tech.bts.onlineshop.data.ProductDatabase;
import tech.bts.onlineshop.model.CartItem;
import tech.bts.onlineshop.model.Product;
import tech.bts.onlineshop.model.ShoppingCart;


public class ProductService {

    private ProductDatabase productDatabase;

    public ProductService(ProductDatabase productDatabase) {
        this.productDatabase = productDatabase;
    }
    /** Adds a product and returns its ID*/
    public long createProduct (Product product) {
        this.productDatabase.add(product);
        return product.getId();
    }

    public void addProductStock (long productId, int quantity) {
        Product product = this.productDatabase.get(productId);
        product.setQuantity(product.getQuantity() + quantity);
    }

    public Product getProductById (long productId) {
        return this.productDatabase.get(productId);
    }

    /**Given a cart, checks the availability of the products and returns a new cart with the num of
     * products that can be delivered (in stock)*/
    public ShoppingCart checkCart (ShoppingCart cart) {

        for (CartItem cartItem : cart.getItems()) {
            Product product = getProductById(cartItem.getProductId());
            if (cartItem.getQuantity() > product.getQuantity()){
                cartItem.setQuantity(product.getQuantity());
            }
        }

        return cart;
    }

    /**Given a product id and a desired quantity, returns true if the quantity is possible
     * to deliver*/
    public boolean checkAvailability (long productId, int quantity) {
        return this.productDatabase.get(productId).getQuantity() >= quantity;
    }

    /**Given a product id and a desired quantity, returns the quantity that is possible to deliver*/
    public int amountAvailable (long productID, int quantity) {
        return Math.min(this.productDatabase.get(productID).getQuantity(), quantity);
    }

    /**Reduces the quantities of the products by the quantities in the cart*/
    public void purchase (ShoppingCart cart) {

        for (CartItem item : cart.getItems()) {
            Product product = this.productDatabase.get(item.getProductId());
            product.setQuantity(product.getQuantity() - item.getQuantity());
        }
    }

}
