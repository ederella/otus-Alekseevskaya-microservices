package otus.microservice_picture.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import otus.microservice_picture.service.PictureService;


@Controller
public class PictureController {

	private PictureService service;
	private HttpServletRequest request;
	private String weatherPicture;
	
	@Autowired
	public void setRequest(HttpServletRequest request, PictureService servise) {
		this.request = request;
		this.service = servise;
	}

	@GetMapping("/")
    public String indexPage() {
		String clientIp = service.getClientIp(request);
		String temperature = service.getWeatherByIp(clientIp);
		weatherPicture = selectWeatherPicture(temperature);
        return "index";
    }
	
	private String selectWeatherPicture(String temperature) {
		Double temp = new Double(temperature);
		if (temp > 15d) {
			return "/images/cat_hot.jpg";
		} else if (temp < 0d) {
			return "/images/cat_cold.jpg";
		} else
			return "/images/cat_warm.jpg";
	}

	@GetMapping("/get-cat")
	public @ResponseBody byte[] getImage() throws IOException {
	    InputStream in = getClass().getResourceAsStream(weatherPicture);
	    return IOUtils.toByteArray(in);
	}

}
