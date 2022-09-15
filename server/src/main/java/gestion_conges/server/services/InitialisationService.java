package gestion_conges.server.services;

import gestion_conges.server.entities.*;
import gestion_conges.server.enums.StatutAbsenceEnum;
import gestion_conges.server.enums.TypeAbsenceEnum;
import gestion_conges.server.enums.TypeJourFerieEnum;
import gestion_conges.server.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

@Component
@AllArgsConstructor
public class InitialisationService
{
    private SalarieRepository salarieRepository;
    private ManagerRepository managerRepository;
    private AdministrateurRepository administrateurRepository;
    private CompteurAbsencesRepository compteurAbsencesRepository;
    private AbsenceRepository absenceRepository;
    private TypeAbsenceRepository typeAbsenceRepository;
    private StatutAbsenceRepository statutAbsenceRepository;
    private TypeJourFerieRepository typeJourFerieRepository;
    private DepartementRepository departementRepository;

    private void populateUsers()
    {
        var departement = new Departement().setNom("IT");
        departementRepository.save(departement);

        var absences =
            Set.of(
                new Absence()
                    .setDateDebut(LocalDate.now())
                    .setDateFin(LocalDate.now().plusDays(1))
                    .setMotif("Un motif.")
                    .setType(typeAbsenceRepository.findByLibelle(TypeAbsenceEnum.CongeNonPaye).get())
                    .setStatut(statutAbsenceRepository.findByLibelle(StatutAbsenceEnum.Initiale).get())
            );

        absenceRepository.saveAll(absences);

        var compteurAbsences = new CompteurAbsences();
        var compteurAbsencesManager = new CompteurAbsences();
        compteurAbsencesRepository.save(compteurAbsences);
        compteurAbsencesRepository.save(compteurAbsencesManager);

        var salarie = new Salarie()
            .setNom("Nom")
            .setPrenom("Prénom")
            .setEmail("e@mail.org")
            .setCompteurAbsences(compteurAbsences)
            .setDepartement(departement)
            .setAbsences(absences);

        salarieRepository.save(salarie);

        var manager = new Manager();
        manager
            .setNom("Nom")
            .setPrenom("Prénom")
            .setEmail("manage@mail.org")
            .setCompteurAbsences(compteurAbsencesManager)
            .setDepartement(departement);

        managerRepository.save(manager);
    }

    private void populateEnums()
    {
        typeAbsenceRepository.saveAll(Arrays.stream(TypeAbsenceEnum.values()).map(v -> new TypeAbsence().setLibelle(v)).toList());

        statutAbsenceRepository.saveAll(Arrays.stream(StatutAbsenceEnum.values()).map(v -> new StatutAbsence().setLibelle(v)).toList());

        typeJourFerieRepository.saveAll(Arrays.stream(TypeJourFerieEnum.values()).map(v -> new TypeJourFerie().setLibelle(v)).toList());
    }

    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void populateDatabase()
    {
        populateEnums();
        populateUsers();
    }
}
