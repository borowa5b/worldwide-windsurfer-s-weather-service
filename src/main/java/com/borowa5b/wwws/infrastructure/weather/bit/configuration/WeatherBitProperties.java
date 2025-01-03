package com.borowa5b.wwws.infrastructure.weather.bit.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "service.weather-bit")
public class WeatherBitProperties {

    private String apiKey;
    private String baseUrl;
    private String forecastUrl;
}
