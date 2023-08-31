package com.ipi.jva324.commande.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.Collections;
@Configuration
public class HalConfiguration {

    @Bean
    @Qualifier("springDataRestTemplate")
    public RestTemplate springDataRestTemplate(@Autowired ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(org.springframework.hateoas.MediaTypes.HAL_JSON));
        converter.setObjectMapper(objectMapper);

        return new RestTemplate(Collections.singletonList(converter));
    }

    @Bean
    @Qualifier("restTemplate")
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
