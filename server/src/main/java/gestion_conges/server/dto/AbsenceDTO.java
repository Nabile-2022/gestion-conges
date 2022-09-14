package gestion_conges.server.dto;

import gestion_conges.server.entities.Absence;
import gestion_conges.server.enums.StatutAbsenceEnum;
import gestion_conges.server.enums.TypeAbsenceEnum;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AbsenceDTO
{
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String motif;
    private TypeAbsenceEnum type;
    private StatutAbsenceEnum statut;

    public AbsenceDTO(Absence absence)
    {
        dateDebut = absence.getDateDebut();
        dateFin = absence.getDateFin();
        motif = absence.getMotif();
        type = absence.getType().getLibelle();
        statut = absence.getStatut().getLibelle();
    }
}
