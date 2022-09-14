package gestion_conges.server.repositories;

import gestion_conges.server.entities.Absence;
import gestion_conges.server.entities.Salarie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface AbsenceResposirory extends JpaRepository<Absence,Long> {
    Optional<Absence> findById(Long id);
    Stream<Absence> findBySalarie(Salarie salarie);


}
