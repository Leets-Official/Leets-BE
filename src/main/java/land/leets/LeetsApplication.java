package land.leets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LeetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeetsApplication.class, args);
	}

}
