package com.borowa5b.wwws.application;

import com.borowa5b.wwws.domain.vo.City;
import com.borowa5b.wwws.infrastructure.configuration.CitiesConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultCitiesResolverTest {

    @Mock
    private CitiesConfiguration citiesConfiguration;

    @Test
    void shouldResolveCities() {
        // given
        final var resolver = new DefaultCitiesResolver(citiesConfiguration);
        final var city = new City("ZIELONA_GORA", 51.94260f, 15.53794f, "PL");
        final var cities = new City[]{city};
        when(citiesConfiguration.getCities()).thenReturn(cities);

        // when
        final var result = resolver.resolve();

        // then
        assertThat(result)
                .hasSize(1)
                .contains(city);
    }

}