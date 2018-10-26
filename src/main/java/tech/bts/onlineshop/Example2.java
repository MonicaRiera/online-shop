package tech.bts.onlineshop;

import tech.bts.onlineshop.business.ProductService;
import tech.bts.onlineshop.data.ProductDatabase;
import tech.bts.onlineshop.model.CartItem;
import tech.bts.onlineshop.model.Product;
import tech.bts.onlineshop.model.ShoppingCart;

import java.util.List;

public class Example2 {
    public static void main(String[] args) {

        ProductDatabase productDatabase = new ProductDatabase();

        ProductService productService = new ProductService(productDatabase);
        long p1Id = productService.createProduct(new Product("MacBook", "Apple", 1500));
        long p2Id = productService.createProduct(new Product("iPhone xs", "Apple", 1200));

        productService.addProductStock(p1Id, 100);
        productService.addProductStock(p1Id, 200);
        productService.addProductStock(p2Id, 250);

        Product product = productService.getProductById(p1Id);
        System.out.println("There are " + product.getQuantity() + " units of " + product.getName() + "s");

        //List<CartItem> items = Arrays.asList(
        //      new CartItem(p1Id, 150),
        //      new CartItem(p2Id, 300));

        ShoppingCart cart = new ShoppingCart();
        cart.add(new CartItem(p1Id, 150));
        System.out.println(productService.amountAvailable(p1Id, 150));
        cart.add(new CartItem(p2Id, 300));
        System.out.println(productService.checkAvailability(p2Id, 300));

        ShoppingCart finalCart = productService.checkCart(cart);
        List<CartItem> finalItems = finalCart.getItems();

        System.out.println("Final cart with available products:");
        for (CartItem finalItem : finalItems) {
            Product product1 = productService.getProductById(finalItem.getProductId());
            System.out.println(product1.getName() + " : " + finalItem.getQuantity() + " units");
        }

        System.out.println(productService.getProductById(p1Id).getName()
                + " units before purchasing: "
                + productService.getProductById(p1Id).getQuantity());

        productService.purchase(finalCart);

        System.out.println(productService.getProductById(p1Id).getName()
                + " units after purchasing: "
                + productService.getProductById(p1Id).getQuantity());






    }
}
