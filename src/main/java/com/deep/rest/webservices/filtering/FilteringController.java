package com.deep.rest.webservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilteringController {

    /*
    * This method is an example of static Filtering
    * */
    @GetMapping("/filtering")
    public SomeBean getStaticFilter(){
        return new SomeBean("value1","value2","value3");
    }

    /*
    * This is example of dyanamic Filtering
    * */
    @GetMapping("/dynamicfilter")
    public MappingJacksonValue getDyanamicFilter(){
        DynamicSomeBean dynamicSomeBean = new DynamicSomeBean("value1","value2","value3");
        SimpleBeanPropertyFilter beanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
        FilterProvider filters = new SimpleFilterProvider().addFilter("beanFilter",beanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(dynamicSomeBean);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

}
