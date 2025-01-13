package com.borowa5b.wwws.application;

import com.borowa5b.wwws.domain.CitiesResolver;
import com.borowa5b.wwws.domain.vo.City;
import com.borowa5b.wwws.infrastructure.configuration.CitiesConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultCitiesResolver implements CitiesResolver {

    private final CitiesConfiguration citiesConfiguration;

    @Override
    public List<City> resolve() {
        return Arrays.stream(citiesConfiguration.getCities()).toList();
    }
}
