package pe.gob.bcrp.traceability.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mom_det_traceability_evento")
public class TraceabilityEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tipo de evento es requerido")
    @Column(name = "ti_evento", nullable = false)
    private String tipoEvento;

    @NotBlank(message = "Estado es requerido")
    @Column(name = "estado", nullable = false)
    private String estado; // OK o ERROR

    @NotNull(message = "Hora es requerida")
    @Column(name = "hora", nullable = false)
    private LocalDateTime hora;

    @NotBlank(message = "ProcessId es requerido")
    @Column(name = "id_proceso", nullable = false)
    private String procesoId;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "fec_reg")
    @Comment("Fecha de registro")
    private LocalDateTime fechaCreacion;

    @Column(name = "detalle", columnDefinition = "TEXT")
    private String detalle;

    @Column(name = "nombre_app")
    private String applicationName;

    public TraceabilityEvento(String tipoEvento, String estado, LocalDateTime hora,
                             String procesoId, String usuario, String detalle, LocalDateTime fechaCreacion) {
        this.tipoEvento = tipoEvento;
        this.estado = estado;
        this.hora = hora;
        this.procesoId = procesoId;
        this.usuario = usuario;
        this.detalle = detalle;
        this.fechaCreacion = fechaCreacion;
    }
}