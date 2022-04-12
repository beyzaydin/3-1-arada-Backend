package winx.bitirme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableMongoRepositories
@Configuration
@EnableScheduling
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BitirmeProjesiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BitirmeProjesiApplication.class, args);
    }

    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/sleep/deneme").allowedOrigins("http://localhost:3000");
            }
        };
    }
}
