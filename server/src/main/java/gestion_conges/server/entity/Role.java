package gestion_conges.server.entity;

import gestion_conges.server.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "libelle", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum libelle;

    @Override
    public String toString() {
        return this.getLibelle().toString();
    }
}
