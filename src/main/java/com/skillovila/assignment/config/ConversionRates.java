package com.skillovila.assignment.config;

import com.skillovila.assignment.exceptions.UnauthorizedException;
import com.skillovila.assignment.models.enums.Currency;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
@Slf4j
@Configuration
@ConfigurationProperties(value = "conversion-rates")
public class ConversionRates {
    private Double usd;
    private Double euro;
    
    public Double getRate(String currency) {
        switch (Currency.valueOf(currency)) {
            case INR: {
                return 1.0;
            }
            case EUR: {
                return euro;
            }
            case USD: {
                return usd;
            }
            default: {
                log.error("Wrong currency passed in request : {}", currency);
                throw new UnauthorizedException("Wrong currency passed in request : " + currency);
            }
        }
    }
}
