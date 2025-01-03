package com.borowa5b.wwws.application.api;

import com.borowa5b.wwws.IntegrationTest;
import com.borowa5b.wwws.domain.enumeration.City;
import com.borowa5b.wwws.domain.exception.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@IntegrationTest
class GetWindsurfingLocationEndpointIT {

    @Autowired
    private GetWindsurfingLocationEndpoint endpoint;

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
    void shouldReturnWindsurfingLocation() throws IOException {
        // given
        final var forecastDate = LocalDate.now().plusDays(1);
        final var weatherBitResponse = new String(getClass().getClassLoader().getResourceAsStream("response/WeatherBitResponse.json").readAllBytes())
                .replace("{date1}", LocalDate.now().toString())
                .replace("{date2}", LocalDate.now().plusDays(1).toString());
        weatherBitServer.expect(ExpectedCount.times(City.values().length), request -> assertThat("/forecast/daily").isEqualTo(request.getURI().getPath()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(weatherBitResponse, MediaType.APPLICATION_JSON));

        // when
        final var result = endpoint.getWindsurfingLocation(forecastDate.toString());

        // then
        weatherBitServer.verify();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    void shouldReturnExceptionWhenDateIsInvalid() {
        // given

        // when
        final var result = catchThrowable(() -> endpoint.getWindsurfingLocation(null));

        // then
        assertThat(result).isExactlyInstanceOf(ValidationException.class);
    }
}