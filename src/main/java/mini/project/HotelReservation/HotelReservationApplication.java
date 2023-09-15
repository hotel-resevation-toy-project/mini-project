package mini.project.HotelReservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //하위 클래스에서 Entity Listener 를 체크
@SpringBootApplication
public class HotelReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelReservationApplication.class, args);
	}

}
