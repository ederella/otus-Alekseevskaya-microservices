package otus.microservice_weather.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import otus.microservice_weather.feign.LocationServiceProxy;
import otus.microservice_weather.model.LocationVO;
import otus.microservice_weather.model.Weather;
import otus.microservice_weather.repository.WeatherRepository;
import otus.microservice_weather.service.WeatherService;

@RestController
public class WeatherController {
	
	private WeatherRepository repository;
	private WeatherService service;

	@Autowired
	WeatherController(WeatherRepository repository, WeatherService service){
		this.repository = repository;
		this.service = service;
	}
	
	@GetMapping(value = "/weather/{clientIp}/get")
	public Weather getWeather(@PathVariable String clientIp) {
		
		LocationVO coordinates = service.getLocation(clientIp);
		
		String latitude = coordinates.getLat();
		String longitude = coordinates.getLng();
		
		Weather weather = repository.findByLatitudeAndLongitude(latitude, longitude);
		
		if (weather == null || weather.getDate().isBefore(LocalDateTime.now().minusHours(1))) {
			
			weather = service.getRemoteWeatherInfo(latitude, longitude);
			
			if (weather != null)
				repository.save(weather);
		}
		return weather;
	}
}
