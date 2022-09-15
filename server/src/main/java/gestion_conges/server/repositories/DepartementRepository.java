package gestion_conges.server.repositories;

import gestion_conges.server.entities.Absence;
import gestion_conges.server.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartementRepository extends JpaRepository<Departement, Integer>
{
}
