package otus.microservice_weather.model;

import java.util.Date;

import javax.persistence.Column;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationVO {

	private String clientIp;
	private String cityName;
	private String continentCode;
	private String countryCode;
	private String countryName;
	private String lat;
	private String lng;
	private String tz;
	private Date date;
}
