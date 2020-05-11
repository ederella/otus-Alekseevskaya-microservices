package otus.microservice_weather.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "weather")
public class Weather {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String latitude;
	@Column
	private String longitude;
	@Column
	private LocalDateTime date;
	@Column
	private String temperature;

	public Weather(String latitude, String longitude, LocalDateTime date, String temperature) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.date = date;
		this.temperature = temperature;
	}
}
