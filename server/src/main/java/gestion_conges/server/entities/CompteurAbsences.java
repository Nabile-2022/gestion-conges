package gestion_conges.server.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter @Setter @Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CompteurAbsences
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private int nombreRTT;
    @Column(nullable = false)
    private int nombreCongesPayes;
}
