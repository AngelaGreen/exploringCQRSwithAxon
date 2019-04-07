package exploringaxon;

import org.axonframework.config.EventProcessingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExploringAxonApplication {

    @Autowired
    public void configure(EventProcessingConfiguration configuration) {
        configuration.usingTrackingProcessors();
    }

    public static void main(String[] args) {
        SpringApplication.run(ExploringAxonApplication.class, args);
    }
}
