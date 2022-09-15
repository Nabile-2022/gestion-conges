package gestion_conges.server.repositories;

import gestion_conges.server.entities.Absence;
import gestion_conges.server.entities.TypeAbsence;
import gestion_conges.server.enums.TypeAbsenceEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeAbsenceRepository extends JpaRepository<TypeAbsence, Integer>
{
    Optional<TypeAbsence> findByLibelle(TypeAbsenceEnum libelle);
}
