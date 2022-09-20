package gestion_conges.server.dto;

import gestion_conges.server.entities.JourFerie;
import gestion_conges.server.entities.TypeJourFerie;
import gestion_conges.server.enums.TypeJourFerieEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class JourFerieDTO {
    private  int id;
    private LocalDate date;
    private String libelle;
    private TypeJourFerieEnum type;

    public JourFerieDTO(JourFerie jourFerie){
        id=jourFerie.getId();
        date=jourFerie.getDate();
        libelle= jourFerie.getLibelle();
        type=jourFerie.getType().getLibelle();
    }

}

