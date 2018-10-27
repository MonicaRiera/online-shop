package tech.bts.onlineshop.business;

import tech.bts.onlineshop.model.Discount;

import java.util.Map;

public class DiscountService {

    private Map<String, Discount> discountMap;

    /**Adds a given discount to the map*/
    public void createDiscount(Discount discount) {
        this.discountMap.put(discount.getId(), discount);
    }

    /**Given a discount id and an amount, returns the final amount after applying the discount.
    If the discount doesn't exist, just returns the original amount with no discount.
    It checks if the discount exists, and if so, checks if the discount is absolute or a percentage
    to properly calculate the final amount.*/
    public double calculateFinalAmount (String discountId, double amount) {
        Discount discount = this.discountMap.get(discountId);
        double finalAmount = amount;

        if (discount != null) {
            if (discount.isPercentage()) {
                finalAmount = amount * discount.getAmount() /100;
            } else {
                finalAmount = amount - discount.getAmount();
            }
        }

        return finalAmount;
    }
}

