package com.directa24.main.challenge.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class DeserealizerConfig {
    // This is needed because the external website doesn't include the header 'Content-Type: application/json'.
    // instead it includes 'Content-Type: application/octet-stream'
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        // Accepts the media type application/octet-stream
        jacksonConverter.setSupportedMediaTypes(
                Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM)
        );

        restTemplate.setMessageConverters(Collections.singletonList(jacksonConverter));
        return restTemplate;
    }
}
