package gestion_conges.server.services;

import gestion_conges.server.dto.AbsenceDTO;
import gestion_conges.server.entities.Absence;
import gestion_conges.server.entities.Salarie;

import java.util.stream.Stream;

public interface AbsenceService {

    public Absence addAbsence(Salarie salarie, AbsenceDTO absence);
    public void deleteAbsence(Salarie salarie, int id);

    Absence readAbsence(Salarie salarie, int id);

    public Absence updateAbsence(Salarie absence, AbsenceDTO absenceDTO);


    public Stream<Absence> listAbsences(Salarie salarie);

    Absence validate(int id);

    Absence reject(int id);
    void processAbsences();
}
