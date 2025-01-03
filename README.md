# Worldwide Windsurfer’s Weather Service

#### Author: Mateusz Borowski <[borowa5b@gmail.com](borowa5b@gmail.com)>

### Tech stack:
- Spring Boot 3.4.1
- JDK 21 LTS
- Gradle 8.11.1

### Summary
This microservice provides data about best location for windsurfing for given date based on data from WeatherBit (https://www.weatherbit.io).

### Starting application
To run the application, follow these steps:

1. Install JDK 21 and set it as your default Java version.
2. Clone the latest version of this repository.
3. Open project using IDE of your choice.
4. Replace value `TO_BE_FILLED_BY_DEVELOPER` with WeatherBit api key in `application-local.properties` file.
5. Run project using gradle `bootRun` task.
6. Application will be available at port `8101`. Swagger documentation will be available at: http://localhost:8101/swagger-ui/index.html.

### Building
To build the application follow the [Starting application](#starting-application) section and run `clean build` gradle task instead of running application.