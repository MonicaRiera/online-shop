package tech.bts.onlineshop.business;

import org.junit.Test;
import tech.bts.onlineshop.data.ProductDatabase;
import tech.bts.onlineshop.model.CartItem;
import tech.bts.onlineshop.model.Product;
import tech.bts.onlineshop.model.ShoppingCart;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest {

    @Test
    public void empty_catalog_has_no_products() {

        ProductDatabase productDatabase = new ProductDatabase();
        ProductService productService = new ProductService(productDatabase);
        assertEquals(0, productService.getCount());
    }
    @Test
    public void add_product_to_catalog() {

        ProductDatabase productDatabase = new ProductDatabase();
        ProductService productService = new ProductService(productDatabase);
        long p1Id = productService.createProduct(new Product("pixel", "Google", 800));
        assertEquals(1, productService.getCount());
        assertEquals("pixel", productService.getProductById(p1Id).getName());
    }
    @Test
    public void product_is_available() {

        ProductDatabase productDatabase = new ProductDatabase();
        ProductService productService = new ProductService(productDatabase);
        long p1Id = productService.createProduct(new Product("pixel", "Google", 800));
        boolean available = productService.checkAvailability(p1Id, 3);
        assertEquals(false, available);

        productService.addProductStock(p1Id, 500);
        available = productService.checkAvailability(p1Id, 3);
        assertEquals(true, available);
    }
    @Test
    public void product_available_quantity() {
        ProductDatabase productDatabase = new ProductDatabase();
        ProductService productService = new ProductService(productDatabase);
        long p1Id = productService.createProduct(new Product("pixel", "Google", 800));
        assertEquals(0, productService.amountAvailable(p1Id, 50));

        productService.addProductStock(p1Id, 100);
        assertEquals(50, productService.amountAvailable(p1Id, 50));
        assertEquals(100, productService.amountAvailable(p1Id, 200));
    }
    @Test
    public void purchase_reduces_products_stock() {
        ProductDatabase productDatabase = new ProductDatabase();
        ProductService productService = new ProductService(productDatabase);
        ShoppingCart shoppingCart = new ShoppingCart();

        long p1Id = productService.createProduct(new Product("pixel", "Google", 800));
        long p2Id = productService.createProduct(new Product("iphone", "Apple", 900));
        long p3Id = productService.createProduct(new Product("pen", "Pilot", 3));
        productService.addProductStock(p1Id, 100);
        productService.addProductStock(p2Id, 50);
        productService.addProductStock(p3Id, 300);
        assertEquals(100, productDatabase.get(p1Id).getQuantity());
        assertEquals(50, productDatabase.get(p2Id).getQuantity());
        assertEquals(300, productDatabase.get(p3Id).getQuantity());

        shoppingCart.add(new CartItem(p1Id, 20));
        shoppingCart.add(new CartItem(p2Id, 10));
        productService.purchase(shoppingCart);
        assertEquals(80, productDatabase.get(p1Id).getQuantity());
        assertEquals(40, productDatabase.get(p2Id).getQuantity());
        assertEquals(300, productDatabase.get(p3Id).getQuantity());

    }
}
