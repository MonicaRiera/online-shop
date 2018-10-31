package tech.bts.onlineshop.business;

import tech.bts.onlineshop.model.Discount;

import java.util.HashMap;
import java.util.Map;

public class DiscountService {

    private Map<String, Discount> discountMap = new HashMap<>();

    /**Adds a given discount to the map*/
    public void createDiscount(Discount discount) {
        discountMap.put(discount.getId(), discount);
    }

    /**Given a discount id and an amount, returns the final amount after applying the discount.
    If the discount doesn't exist, just returns the original amount with no discount.
    It checks if the discount exists, and if so, checks if the discount is absolute or a percentage
    to properly calculate the final amount.*/
    public double calculateFinalAmount (String discountId, double amount) {
        Discount discount = this.discountMap.get(discountId);
        double finalAmount;

        if (discount != null) {
            if (discount.isPercentage()) {
                finalAmount = amount *(1 - discount.getAmount() /100);
            } else {
                finalAmount = amount - discount.getAmount();
            }
        } else {
            finalAmount = amount;
        }

        if (finalAmount < 0) {
            return 0;
        } else {
            return finalAmount;
        }
    }

    public Map<String, Discount> getDiscountMap() {
        return discountMap;
    }
}

