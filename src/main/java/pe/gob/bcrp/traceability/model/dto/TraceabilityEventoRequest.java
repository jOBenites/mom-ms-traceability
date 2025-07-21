package pe.gob.bcrp.traceability.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

public record TraceabilityEventoRequest(
        @NotBlank(message = "Tipo de evento es requerido")
        String tipoEvento,

        @NotBlank(message = "Estado es requerido")
        @Pattern(regexp = "OK|ERROR", message = "Estado debe ser OK o ERROR")
        String estado,

        LocalDateTime hora,

        @NotBlank(message = "ProcessId es requerido")
        String procesoId,

        String usuario,
        String detalle,
        String nombreApp
) {
    public TraceabilityEventoRequest {
        if (hora == null) {
            hora = LocalDateTime.now();
        }
    }
}