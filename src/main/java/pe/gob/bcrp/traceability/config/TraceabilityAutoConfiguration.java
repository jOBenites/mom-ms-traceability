package pe.gob.bcrp.traceability.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pe.gob.bcrp.traceability.repository.TraceabilityEventoRepository;
import pe.gob.bcrp.traceability.service.ITraceabilityService;
import pe.gob.bcrp.traceability.service.Impl.TraceabilityServiceImpl;

@AutoConfiguration
@ConditionalOnClass({ITraceabilityService.class})
@ConditionalOnProperty(prefix = "traceability", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(TraceabilityProperties.class)
@ComponentScan(basePackages = "pe.gob.bcrp.traceability")
@EntityScan(basePackages = "pe.gob.bcrp.traceability.model")
@EnableJpaRepositories(basePackages = "pe.gob.bcrp.traceability.repository")
public class TraceabilityAutoConfiguration {

    private final UsernameContext usernameContext;
    public TraceabilityAutoConfiguration(UsernameContext usernameContext) {
        this.usernameContext = usernameContext;
    }

    @Bean
    @ConditionalOnMissingBean
    public ITraceabilityService traceabilityService(TraceabilityEventoRepository repository) {
        return new TraceabilityServiceImpl(repository, usernameContext);
    }
}
