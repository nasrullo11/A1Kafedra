package uz.istart.kafedra.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(value = {"uz.istart.kafedra.core.entities"})
@EnableJpaRepositories(value = {"uz.istart.kafedra.core.repositories"})
@SpringBootApplication(scanBasePackages = "uz.istart.kafedra.admin")
public class AdminWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminWebApplication.class, args);
	}

}
