package gestion_conges.server.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Limites
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private int maxRTTEmployeur;
    @Column(nullable = false)
    private int maxRTTSalarie;
    @Column(nullable = false)
    private int maxCongesPayes;
}
