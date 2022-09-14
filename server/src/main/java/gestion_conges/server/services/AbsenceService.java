package gestion_conges.server.services;

import gestion_conges.server.entities.Absence;
import gestion_conges.server.entities.Salarie;

import java.util.List;

public interface AbsenceService {

    public void addAbsence(Salarie salarie);
    public void deleteAbsence(int id);
    public Absence readAbsence(int id);
    public Absence updateAbsence( Absence absence, int id);
    public List<Absence> listerAbsence();
}