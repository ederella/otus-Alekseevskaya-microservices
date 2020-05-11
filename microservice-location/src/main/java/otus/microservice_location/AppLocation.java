package otus.microservice_location;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc
@EnableEurekaClient
@EnableCircuitBreaker
public class AppLocation 
{
    public static void main( String[] args ){
    	SpringApplication.run(AppLocation.class, args);
    }
}