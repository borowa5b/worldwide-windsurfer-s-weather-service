package com.borowa5b.wwws.domain.vo;

import com.borowa5b.wwws.domain.enumeration.City;

import java.time.LocalDate;

public record Forecast(
        City city,
        LocalDate forecastDate,
        float avgTemperature,
        float windSpeed
) {
}
