package gestion_conges.server.repositories;

import gestion_conges.server.entities.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbsenceRepository extends JpaRepository<Absence, Integer>
{
}
