package com.borowa5b.wwws.infrastructure.weather.bit.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ForecastDataResponse(
        LocalDate datetime,
        float temp,
        float windSpd
) {
}
