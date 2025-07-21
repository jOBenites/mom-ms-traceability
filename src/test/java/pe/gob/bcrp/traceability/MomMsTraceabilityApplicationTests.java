package pe.gob.bcrp.traceability;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import pe.gob.bcrp.traceability.service.ITraceabilityService;

@SpringBootTest(classes = MomMsTraceabilityApplication.class)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/multiupi",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "traceability.enabled=true"
})
class MomMsTraceabilityApplicationTests {

    @Autowired
    private ITraceabilityService traceabilityService;

    @Test
    void contextLoads() {
        assert traceabilityService != null;
    }

    @Test
    void testLogSuccess() {
        var response = traceabilityService.logSuccess("TEST_EVENT", "PROC-001", "Test details");
        assert response != null;
        assert response.tipoEvento().equals("TEST_EVENT");
        assert response.estado().equals("OK");
    }

}
