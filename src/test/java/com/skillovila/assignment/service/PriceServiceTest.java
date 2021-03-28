package com.skillovila.assignment.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.skillovila.assignment.config.ConversionRates;
import com.skillovila.assignment.models.dtos.CourseDto;
import com.skillovila.assignment.models.dtos.PriceDto;
import com.skillovila.assignment.models.dtos.TaxComponentDto;
import com.skillovila.assignment.models.entities.Course;
import com.skillovila.assignment.models.entities.Price;
import com.skillovila.assignment.models.entities.PricingStrategy;
import com.skillovila.assignment.models.entities.TaxComponent;
import com.skillovila.assignment.repositories.CourseRepository;
import com.skillovila.assignment.repositories.PriceRepository;
import com.skillovila.assignment.services.PriceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
@RunWith(MockitoJUnitRunner.class)
public class PriceServiceTest {
    
    public static final String NAME = "random name";
    public static final String DESCRIPTION = "random description";
    public static final double BASE_AMOUNT = 12345.0;
    public static final double GST_AMOUNT = 100.0;
    @InjectMocks
    PriceService priceService;
    
    @Mock
    CourseRepository courseRepository;
    
    @Mock
    ConversionRates conversionRates;
    
    @Test
    public void getCourses(){
        Mockito.when(conversionRates.getRate(anyString())).thenReturn(1.0);
        Mockito.when(courseRepository.findAll()).thenReturn(getCourseEntities());
        Set<CourseDto> courses = priceService.getCourses("inr");
        assertNotEquals(0, courses.size());
        Optional<CourseDto> first = courses.stream().findFirst();
        assertTrue(first.isPresent());
        assertEquals(NAME, first.get().getName());
    }
    
    @Test
    public void getCourseById(){
        Mockito.when(conversionRates.getRate(anyString())).thenReturn(1.0);
        Mockito.when(courseRepository.findById(anyLong())).thenReturn(Optional.of(getCourseEntity()));
        CourseDto courses = priceService.fetchCourseById(1L, "inr");
        assertEquals(NAME, courses.getName());
    }
    
    @Test
    public void getCourseByStrategy(){
        Mockito.when(conversionRates.getRate(anyString())).thenReturn(1.0);
        Mockito.when(courseRepository.findCoursesByStrategy(any(com.skillovila.assignment.models.enums.PricingStrategy.class)))
               .thenReturn(new HashSet<>(getCourseEntities()));
        Set<CourseDto> courses = priceService
                .getCoursesByStrategy(com.skillovila.assignment.models.enums.PricingStrategy.ONE_TIME, "inr");
        assertNotEquals(0, courses.size());
        Optional<CourseDto> first = courses.stream().findFirst();
        assertTrue(first.isPresent());
        assertEquals(NAME, first.get().getName());
    }
    
    private List<Course> getCourseEntities() {
        Course course = getCourseEntity();
        return List.of(course);
    }
    
    private Course getCourseEntity() {
        Course course = new Course();
        course.setName(NAME);
        course.setPrices(getPriceEntities());
        course.setDescription(DESCRIPTION);
        return course;
    }
    
    private Set<CourseDto> getCourseDtos() {
        CourseDto course = getCourseDto();
        return Set.of(course);
    }
    
    private CourseDto getCourseDto() {
        return CourseDto.builder()
                        .name(NAME)
                        .prices(getPriceDtos())
                        .description(DESCRIPTION)
                        .build();
    }
    
    private PriceDto getPriceDto() {
        return PriceDto.builder()
                       .baseAmount(BASE_AMOUNT)
                       .taxes(getTaxComponentDto())
                       .build();
    }
    
    private Price getPriceEntity() {
        Price price = new Price();
        price.setBaseAmount(BASE_AMOUNT);
        price.setStrategy(getPricingStrategy());
        price.setTaxes(getTaxComponentEntity());
        return price;
    }
    
    private TaxComponentDto getTaxComponentDto() {
        return TaxComponentDto.builder()
                       .gstAmount(GST_AMOUNT)
                       .build();
    }
    
    private TaxComponent getTaxComponentEntity() {
        TaxComponent taxComponent = new TaxComponent();
        taxComponent.setGstAmount(GST_AMOUNT);
        return taxComponent;
    }
    
    private Set<PriceDto> getPriceDtos() {
        return Set.of(getPriceDto());
    }
    
    private Set<Price> getPriceEntities() {
        return Set.of(getPriceEntity());
    }
    
    private PricingStrategy getPricingStrategy() {
        PricingStrategy pricingStrategy = new PricingStrategy();
        pricingStrategy.setType(com.skillovila.assignment.models.enums.PricingStrategy.ONE_TIME);
        return pricingStrategy;
    }
}
