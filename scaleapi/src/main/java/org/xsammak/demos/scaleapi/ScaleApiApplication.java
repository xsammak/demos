package org.xsammak.demos.scaleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class to run the Spring Boot application.
 *
 * @author xsammak
 */
@SpringBootApplication
@SuppressWarnings({"hideutilityclassconstructor", "squid:S1118"}) // not an utily class!
public class ScaleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScaleApiApplication.class, args);
    }
}
