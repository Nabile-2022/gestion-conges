package gestion_conges.server.repositories;

import gestion_conges.server.entities.Manager;
import gestion_conges.server.entities.Salarie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Integer>
{
    Optional<Manager> findByEmail(String email);
}
