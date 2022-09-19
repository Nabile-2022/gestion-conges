package gestion_conges.server.repositories;

import gestion_conges.server.entities.Absence;
import gestion_conges.server.entities.JourFerie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JourFerieRepository extends JpaRepository<JourFerie, Integer>
{
}
