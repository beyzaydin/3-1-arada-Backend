package winx.bitirme.config;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    String host = "localhost";

    String userName = "setUsernameHere";

    String password = "setPasswordHere";

    String port = "27017";


    public @Bean
    MongoClient mongoClient(){
        return MongoClients.create(String.format("mongodb://%s:%s@%s:%s",userName,password,host,port));
    }
}
