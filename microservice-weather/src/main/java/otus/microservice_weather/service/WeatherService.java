package otus.microservice_weather.service;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import otus.microservice_weather.feign.LocationServiceProxy;
import otus.microservice_weather.model.LocationVO;
import otus.microservice_weather.model.Weather;

@Service
@SuppressWarnings("unchecked")
public class WeatherService {

	@Autowired
	LocationServiceProxy feignClient;
	
	@HystrixCommand(commandKey="getRentsKey", fallbackMethod="buildFallbackRents")
	public Weather getRemoteWeatherInfo(String latitude, String longitude) {

		Weather weather = null;

		try {
			RestTemplate restTemplate = new RestTemplate();

			Map<String, String> urlPathVariables = new HashMap<>();
			urlPathVariables.put("coordinates", latitude + "," + longitude);
			String url = "http://api.weatherapi.com/v1/current.json?key=810ae2f082a645a9b8b204142201005&q={coordinates}";

			String json = restTemplate.getForEntity(url, String.class, urlPathVariables).getBody();
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> map = objectMapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
			});

			map = (HashMap<String, Object>) map.get("current");

			Double tempC = (Double) map.get("temp_c");
			weather = new Weather(latitude, longitude, LocalDateTime.now(), tempC.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return weather;
	}
	
	public Weather buildFallbackRents(String latitude, String longitude) {
		Weather weather = new Weather();
		weather.setDate(LocalDateTime.now());
		weather.setId(0L);
		weather.setLatitude(latitude);
		weather.setLongitude(longitude);
		weather.setTemperature("-273.15");
		return weather;
	}
	@HystrixCommand(commandKey="getRentsKey", fallbackMethod="buildFallbackRentsLocation")
	public LocationVO getLocation(String clientIp) {		
		return feignClient.getLocation(clientIp);
	}
	
	public LocationVO buildFallbackRentsLocation(String clientIp) {	
		LocationVO location = new LocationVO();
		location.setCityName("N/A");
		location.setClientIp(clientIp);
		location.setContinentCode("N/A");
		location.setCountryCode("N/A");
		location.setLat("0");
		location.setLng("0");
		location.setDate(new Date());
		location.setTz("N/A");
		return location;

	}
}
