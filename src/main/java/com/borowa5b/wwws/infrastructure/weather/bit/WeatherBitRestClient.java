package com.borowa5b.wwws.infrastructure.weather.bit;

import com.borowa5b.wwws.domain.vo.City;
import com.borowa5b.wwws.infrastructure.weather.bit.configuration.WeatherBitProperties;
import com.borowa5b.wwws.infrastructure.weather.bit.response.ForecastResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class WeatherBitRestClient implements WeatherBitClient {

    private final WeatherBitProperties weatherBitProperties;
    private final RestTemplate restTemplate;

    public ForecastResponse getForecast(final int daysToFetch, final City city) {
        final var uriString = UriComponentsBuilder.fromUriString(weatherBitProperties.getBaseUrl())
                .path(weatherBitProperties.getForecastUrl())
                .queryParam("key", weatherBitProperties.getApiKey())
                .queryParam("days", daysToFetch)
                .queryParam("lat", city.latitude())
                .queryParam("lon", city.longitude())
                .queryParam("country", city.country())
                .toUriString();
        return restTemplate.getForObject(uriString, ForecastResponse.class);
    }
}
