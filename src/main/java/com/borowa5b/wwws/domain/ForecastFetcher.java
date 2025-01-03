package com.borowa5b.wwws.domain;

import com.borowa5b.wwws.domain.enumeration.City;
import com.borowa5b.wwws.domain.vo.Forecast;

import java.time.LocalDate;

public interface ForecastFetcher {

    Forecast fetch(final int daysToFetch, LocalDate forecastDate, final City city);
}
