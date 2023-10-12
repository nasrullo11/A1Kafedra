package uz.istart.kafedra.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

/**
 * Created by babayev.xushnud@gmail.com on 2/23/2021.
 * Project kafedra-app
 */

@EntityScan(value = {"uz.istart.kafedra.core.entities"})
@EnableJpaRepositories(value = {"uz.istart.kafedra.core.repositories"})
@SpringBootApplication(scanBasePackages = "uz.istart.kafedra.api")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
