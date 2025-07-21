package pe.gob.bcrp.traceability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages = "pe.gob.bcrp.traceability")
public class MomMsTraceabilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MomMsTraceabilityApplication.class, args);
    }

}
