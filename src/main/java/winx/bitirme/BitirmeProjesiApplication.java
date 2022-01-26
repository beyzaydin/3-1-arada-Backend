package winx.bitirme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "winx.bitirme")
public class BitirmeProjesiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BitirmeProjesiApplication.class, args);
    }

}
