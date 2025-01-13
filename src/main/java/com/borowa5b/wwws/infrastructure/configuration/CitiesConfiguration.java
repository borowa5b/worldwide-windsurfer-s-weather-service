package com.borowa5b.wwws.infrastructure.configuration;

import com.borowa5b.wwws.domain.vo.City;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "configuration")
public class CitiesConfiguration {

    private City[] cities;
}
