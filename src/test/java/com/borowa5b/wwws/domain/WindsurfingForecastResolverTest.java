package com.borowa5b.wwws.domain;

import com.borowa5b.wwws.domain.exception.WindsurfingLocationNotFoundException;
import com.borowa5b.wwws.domain.vo.City;
import com.borowa5b.wwws.domain.vo.Forecast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WindsurfingForecastResolverTest {

    @Mock
    private ForecastFetcher forecastFetcher;

    @Mock
    private CitiesResolver citiesResolver;

    @InjectMocks
    private WindsurfingForecastResolver windsurfingForecastResolver;

    @BeforeEach
    void setUp() {
        final var city = new City("ZIELONA_GORA", 51.94260f, 15.53794f, "PL");
        final var city2 = new City("ZIELONA_GORA", 51.94260f, 15.53794f, "PL");
        when(citiesResolver.resolve()).thenReturn(List.of(city, city2));
    }

    @Test
    void shouldNotResolveForecast() {
        // given
        final var localDate = LocalDate.now().plusDays(1);
        final var city = new City("ZIELONA_GORA", 51.94260f, 15.53794f, "PL");
        when(forecastFetcher.fetch(eq(2), eq(localDate), any())).thenReturn(new Forecast(city, localDate, 1, 1));

        // when
        final var result = catchThrowable(() -> windsurfingForecastResolver.resolve(localDate));

        // then
        verify(forecastFetcher, times(citiesResolver.resolve().size())).fetch(eq(2), eq(localDate), any());
        verifyNoMoreInteractions(forecastFetcher);

        assertThat(result).isExactlyInstanceOf(WindsurfingLocationNotFoundException.class);
    }

    @Test
    void shouldResolveForecastWithSingleForecastMeetingTempAndWindCriteria() {
        // given
        final var localDate = LocalDate.now().plusDays(1);
        final var city = new City("ZIELONA_GORA", 51.94260f, 15.53794f, "PL");
        final var expectedForecast = new Forecast(city, localDate, 15, 15);
        when(forecastFetcher.fetch(eq(2), eq(localDate), any())).thenReturn(expectedForecast);

        // when
        final var result = windsurfingForecastResolver.resolve(localDate);

        // then
        verify(forecastFetcher, times(citiesResolver.resolve().size())).fetch(eq(2), eq(localDate), any());
        verifyNoMoreInteractions(forecastFetcher);

        assertThat(result).isNotNull().isEqualTo(expectedForecast);
    }

    @Test
    void shouldResolveForecastWithMultipleForecastsMeetingTempAndWindCriteria() {
        // given
        final var localDate = LocalDate.now().plusDays(1);
        final var city = new City("ZIELONA_GORA", 51.94260f, 15.53794f, "PL");
        final var city2 = new City("ZIELONA_GORA2", 51.94260f, 15.53794f, "PL");
        final var expectedForecast = new Forecast(city, localDate, 15, 15);
        when(forecastFetcher.fetch(eq(2), eq(localDate), any())).thenReturn(expectedForecast, new Forecast(city2, localDate, 10, 10));

        // when
        final var result = windsurfingForecastResolver.resolve(localDate);

        // then
        verify(forecastFetcher, times(citiesResolver.resolve().size())).fetch(eq(2), eq(localDate), any());
        verifyNoMoreInteractions(forecastFetcher);

        assertThat(result).isNotNull().isEqualTo(expectedForecast);
    }
}