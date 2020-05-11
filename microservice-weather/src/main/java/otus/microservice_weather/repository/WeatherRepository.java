package otus.microservice_weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import otus.microservice_weather.model.Weather;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

	Weather findByLatitudeAndLongitude(String latitude, String longitude);
}
