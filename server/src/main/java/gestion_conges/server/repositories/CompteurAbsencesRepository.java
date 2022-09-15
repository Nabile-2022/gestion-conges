package gestion_conges.server.repositories;

import gestion_conges.server.entities.Absence;
import gestion_conges.server.entities.CompteurAbsences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteurAbsencesRepository extends JpaRepository<CompteurAbsences, Integer>
{
}
