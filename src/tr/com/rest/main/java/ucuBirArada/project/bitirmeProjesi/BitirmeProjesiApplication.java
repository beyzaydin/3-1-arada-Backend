package ucuBirArada.project.bitirmeProjesi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories
@SpringBootApplication(scanBasePackages = {"tr.com.rest.btr", "tr.com.rest.main"})
public class BitirmeProjesiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BitirmeProjesiApplication.class, args);
    }

}
