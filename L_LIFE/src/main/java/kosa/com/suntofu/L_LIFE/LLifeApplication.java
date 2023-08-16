package kosa.com.suntofu.L_LIFE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class LLifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LLifeApplication.class, args);
	}

}
