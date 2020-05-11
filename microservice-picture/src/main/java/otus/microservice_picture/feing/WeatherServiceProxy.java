package otus.microservice_picture.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import otus.microservice_picture.model.WeatherVO;

@FeignClient(name = "microservice-weather")
public interface WeatherServiceProxy {
	@GetMapping(value = "weather/{clientIp}/get")
	public WeatherVO getWeather(@PathVariable String clientIp);
}
