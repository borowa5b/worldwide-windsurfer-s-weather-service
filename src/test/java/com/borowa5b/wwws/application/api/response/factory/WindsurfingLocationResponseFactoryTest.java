package com.borowa5b.wwws.application.api.response.factory;

import com.borowa5b.wwws.domain.enumeration.City;
import com.borowa5b.wwws.domain.vo.Forecast;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class WindsurfingLocationResponseFactoryTest {

    @Test
    void shouldCreateWindsurfingLocationResponse() {
        // given
        final var city = City.JASTARNIA;
        final var forecastDate = LocalDate.of(2022, 1, 12);
        final var forecast = new Forecast(city, forecastDate, 24.2f, 5.9f);

        // when
        final var result = WindsurfingLocationResponseFactory.create(forecast);

        // then
        assertThat(result.city()).isEqualTo(city);
        assertThat(result.countryCode()).isEqualTo(city.getCountry());
        assertThat(result.forecastDate()).isEqualTo(forecastDate);
        assertThat(result.latitude()).isEqualTo(city.getLatitude());
        assertThat(result.longitude()).isEqualTo(city.getLongitude());
        assertThat(result.avgTemperature()).isEqualTo(forecast.avgTemperature());
        assertThat(result.windSpeed()).isEqualTo(forecast.windSpeed());
    }
}