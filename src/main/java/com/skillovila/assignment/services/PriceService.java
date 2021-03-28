package com.skillovila.assignment.services;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.skillovila.assignment.config.ConversionRates;
import com.skillovila.assignment.exceptions.EntityNotFoundException;
import com.skillovila.assignment.exceptions.UnauthorizedException;
import com.skillovila.assignment.models.dtos.CourseDto;
import com.skillovila.assignment.models.dtos.PriceDto;
import com.skillovila.assignment.models.dtos.TaxComponentDto;
import com.skillovila.assignment.models.entities.Coupon;
import com.skillovila.assignment.models.entities.Course;
import com.skillovila.assignment.models.entities.Price;
import com.skillovila.assignment.models.entities.TaxComponent;
import com.skillovila.assignment.models.enums.Currency;
import com.skillovila.assignment.models.enums.PricingStrategy;
import com.skillovila.assignment.repositories.CouponRepository;
import com.skillovila.assignment.repositories.CourseRepository;
import com.skillovila.assignment.repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
@Slf4j
@Service
public class PriceService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private CouponRepository couponRepository;
    
    @Autowired
    private CouponService couponService;
    
    @Autowired
    ConversionRates conversionRates;
    
    public Set<CourseDto> getCourses(String currency) {
        Double conversionMultiple = conversionRates.getRate(currency.toUpperCase());
        
        return courseRepository.findAll().stream()
                               .map(course -> getCourseDtoFromCourseEntity(course, currency, conversionMultiple))
                               .collect(Collectors.toSet());
    }
    
    public CourseDto fetchCourseById(Long courseId, String currency) {
        Course course= courseRepository.findById(courseId).orElseThrow(() -> {
                                    log.error("Course with id {} not found", courseId);
                                    throw new EntityNotFoundException("Course with id " + courseId + " not found");
                                });
        Double conversionMultiple = conversionRates.getRate(currency.toUpperCase());
        return getCourseDtoFromCourseEntity(course, currency, conversionMultiple);
        
    }
    
    public Set<CourseDto> getCoursesByStrategy(PricingStrategy strategy, String currency) {
        Set<Course> courses = courseRepository.findCoursesByStrategy(strategy);
        Double conversionMultiple = conversionRates.getRate(currency.toUpperCase());
        return courses.stream()
                      .map(course -> getCourseDtoFromCourseEntityBasedOnStrategy(course, currency, conversionMultiple,
                                                                                 strategy))
                      .collect(Collectors.toSet());
    }
    
    
    public CourseDto fetchCourseByIdWithCoupon(Long courseId, String currency, String coupon) {
        Course course= courseRepository.findById(courseId).orElseThrow(() -> {
            log.error("Course with id {} not found", courseId);
            throw new EntityNotFoundException("Course with id " + courseId + " not found");
        });
    
        Coupon savedCoupon =  couponRepository.findByName(coupon).orElseThrow(() -> {
            log.error("Coupon with name {} not found", coupon);
            throw new EntityNotFoundException("Course with name " + coupon + " not found");
        });
        Double conversionMultiple = conversionRates.getRate(currency.toUpperCase());
        if(!isCouponValid(course, coupon)){
            log.error("Coupon {} used for discount is not valid for course {}", coupon, currency);
            throw new UnauthorizedException("Coupon "+ coupon + " not valid");
        }
        return couponService.getCourseDtoFromCourseEntityWithCoupon(course, currency, conversionMultiple, savedCoupon);
    }
    
    private boolean isCouponValid(Course course, String coupon) {
        return Optional.ofNullable(course.getCoupons()).map(coupons-> coupons.stream().filter(Objects::nonNull)
                     .anyMatch(validCoupon -> validCoupon.getName().equalsIgnoreCase(coupon)))
                .orElse(false);
    }
    
    private PriceDto convertPriceEntityToPriceDto(Price price, String currency,
                                                Double conversionMultiple) {
        Double totalAmount = getTotalAmount(price, price.getTaxes(), conversionMultiple);
        return PriceDto.builder()
                       .totalAmount(totalAmount)
                       .baseAmount(getBaseAmount(price, conversionMultiple))
                       .pricingStrategy(price.getStrategy().getType())
                       .durationOfMonths(price.getStrategy().getDurationInMonths())
                       .currency(Currency.valueOf(currency.toUpperCase()))
                       .conversionMultiple(conversionMultiple)
                       .taxes(getTaxDtoFromEntity(price.getTaxes(), conversionMultiple))
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
    
    private CourseDto getCourseDtoFromCourseEntity(Course course, String currency, Double conversionMultiple) {
        return CourseDto.builder()
                        .id(course.getId())
                        .name(course.getName())
                        .description(course.getDescription())
                        .prices(getPricesDto(course, currency, conversionMultiple))
                        .build();
    }
    
    private CourseDto getCourseDtoFromCourseEntityBasedOnStrategy(Course course, String currency,
            Double conversionMultiple, PricingStrategy strategy) {
        return CourseDto.builder()
                        .id(course.getId())
                        .name(course.getName())
                        .description(course.getDescription())
                        .prices(getPricesDtoByStrategy(course, currency, conversionMultiple, strategy))
                        .build();
    }
    
    private Set<PriceDto> getPricesDto(Course course, String currency, Double conversionMultiple) {
        return course.getPrices().stream()
                     .map(price -> convertPriceEntityToPriceDto(price, currency, conversionMultiple))
                     .collect(Collectors.toSet());
    }
    
    private Set<PriceDto> getPricesDtoByStrategy(Course course, String currency, Double conversionMultiple,
            PricingStrategy pricingStrategy) {
        return course.getPrices().stream()
                     .filter(price -> pricingStrategy.equals(price.getStrategy().getType()))
                     .map(price -> convertPriceEntityToPriceDto(price, currency, conversionMultiple))
                     .collect(Collectors.toSet());
    }
    
    private Double getTotalAmount(Price price, TaxComponent taxes, Double conversionRate) {
        return (Optional.ofNullable(price).map(Price::getBaseAmount).orElse(0.0)
                + Optional.ofNullable(taxes).map(TaxComponent::getGstAmount).orElse(0.0))/conversionRate;
    }
    
    private TaxComponentDto getTaxDtoFromEntity(TaxComponent taxes, Double conversionMultiple) {
        return TaxComponentDto.builder()
                              .gstAmount(taxes.getGstAmount() / conversionMultiple)
                              .build();
    }
}
