package com.borowa5b.wwws.infrastructure.weather.bit.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ForecastResponse(
        String cityName,
        String countryCode,
        ForecastDataResponse[] data
) {
}
