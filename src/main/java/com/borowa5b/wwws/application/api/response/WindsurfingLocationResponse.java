package com.borowa5b.wwws.application.api.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record WindsurfingLocationResponse(
        @Schema(description = "Windsurfing city", example = "JASTARNIA")
        String city,
        @Schema(description = "Windsurfing country code", example = "PL")
        String countryCode,
        @Schema(description = "Windsurfing location forecast date", example = "2022-01-12")
        LocalDate forecastDate,
        @Schema(description = "Windsurfing location latitude", example = "-20.44509")
        float latitude,
        @Schema(description = "Windsurfing location latitude", example = "56.44509")
        float longitude,
        @Schema(description = "Windsurfing location average temperature in Celsius", example = "24.2")
        float avgTemperature,
        @Schema(description = "Windsurfing location wind speed in m/s", example = "5.9")
        float windSpeed
) {
}
