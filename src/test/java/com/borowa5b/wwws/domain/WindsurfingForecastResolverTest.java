package com.borowa5b.wwws.domain;

import com.borowa5b.wwws.domain.enumeration.City;
import com.borowa5b.wwws.domain.exception.WindsurfingLocationNotFoundException;
import com.borowa5b.wwws.domain.vo.Forecast;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WindsurfingForecastResolverTest {

    @Mock
    private ForecastFetcher forecastFetcher;

    @InjectMocks
    private WindsurfingForecastResolver windsurfingForecastResolver;

    @Test
    void shouldNotResolveForecast() {
        // given
        final var localDate = LocalDate.now().plusDays(1);
        when(forecastFetcher.fetch(eq(2), eq(localDate), any())).thenReturn(new Forecast(City.BRIDGETOWN, localDate, 1, 1));

        // when
        final var result = catchThrowable(() -> windsurfingForecastResolver.resolve(localDate));

        // then
        verify(forecastFetcher, times(City.values().length)).fetch(eq(2), eq(localDate), any());
        verifyNoMoreInteractions(forecastFetcher);

        assertThat(result).isExactlyInstanceOf(WindsurfingLocationNotFoundException.class);
    }

    @Test
    void shouldResolveForecastWithSingleForecastMeetingTempAndWindCriteria() {
        // given
        final var localDate = LocalDate.now().plusDays(1);
        final var expectedForecast = new Forecast(City.BRIDGETOWN, localDate, 15, 15);
        when(forecastFetcher.fetch(eq(2), eq(localDate), any())).thenReturn(expectedForecast);

        // when
        final var result = windsurfingForecastResolver.resolve(localDate);

        // then
        verify(forecastFetcher, times(City.values().length)).fetch(eq(2), eq(localDate), any());
        verifyNoMoreInteractions(forecastFetcher);

        assertThat(result).isNotNull().isEqualTo(expectedForecast);
    }

    @Test
    void shouldResolveForecastWithMultipleForecastsMeetingTempAndWindCriteria() {
        // given
        final var localDate = LocalDate.now().plusDays(1);
        final var expectedForecast = new Forecast(City.BRIDGETOWN, localDate, 15, 15);
        when(forecastFetcher.fetch(eq(2), eq(localDate), any())).thenReturn(expectedForecast, new Forecast(City.PISSOURI, localDate, 10, 10));

        // when
        final var result = windsurfingForecastResolver.resolve(localDate);

        // then
        verify(forecastFetcher, times(City.values().length)).fetch(eq(2), eq(localDate), any());
        verifyNoMoreInteractions(forecastFetcher);

        assertThat(result).isNotNull().isEqualTo(expectedForecast);
    }
}