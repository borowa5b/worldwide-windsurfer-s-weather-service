package com.borowa5b.wwws.domain;

import com.borowa5b.wwws.domain.exception.WindsurfingLocationNotFoundException;
import com.borowa5b.wwws.domain.vo.Forecast;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WindsurfingForecastResolver {

    private final ForecastFetcher forecastFetcher;
    private final CitiesResolver citiesResolver;

    @Cacheable(cacheNames = "forecast")
    public Forecast resolve(final LocalDate forecastDate) {
        final var daysToFetch = resolveDaysToFetch(forecastDate);
        final var forecasts = citiesResolver.resolve().stream()
                .map(city -> forecastFetcher.fetch(daysToFetch, forecastDate, city))
                .toList();
        final var forecastWithBestConditions = resolveForecastWithBestConditions(forecasts);
        if (forecastWithBestConditions == null) {
            throw new WindsurfingLocationNotFoundException();
        }
        return forecastWithBestConditions;
    }

    private int resolveDaysToFetch(final LocalDate forecastDate) {
        return LocalDate.now().until(forecastDate).getDays() + 1;
    }

    private Forecast resolveForecastWithBestConditions(final List<Forecast> forecasts) {
        final var forecastsWithinWindAndTempRange = forecasts.stream().filter(forecast -> {
            final var windSpeed = forecast.windSpeed();
            final var avgTemperature = forecast.avgTemperature();
            return (windSpeed >= 5 && windSpeed <= 18) && (avgTemperature >= 5 && avgTemperature <= 35);
        }).toList();

        if (forecastsWithinWindAndTempRange.isEmpty()) {
            return null;
        }

        return forecastsWithinWindAndTempRange.stream()
                .max(Comparator.comparing(forecast -> forecast.windSpeed() * 3 + forecast.avgTemperature()))
                .orElse(null);
    }
}
