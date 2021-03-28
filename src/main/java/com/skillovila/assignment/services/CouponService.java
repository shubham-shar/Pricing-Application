package com.skillovila.assignment.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.skillovila.assignment.models.dtos.CouponDto;
import com.skillovila.assignment.models.dtos.CourseDto;
import com.skillovila.assignment.models.dtos.DiscountDto;
import com.skillovila.assignment.models.dtos.PriceDto;
import com.skillovila.assignment.models.dtos.TaxComponentDto;
import com.skillovila.assignment.models.entities.Coupon;
import com.skillovila.assignment.models.entities.Course;
import com.skillovila.assignment.models.entities.Discount;
import com.skillovila.assignment.models.entities.Price;
import com.skillovila.assignment.models.entities.TaxComponent;
import com.skillovila.assignment.models.enums.Currency;
import com.skillovila.assignment.models.enums.PricingStrategy;
import com.skillovila.assignment.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
@Component
public class CouponService {
    @Autowired
    DiscountRepository discountRepository;
    
    public CourseDto getCourseDtoFromCourseEntityWithCoupon(Course course, String currency, Double conversionMultiple,
            Coupon savedCoupon) {
        return CourseDto.builder()
                        .id(course.getId())
                        .name(course.getName())
                        .description(course.getDescription())
                        .prices(getPricesDto(course, currency, conversionMultiple, savedCoupon))
                        .build();
    }
    
    private Set<PriceDto> getPricesDto(Course course, String currency, Double conversionMultiple, Coupon coupon) {
        return course.getPrices().stream()
                     .map(price -> convertPriceEntityToPriceDto(price, currency, conversionMultiple, coupon))
                     .collect(Collectors.toSet());
    }
    
    private PriceDto convertPriceEntityToPriceDto(Price price, String currency, Double conversionMultiple,
            Coupon coupon) {
        Discount discount = discountRepository.findByCourseIdAndCouponId(price.getCourse().getId(), coupon.getId())
                                              .orElseThrow();
        Double totalAmount = getTotalAmount(price, price.getTaxes(), discount, conversionMultiple);
        return PriceDto.builder()
                       .totalAmount(totalAmount)
                       .baseAmount(getBaseAmount(price, conversionMultiple))
                       .pricingStrategy(price.getStrategy().getType())
                       .durationOfMonths(price.getStrategy().getDurationInMonths())
                       .currency(Currency.valueOf(currency.toUpperCase()))
                       .conversionMultiple(conversionMultiple)
                       .couponDto(getCouponDtoFromEntity(coupon, discount))
                       .isCouponApplied(true)
                       .taxes(getTaxDtoFromEntity(price.getTaxes(), conversionMultiple))
                       .build();
    }
    
    private Double getTotalAmount(Price price, TaxComponent taxes, Discount discount, Double conversionMultiple) {
        return (Optional.ofNullable(price).map(Price::getBaseAmount).orElse(0.0)
                + Optional.ofNullable(taxes).map(TaxComponent::getGstAmount).orElse(0.0)
                - Optional.ofNullable(discount).map(Discount::getAmount).orElse(0.0))/conversionMultiple;
    }
    
    private CouponDto getCouponDtoFromEntity(Coupon coupon, Discount discount) {
        return CouponDto.builder()
                .discount(DiscountDto.builder().amount(discount.getAmount()).build())
                .name(coupon.getName())
                .build();
    }
    
    private double getBaseAmount(Price price, Double conversionMultiple) {
        PricingStrategy pricingStrategy = price.getStrategy().getType();
        /*
            Assumption - If Pricing Strategy is subscription based then base price should be per month bases
            UI should show prices as per month only.
         */
        if(PricingStrategy.SUBSCRIPTION.equals(pricingStrategy)) {
            return (price.getBaseAmount() / conversionMultiple) / price.getStrategy().getDurationInMonths();
        }
        return price.getBaseAmount() / conversionMultiple;
    }
    
    private TaxComponentDto getTaxDtoFromEntity(TaxComponent taxes, Double conversionMultiple) {
        return TaxComponentDto.builder()
                              .gstAmount(taxes.getGstAmount() / conversionMultiple)
                              .build();
    }
}
