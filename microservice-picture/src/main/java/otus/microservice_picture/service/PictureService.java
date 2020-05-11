package otus.microservice_picture.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import otus.microservice_picture.feing.WeatherServiceProxy;
import otus.microservice_picture.model.WeatherVO;

@Service
public class PictureService {
	@Autowired
	WeatherServiceProxy feignClient;
	@HystrixCommand(commandKey="getRentsKey", fallbackMethod="buildFallbackRentsIP")
	public String getClientIp(HttpServletRequest request) {
		String remoteAddr = "";

		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || remoteAddr.equalsIgnoreCase("")) {
				remoteAddr = request.getRemoteAddr();
			}
			if (isLocalHost(remoteAddr)) {
				RestOperations rest = new RestTemplate();
				remoteAddr = rest.getForObject("http://checkip.amazonaws.com", String.class).replaceAll("\n", "");
			}
		}
		return remoteAddr;
	}
	private boolean isLocalHost(String remoteAddr) {
		return (remoteAddr != null) && remoteAddr.matches("127.0.0.1|0:0:0:0:0:0:0:1");
	}
	//@HystrixCommand(commandKey="getRentsKey", fallbackMethod="buildFallbackRentsWeather")
	public String getWeatherByIp(String clientIp) {
		return feignClient.getWeather(clientIp).getTemperature();
	/*	Application app = eurekaClient.getApplication("microservice-weather");
        List<InstanceInfo> instances = app.getInstances();

        String serviceUri = String.format("%sweather/{clientIp}/get", instances.get(0).getHomePageUrl());
        
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> urlPathVariables = new HashMap<>();
		urlPathVariables.put("clientIp", clientIp);

		ResponseEntity<WeatherVO> responseEntity = restTemplate
				.getForEntity(serviceUri, WeatherVO.class, urlPathVariables);
		WeatherVO weatherVO = responseEntity.getBody();
		return weatherVO.getTemperature();*/

	}
	
	public String buildFallbackRentsIP(HttpServletRequest request) {
		return "87.250.250.242";
	}
	public String buildFallbackRentsWeather(String clientIp) {
		return "-273.15";
	}
}
