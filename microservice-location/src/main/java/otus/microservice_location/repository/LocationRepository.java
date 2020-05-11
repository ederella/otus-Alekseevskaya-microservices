package otus.microservice_location.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import otus.microservice_location.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{

	Location findFirstByClientIp(String clientIp);

}
