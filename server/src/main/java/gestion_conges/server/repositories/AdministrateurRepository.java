package gestion_conges.server.repositories;

import gestion_conges.server.entities.Administrateur;
import gestion_conges.server.entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Integer>
{
    Optional<Administrateur> findByEmail(String email);
}
