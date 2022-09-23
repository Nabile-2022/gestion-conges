package gestion_conges.server.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter @Accessors(chain = true)
@NoArgsConstructor @AllArgsConstructor
public class Salarie
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String email;
    private String password;

    @ManyToOne
    private CompteurAbsences compteurAbsences;
    @ManyToOne
    private Departement departement;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Absence> absences = new HashSet<>();
}
