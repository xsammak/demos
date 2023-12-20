# Scale REST API demo

Simple Spring Boot application with two endpoints, one to import data and other to read it using filters.

## Building and running

````
mvn clean install && java -jar target/scaleapi.jar
````
Uses port 8080 by default. Swagger-UI is also enabled: http://localhost:8080/swagger-ui.html

Unit test coverage report: target/site/jacoco/index.html

## Some notes about the implementation

- Supports extending solution with new weigth units (now kg, ton and pound). Conversion rules taken from the internet :)
- Units should be better defined (e.g. is ton metric ton, short ton or long ton).
- Accuracy of weights, can we assume one kg accuracy?
- Validation rules for the data, how long can scale ID be etc.
- Time does not have time zone information, so we must assume that local time is used and that application entering the data has converted times to local time of the backend service.
- Using Spring Boot provided error handling, custom one would be easy to implement but did not have time for that and default handler is OK.
- More test cases should be added (now testing just the success case).
