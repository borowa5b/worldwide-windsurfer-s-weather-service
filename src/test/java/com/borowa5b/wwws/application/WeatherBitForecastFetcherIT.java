package com.borowa5b.wwws.application;

import com.borowa5b.wwws.IntegrationTest;
import com.borowa5b.wwws.domain.enumeration.City;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@IntegrationTest
class WeatherBitForecastFetcherIT {

    @Autowired
    private WeatherBitForecastFetcher weatherBitForecastFetcher;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer weatherBitServer;

    @BeforeEach
    void setUp() {
        weatherBitServer = MockRestServiceServer.createServer(restTemplate);
    }

    @AfterEach
    void tearDown() {
        weatherBitServer.reset();
    }

    @Test
    void shouldFetchForecast() throws IOException {
        // given
        final var daysToFetch = 2;
        final var city = City.JASTARNIA;
        final var expectedDate = LocalDate.now().plusDays(1);
        final var weatherBitResponse = new String(getClass().getClassLoader().getResourceAsStream("response/WeatherBitResponse.json").readAllBytes())
                .replace("{date1}", LocalDate.now().toString())
                .replace("{date2}", expectedDate.toString());
        weatherBitServer.expect(requestTo("http://localhost:8102/forecast/daily?key=123&days=2&lat=54.69606&lon=18.67873&country=PL"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(weatherBitResponse, MediaType.APPLICATION_JSON));

        // when
        final var result = weatherBitForecastFetcher.fetch(daysToFetch, expectedDate, city);

        // then
        weatherBitServer.verify();
        assertThat(result.forecastDate()).isEqualTo(expectedDate);
    }
}