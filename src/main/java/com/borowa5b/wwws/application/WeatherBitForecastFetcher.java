package com.borowa5b.wwws.application;

import com.borowa5b.wwws.domain.ForecastFetcher;
import com.borowa5b.wwws.domain.vo.City;
import com.borowa5b.wwws.domain.vo.Forecast;
import com.borowa5b.wwws.infrastructure.weather.bit.WeatherBitClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class WeatherBitForecastFetcher implements ForecastFetcher {

    private final WeatherBitClient weatherBitClient;

    @Override
    public Forecast fetch(final int daysToFetch, final LocalDate forecastDate, final City city) {
        final var forecastResponse = weatherBitClient.getForecast(daysToFetch, city);
        return Arrays.stream(forecastResponse.data())
                .filter(data -> data.datetime().isEqual(forecastDate))
                .map(data -> new Forecast(city, data.datetime(), data.temp(), data.windSpd()))
                .findFirst().orElse(null);
    }
}
