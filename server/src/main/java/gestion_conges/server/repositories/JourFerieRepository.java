package gestion_conges.server.repositories;

import gestion_conges.server.entities.Absence;
import gestion_conges.server.entities.JourFerie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface JourFerieRepository extends JpaRepository<JourFerie, Integer>
{
    Optional<JourFerie> findByDate(LocalDate date);
}
