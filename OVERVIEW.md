Movies API Challenge

Overview

This project is a Spring Boot application that fetches movie data from an external API and provides a REST endpoint to retrieve the names of directors who directed more movies than a given threshold. The returned director names are sorted alphabetically.

API to fetch data from:
https://eron-movies.wiremockapi.cloud/api/movies/search?page=<pageNumber>

REST Endpoint provided by this application:
GET /movies-api/directors?threshold=X

{
    "directors": [
    "Martin Scorsese",
    "Woody Allen"
    ]
}

Features Implemented

1.	Data Loading from External API:
- On startup, the application fetches all movie data from the external API by iterating through all available pages.
The data is then stored in an in-memory H2 database.
2.	Data Persistence with H2:
- I used an H2 in-memory database to store movie and director information.
3.	Querying Directors Above Threshold:
- The service implements List<String> getDirectors(int threshold) to return directors who have directed more movies than the given threshold.
Results are alphabetically sorted.
4.	Error Handling and Global Exception Handling:
- InvalidThresholdException: Thrown when the threshold parameter is negative, and there could be added as many validations as needed.
- A GlobalExceptionHandler provides standardized JSON error responses for these exceptions.
5.	AOP-based Parameter Validation:
- I implemented a custom DirectorValidatorAspect that intercepts calls to the /movies-api/directors endpoint, ensuring that the threshold parameter is valid.
If the parameter is invalid, it throws InvalidThresholdException.
6.	OpenAPI/Swagger Documentation:
- I integrated Springdoc OpenAPI to generate OpenAPI specifications. This provides a UI at /swagger-ui.html and JSON specs at /v3/api-docs.
The DirectorController endpoint is annotated with @Operation and @Parameter to describe the endpoint and its parameters.
7.	Logging:
- Logging statements are included at various points (DirectorController, DataLoader and DirectorValidatorAspect) to facilitate debugging.
You can adjust log levels using application.properties.
8.	Configuration via Properties Files:
- application.properties for main configuration.
- External API base URL is injected via @Value and can be easily changed without modifying the code.

Testing

I included:
- Unit tests for DirectorService with JUnit and Mockito.
- Integration test for `/movies-api/directors` endpoint

Swagger UI

Once the application is running:
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- Swagger UI: http://localhost:8080/swagger-ui.html

How to test?
- Start up the application from any IDE such as intellij with jdk 11 configured
- You can test it from here http://localhost:8080/swagger-ui/index.html#/director-controller/getDirectors
- You can test it executing this command on the terminal: `curl -X GET "http://localhost:8080/movies-api/directors?threshold=2" -H "Accept: application/json"`
- You can test it running the testcases from the IDE

Improvements Considered (Bonus)

- Caching: I considered using caching mechanisms, but decided to store data in H2 as a form of rapid in-memory storage for this exercise.
- Resilience Patterns: Could be added (like Circuit Breakers or Retries) to handle API failures more gracefully.