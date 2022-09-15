package gestion_conges.server.repositories;

import gestion_conges.server.entities.StatutAbsence;
import gestion_conges.server.entities.TypeAbsence;
import gestion_conges.server.enums.StatutAbsenceEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatutAbsenceRepository extends JpaRepository<StatutAbsence, Integer>
{
    Optional<StatutAbsence> findByLibelle(StatutAbsenceEnum libelle);
}
