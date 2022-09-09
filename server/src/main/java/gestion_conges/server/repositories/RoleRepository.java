package gestion_conges.server.repositories;

import gestion_conges.server.entities.Role;
import gestion_conges.server.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>
{
    Optional<Role> findByLibelle(RoleEnum libelle);
}
