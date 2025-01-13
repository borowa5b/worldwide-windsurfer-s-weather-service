package com.borowa5b.wwws.application.api.response.factory;

import com.borowa5b.wwws.domain.vo.City;
import com.borowa5b.wwws.domain.vo.Forecast;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class WindsurfingLocationResponseFactoryTest {

    @Test
    void shouldCreateWindsurfingLocationResponse() {
        // given
        final var city = new City("ZIELONA_GORA", 51.94260f, 15.53794f, "PL");
        final var forecastDate = LocalDate.of(2022, 1, 12);
        final var forecast = new Forecast(city, forecastDate, 24.2f, 5.9f);

        // when
        final var result = WindsurfingLocationResponseFactory.create(forecast);

        // then
        assertThat(result.city()).isEqualTo(city.name());
        assertThat(result.countryCode()).isEqualTo(city.country());
        assertThat(result.forecastDate()).isEqualTo(forecastDate);
        assertThat(result.latitude()).isEqualTo(city.latitude());
        assertThat(result.longitude()).isEqualTo(city.longitude());
        assertThat(result.avgTemperature()).isEqualTo(forecast.avgTemperature());
        assertThat(result.windSpeed()).isEqualTo(forecast.windSpeed());
    }
}