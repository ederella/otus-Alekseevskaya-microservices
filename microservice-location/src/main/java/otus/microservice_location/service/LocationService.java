package otus.microservice_location.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import otus.microservice_location.model.Location;

@Service
public class LocationService {
	@HystrixCommand(commandKey="getRentsKey", fallbackMethod="buildFallbackRents")
	public Location getRemoteLocation(String clientIp) throws IOException, JsonParseException, JsonMappingException {
		Location location;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-rapidapi-host", "fastah-ip.p.rapidapi.com");
		headers.add("x-rapidapi-key", "ac33a235b1msh23e106e4ec03a29p19afeejsn754f38f2e6e7");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		String url = "https://fastah-ip.p.rapidapi.com/whereis/v1/json/" + clientIp;
		String json = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Location> map = new HashMap<String, Location>();
		map = objectMapper.readValue(json, new TypeReference<HashMap<String, Location>>() {});
		location = map.get("locationData");
		location.setClientIp(clientIp);
		location.setDate(new Date());
		return location;
	}
	public Location buildFallbackRents(String clientIp) {
		Location location = new Location();
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
