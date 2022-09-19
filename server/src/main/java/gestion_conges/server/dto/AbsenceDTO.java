package gestion_conges.server.dto;

import gestion_conges.server.entities.Absence;
import gestion_conges.server.enums.StatutAbsenceEnum;
import gestion_conges.server.enums.TypeAbsenceEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter @Setter @Accessors(chain = true)
@NoArgsConstructor
public class AbsenceDTO
{
    private int id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String motif;
    private TypeAbsenceEnum type;
    private StatutAbsenceEnum statut;

    public AbsenceDTO(Absence absence)
    {
        id = absence.getId();
        dateDebut = absence.getDateDebut();
        dateFin = absence.getDateFin();
        motif = absence.getMotif();
        type = absence.getType().getLibelle();
        statut = absence.getStatut().getLibelle();
    }
}
