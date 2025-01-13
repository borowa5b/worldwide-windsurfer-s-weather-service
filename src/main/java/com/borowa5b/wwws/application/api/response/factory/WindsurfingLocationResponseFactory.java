package com.borowa5b.wwws.application.api.response.factory;

import com.borowa5b.wwws.application.api.response.WindsurfingLocationResponse;
import com.borowa5b.wwws.domain.vo.Forecast;

public class WindsurfingLocationResponseFactory {

    public static WindsurfingLocationResponse create(final Forecast forecast) {
        if (forecast == null) {
            return null;
        }

        final var city = forecast.city();
        return new WindsurfingLocationResponse(
                city.name(),
                city.country(),
                forecast.forecastDate(),
                city.latitude(),
                city.longitude(),
                forecast.avgTemperature(),
                forecast.windSpeed()
        );
    }
}
