package pe.gob.bcrp.traceability.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.bcrp.traceability.model.dto.TraceabilityEventoRequest;
import pe.gob.bcrp.traceability.model.dto.TraceabilityEventoResponse;
import pe.gob.bcrp.traceability.service.ITraceabilityService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/trazabilidad")
@CrossOrigin(origins = "*")
public class TraceabilityController {

    @Autowired
    private ITraceabilityService traceabilityService;

    @PostMapping("/eventos")
    public ResponseEntity<TraceabilityEventoResponse> saveEvent(@Valid @RequestBody TraceabilityEventoRequest request) {
        TraceabilityEventoResponse response = traceabilityService.logEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/eventos/proceso/{processId}")
    public ResponseEntity<List<TraceabilityEventoResponse>> getEventsByProcess(@PathVariable String processId) {
        List<TraceabilityEventoResponse> events = traceabilityService.getEventsByProcessId(processId);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/proceso/{processId}/trazabilidad")
    public ResponseEntity<List<TraceabilityEventoResponse>> getProcessTraceability(
            @PathVariable String processId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<TraceabilityEventoResponse> events = traceabilityService.getProcessTraceability(processId, startDate, endDate);
        return ResponseEntity.ok(events);
    }
}