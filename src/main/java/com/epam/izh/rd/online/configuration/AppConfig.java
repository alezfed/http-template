package com.epam.izh.rd.online.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;

@Configuration
@ComponentScan("com.epam.izh.rd.online")
@PropertySource("application.properties")
public class AppConfig {
    @Value("${rest.url}")
    private String restUrl;
    @Value("${connect.timeout}")
    private int connectTimeout;
    @Value("${read.timeout}")
    private int readTimeout;
    @Value("${rest.header}")
    private String header;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplateBuilder(rt -> rt.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add(header, header);
            return execution.execute(request, body);
        }))
                .setConnectTimeout(Duration.ofSeconds(connectTimeout))
                .setReadTimeout(Duration.ofSeconds(readTimeout))
                .build();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(restUrl));
        return restTemplate;
    }
}