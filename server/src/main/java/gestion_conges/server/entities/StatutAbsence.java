package gestion_conges.server.entities;

import gestion_conges.server.enums.StatutAbsenceEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter @Setter @Accessors(chain = true)
@NoArgsConstructor @AllArgsConstructor
public class StatutAbsence
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private StatutAbsenceEnum libelle;
}
