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
                city,
                city.getCountry(),
                forecast.forecastDate(),
                city.getLatitude(),
                city.getLongitude(),
                forecast.avgTemperature(),
                forecast.windSpeed()
        );
    }
}
