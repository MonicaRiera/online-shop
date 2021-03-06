package tech.bts.onlineshop.business;

import org.junit.Assert;
import org.junit.Test;
import tech.bts.onlineshop.model.Discount;
import static org.junit.Assert.assertEquals;

public class DiscountServiceTest {

    @Test
    public void create_discount() {
        DiscountService discountService = new DiscountService();
        discountService.createDiscount(new Discount("BF2018", "Black Friday", 70, false));
        assertEquals(1, discountService.getDiscountMap().size());
    }

    @Test
    public void discount_percentage() {
        DiscountService discountService = new DiscountService();
        discountService.createDiscount(new Discount("XM2018", "Christmas sales", 50, true));
        Assert.assertEquals(100.0, discountService.calculateFinalAmount("XM2018", 200), 0.0);
    }

    @Test
    public void discount_absolute() {
        DiscountService discountService = new DiscountService();
        discountService.createDiscount(new Discount("BF2018", "Black Friday", 70, false));
        assertEquals(30.0, discountService.calculateFinalAmount("BF2018", 100), 0.0);
    }

    @Test
    public void not_existing_discount() {
        DiscountService discountService = new DiscountService();
        assertEquals(100.0, discountService.calculateFinalAmount("WHATEVER", 100), 0.0);
    }

    @Test
    public void discount_higher_than_amount() {
        DiscountService discountService = new DiscountService();
        discountService.createDiscount(new Discount("BF2018", "Black Friday", 70, false));
        double totalAmount = discountService.calculateFinalAmount("BF2018", 50);
        assertEquals(0, totalAmount, 0.0);
    }
}