import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

@Configuration
@EnableAutoConfiguration
@EntityScan({"org.test.web.domain"})
@EnableJpaRepositories({"org.test.web.repo"})
@ComponentScan({"org.test"})
@EnableAspectJAutoProxy
@SpringBootApplication
public class BootStrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootStrapApplication.class, args);
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }


}