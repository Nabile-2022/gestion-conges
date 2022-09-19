package gestion_conges.server.repositories;

import gestion_conges.server.entities.TypeAbsence;
import gestion_conges.server.entities.TypeJourFerie;
import gestion_conges.server.enums.TypeAbsenceEnum;
import gestion_conges.server.enums.TypeJourFerieEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeJourFerieRepository extends JpaRepository<TypeJourFerie, Integer>
{
    Optional<TypeJourFerie> findByLibelle(TypeJourFerieEnum libelle);
}
