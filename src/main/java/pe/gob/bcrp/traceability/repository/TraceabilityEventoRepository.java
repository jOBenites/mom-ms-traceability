package pe.gob.bcrp.traceability.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.bcrp.traceability.model.entity.TraceabilityEvento;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TraceabilityEventoRepository extends JpaRepository<TraceabilityEvento, Long> {

    List<TraceabilityEvento> findByProcesoIdOrderByHoraDesc(String processId);

    List<TraceabilityEvento> findByTipoEventoOrderByHoraDesc(String eventType);

    List<TraceabilityEvento> findByEstadoOrderByHoraDesc(String status);

    @Query("SELECT t FROM TraceabilityEvento t WHERE t.hora BETWEEN :startDate AND :endDate ORDER BY t.hora DESC")
    List<TraceabilityEvento> findEventsBetweenDates(@Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate);

    @Query("SELECT t FROM TraceabilityEvento t WHERE t.hora = :processId AND t.hora BETWEEN :startDate AND :endDate ORDER BY t.hora DESC")
    List<TraceabilityEvento> findByProcessIdAndDateRange(@Param("processId") String processId,
                                                        @Param("startDate") LocalDateTime startDate,
                                                        @Param("endDate") LocalDateTime endDate);
}