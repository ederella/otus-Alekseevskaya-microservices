package otus.microservice_location.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import otus.microservice_location.model.Location;
import otus.microservice_location.repository.LocationRepository;
import otus.microservice_location.service.LocationService;

@RestController
public class LocationController {
	
	private LocationService service;
	private LocationRepository repository;

	@Autowired
	LocationController(LocationRepository repository, LocationService service) {
		this.repository = repository;
		this.service = service;
	}
	
	@GetMapping(value = "/location/{clientIp}/get")
	public Location getLocation(@PathVariable String clientIp) {

		Location location = repository.findFirstByClientIp(clientIp);
		if (location == null) {
			try {
				location = service.getRemoteLocation(clientIp);
				repository.save(location);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return location;
	}
}
