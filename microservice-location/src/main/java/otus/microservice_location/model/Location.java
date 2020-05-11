package otus.microservice_location.model;

import java.util.Date;

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
@Entity(name = "location")
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String clientIp;
	@Column
	private String cityName;
	@Column
	private String continentCode;
	@Column
	private String countryCode;
	@Column
	private String countryName;
	@Column
	private String lat;
	@Column
	private String lng;
	@Column
	private String tz;
	@Column
	private Date date;
}
