package com.skillovila.assignment.api;

import java.util.Set;

import com.skillovila.assignment.models.dtos.CourseDto;
import com.skillovila.assignment.models.enums.PricingStrategy;
import com.skillovila.assignment.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
@Slf4j
@RestController
@RequestMapping("/course")
public class ApiController {
    
    @Autowired
    private PriceService priceService;

    @GetMapping("/all")
    public Set<CourseDto> getAllCourses(@RequestParam String currency) {
        log.info("Fetching all the course prices");
        return priceService.getCourses(currency);
    }
    
    @GetMapping("/{id}/price")
    public CourseDto getCourseById(@PathVariable("id") Long courseId, @RequestParam String currency) {
        log.info("Fetching the course {} price {}", courseId, currency);
        return priceService.fetchCourseById(courseId, currency);
    }
    
    @GetMapping("/strategy")
    public Set<CourseDto> getAllCourseBasedOnStrategy(@RequestParam("type") PricingStrategy strategy,
            @RequestParam String currency) {
        log.info("Fetching all the course based on strategy {} with currency {}", strategy, currency);
        return priceService.getCoursesByStrategy(strategy, currency);
    }
    
    @GetMapping("/{id}/coupon/price")
    public CourseDto getCourseById(@PathVariable("id") Long courseId, @RequestParam String currency,
            @RequestParam String coupon) {
        log.info("Fetching the course {} price, {} currency and with coupon - {}", courseId, currency, coupon);
        return priceService.fetchCourseByIdWithCoupon(courseId, currency, coupon);
    }
}
