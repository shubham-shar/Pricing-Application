package com.skillovila.assignment.models.dtos;

import lombok.Builder;
import lombok.Getter;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
@Getter
@Builder
public class CouponDto {
    private String name;
    private DiscountDto discount;
}
