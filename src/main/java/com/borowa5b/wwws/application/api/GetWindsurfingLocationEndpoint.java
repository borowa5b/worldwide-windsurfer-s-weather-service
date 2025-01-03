package com.borowa5b.wwws.application.api;

import com.borowa5b.wwws.application.api.response.WindsurfingLocationResponse;
import com.borowa5b.wwws.application.api.response.factory.WindsurfingLocationResponseFactory;
import com.borowa5b.wwws.domain.WindsurfingForecastResolver;
import com.borowa5b.wwws.domain.exception.ValidationException;
import com.borowa5b.wwws.domain.exception.validation.AggregatingValidationExceptionHandler;
import com.borowa5b.wwws.domain.exception.validation.Validator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Tag(name = "windsurfing")
public class GetWindsurfingLocationEndpoint {

    private final WindsurfingForecastResolver calculator;

    @Operation(summary = """
            Gets information about best place for windsurfing given planned date withing 16 days\s
            period starting from current day.\
            Date format: ISO-8601, e.g. 2025-01-14.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Windsurfing location determined successfully",
                    content = @Content(schema = @Schema(implementation = WindsurfingLocationResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation errors occurred"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Windsurfing location not found"
            )
    })
    @GetMapping(value = "/windsurfing/{forecastDate}/location", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<WindsurfingLocationResponse> getWindsurfingLocation(
            @PathVariable
            @Parameter(description = "Planned windsurfing date in ISO-8601 format", required = true, example = "2025-01-12") final String forecastDate
    ) {
        validate(forecastDate);

        final var forecast = calculator.resolve(LocalDate.parse(forecastDate));
        final var windsurfingLocationResponse = WindsurfingLocationResponseFactory.create(forecast);
        return ResponseEntity.ok(windsurfingLocationResponse);
    }

    private void validate(final String forecastDate) {
        final var exceptionHandler = new AggregatingValidationExceptionHandler();

        Validator.isNotNull(forecastDate, "forecastDate", exceptionHandler);
        Validator.isCorrectLocalDate(forecastDate, "forecastDate", exceptionHandler);
        Validator.isWithinDaysPeriodInFuture(forecastDate, "forecastDate", 16, exceptionHandler);

        if (exceptionHandler.hasErrors()) {
            throw new ValidationException(exceptionHandler.getErrors());
        }
    }
}
