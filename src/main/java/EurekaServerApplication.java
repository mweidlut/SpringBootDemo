import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;


//@SpringCloudApplication
@ComponentScan({"org.test"})
@EnableEurekaServer
@SpringBootApplication
//@EnableDiscoveryClient
//@EnableCircuitBreaker
public class EurekaServerApplication {

    public static void main(String[] args) {

        //SpringApplication.run(EurekaServerApplication.class, args);
        new SpringApplicationBuilder(EurekaServerApplication.class).web(true).run(args);
    }
}
