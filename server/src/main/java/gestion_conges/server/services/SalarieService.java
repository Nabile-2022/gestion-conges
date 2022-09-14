package gestion_conges.server.services;

import gestion_conges.server.entities.Absence;
import gestion_conges.server.entities.Salarie;

import java.util.List;

public interface SalarieService {
    void addAbsence(Salarie salarie,Absence absence);
    public void deleteAbsence(int id);
    public Salarie readAbsence(int id);
    public Salarie updateAbsence(Salarie salarie, int id);
    public List<Salarie> listerAbsenceEmployeur();
    public List<Absence> listeAbsence();
}
