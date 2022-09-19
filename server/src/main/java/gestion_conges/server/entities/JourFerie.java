package gestion_conges.server.entities;

import gestion_conges.server.enums.TypeJourFerieEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class JourFerie
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private String libelle;
    @ManyToOne
    private TypeJourFerie type;
}
