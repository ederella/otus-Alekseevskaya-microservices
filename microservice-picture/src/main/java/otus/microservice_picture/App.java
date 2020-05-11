package otus.microservice_picture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc
@EnableCircuitBreaker
@EnableFeignClients(basePackages = "otus.microservice_picture.feing")
public class App 
{
    public static void main( String[] args ){
    	SpringApplication.run(App.class, args);
    }
   @LoadBalanced
   	@Bean
   	public RestTemplate getRestTemplate() {
   		return new RestTemplate();
   	}
}
