package pe.gob.bcrp.traceability.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.bcrp.traceability.config.TraceabilityProperties;
import pe.gob.bcrp.traceability.model.dto.TraceabilityEventoRequest;
import pe.gob.bcrp.traceability.model.dto.TraceabilityEventoResponse;
import pe.gob.bcrp.traceability.model.TraceabilityEvento;
import pe.gob.bcrp.traceability.repository.TraceabilityEventoRepository;
import pe.gob.bcrp.traceability.service.ITraceabilityService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class TraceabilityServiceImpl implements ITraceabilityService {
    @Value("${spring.application.name:unknown}")
    private String applicationName;

    private TraceabilityEventoRepository repository;
    private final TraceabilityProperties properties;

    public TraceabilityServiceImpl(TraceabilityEventoRepository repository) {
        this.repository = repository;
        this.properties = new TraceabilityProperties(); // Default properties
    }

    @Autowired(required = false)
    public void setProperties(TraceabilityProperties properties) {}

    @Override
    public TraceabilityEventoResponse logEvent(TraceabilityEventoRequest request) {
        TraceabilityEvento evento = createEvent(request);
        TraceabilityEvento savedEvent = repository.save(evento);
        return mapToResponse(savedEvent);
    }

    // Método asíncrono para alta concurrencia
    @Override
    @Async
    public CompletableFuture<TraceabilityEventoResponse> logEventAsync(TraceabilityEventoRequest request) {
        return CompletableFuture.completedFuture(logEvent(request));
    }

    // Métodos de conveniencia para uso directo
    @Override
    public TraceabilityEventoResponse logSuccess(String eventType, String processId, String details) {
        return logEvent(new TraceabilityEventoRequest(eventType, "OK", null, processId,
                "SYSTEM", details, applicationName));
    }
    @Override
    public TraceabilityEventoResponse logError(String eventType, String processId, String details) {
        return logEvent(new TraceabilityEventoRequest(eventType, "ERROR", null, processId,
                "SYSTEM", details, applicationName));
    }
    @Override
    public TraceabilityEventoResponse logUserAction(String eventType, String processId,
                                                   String userId, String details) {
        return logEvent(new TraceabilityEventoRequest(eventType, "OK", null, processId,
                userId, details, applicationName));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TraceabilityEventoResponse> getEventsByProcessId(String processId) {
        return repository.findByProcesoIdOrderByHoraDesc(processId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TraceabilityEventoResponse> getEventsByType(String eventType) {
        return repository.findByTipoEventoOrderByHoraDesc(eventType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TraceabilityEventoResponse> getEventsByStatus(String status) {
        return repository.findByEstadoOrderByHoraDesc(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TraceabilityEventoResponse> getEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findEventsBetweenDates(startDate, endDate)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TraceabilityEventoResponse> getProcessTraceability(String processId, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByProcessIdAndDateRange(processId, startDate, endDate)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TraceabilityEvento createEvent(TraceabilityEventoRequest request) {

        TraceabilityEvento event = new TraceabilityEvento(
                request.tipoEvento(),
                request.estado(),
                request.hora(),
                request.procesoId(),
                request.usuario(),
                request.detalle()
        );

        // Set application name if not provided in request
        String appName = request.nombreApp() != null ? request.nombreApp() : applicationName;
        event.setApplicationName(appName);

        return event;
    }

    private TraceabilityEventoResponse mapToResponse(TraceabilityEvento event) {
        return new TraceabilityEventoResponse(
                event.getId(),
                event.getTipoEvento(),
                event.getEstado(),
                event.getHora(),
                event.getProcesoId(),
                event.getUsuario(),
                event.getDetalle(),
                event.getApplicationName()
        );
    }
}
