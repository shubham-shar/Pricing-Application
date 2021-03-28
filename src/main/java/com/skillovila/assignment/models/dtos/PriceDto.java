package com.skillovila.assignment.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skillovila.assignment.models.entities.Course;
import com.skillovila.assignment.models.entities.TaxComponent;
import com.skillovila.assignment.models.enums.Currency;
import com.skillovila.assignment.models.enums.PricingStrategy;
import lombok.Builder;
import lombok.Getter;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceDto {
    private Double totalAmount;
    
    private Double baseAmount;
    
    private TaxComponentDto taxes;
    
    private Double conversionMultiple;
    
    private Double discount;
    
    private boolean isCouponApplied = false;
    
    private String appliedCoupon = null;
    
    private Currency currency = Currency.INR;
    
    private PricingStrategy pricingStrategy;
    
    private int durationOfMonths;
    
}
