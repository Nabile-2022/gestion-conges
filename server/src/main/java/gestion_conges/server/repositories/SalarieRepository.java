package gestion_conges.server.repositories;

import gestion_conges.server.entities.Salarie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalarieRepository extends JpaRepository<Salarie, Integer>
{
    Optional<Salarie> findByEmail(String email);
}
