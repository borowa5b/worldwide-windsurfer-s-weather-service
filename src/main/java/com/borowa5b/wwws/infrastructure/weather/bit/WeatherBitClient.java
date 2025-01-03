package com.borowa5b.wwws.infrastructure.weather.bit;

import com.borowa5b.wwws.domain.enumeration.City;
import com.borowa5b.wwws.infrastructure.weather.bit.response.ForecastResponse;

public interface WeatherBitClient {

    ForecastResponse getForecast(final int daysToFetch, final City city);
}
