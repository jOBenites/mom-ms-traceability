package pe.gob.bcrp.traceability.service;

import org.springframework.scheduling.annotation.Async;
import pe.gob.bcrp.traceability.model.dto.TraceabilityEventoRequest;
import pe.gob.bcrp.traceability.model.dto.TraceabilityEventoResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ITraceabilityService {
    TraceabilityEventoResponse logEvent(TraceabilityEventoRequest request);
    @Async
    CompletableFuture<TraceabilityEventoResponse> logEventAsync(TraceabilityEventoRequest request);
    TraceabilityEventoResponse logSuccess(String eventType, String processId, String details);
    TraceabilityEventoResponse logError(String eventType, String processId, String details);
    TraceabilityEventoResponse logUserAction(String eventType, String processId, String userId, String details);
    List<TraceabilityEventoResponse> getEventsByProcessId(String processId);
    List<TraceabilityEventoResponse> getEventsByType(String eventType);
    List<TraceabilityEventoResponse> getEventsByStatus(String status);
    List<TraceabilityEventoResponse> getEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<TraceabilityEventoResponse> getProcessTraceability(String processId, LocalDateTime startDate, LocalDateTime endDate);
}
