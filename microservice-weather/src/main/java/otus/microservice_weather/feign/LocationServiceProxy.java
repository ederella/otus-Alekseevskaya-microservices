package otus.microservice_weather.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import otus.microservice_weather.model.LocationVO;

@FeignClient(name="microservice-location")
public interface LocationServiceProxy {

	@GetMapping(value = "/location/{clientIp}/get")
	public LocationVO getLocation(@PathVariable String clientIp);
}
