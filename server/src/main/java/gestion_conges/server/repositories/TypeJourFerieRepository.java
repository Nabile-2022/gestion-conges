package gestion_conges.server.repositories;

import gestion_conges.server.entities.TypeAbsence;
import gestion_conges.server.entities.TypeJourFerie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeJourFerieRepository extends JpaRepository<TypeJourFerie, Integer>
{
}
