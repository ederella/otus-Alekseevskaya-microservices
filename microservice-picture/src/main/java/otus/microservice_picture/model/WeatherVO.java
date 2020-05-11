package otus.microservice_picture.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherVO {

	private String latitude;
	private String longitude;
	private LocalDateTime date;
	private String temperature;
}
