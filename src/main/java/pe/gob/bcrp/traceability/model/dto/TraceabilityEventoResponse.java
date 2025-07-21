package pe.gob.bcrp.traceability.model.dto;

import java.time.LocalDateTime;

public record TraceabilityEventoResponse(
        Long id,
        String tipoEvento,
        String estado,
        LocalDateTime hora,
        String procesoId,
        String usuario,
        String detalle,
        String nombreApp
) {}