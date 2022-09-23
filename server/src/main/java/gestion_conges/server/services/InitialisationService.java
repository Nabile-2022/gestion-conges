package gestion_conges.server.services;

import gestion_conges.server.entities.*;
import gestion_conges.server.enums.StatutAbsenceEnum;
import gestion_conges.server.enums.TypeAbsenceEnum;
import gestion_conges.server.enums.TypeJourFerieEnum;
import gestion_conges.server.repositories.*;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
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
    private JourFerieRepository jourFerieRepository;

    private void populateUsers()
    {
        var departement = new Departement().setNom("IT");
        departementRepository.save(departement);

        var absences =
            Set.of(
                new Absence()
                    .setDateDebut(LocalDate.now().plusDays(1))
                    .setDateFin(LocalDate.now().plusDays(2))
                    .setMotif("Un motif.")
                    .setType(typeAbsenceRepository.findByLibelle(TypeAbsenceEnum.CongeNonPaye).get())
                    .setStatut(statutAbsenceRepository.findByLibelle(StatutAbsenceEnum.Initiale).get())
            );

        absenceRepository.saveAll(absences);

        var compteurAbsences = new CompteurAbsences()
            .setNombreRTT(11)
            .setNombreCongesPayes(25);

        var compteurAbsencesManager = new CompteurAbsences();
        var compteurAbsencesAdministrateur = new CompteurAbsences();
        compteurAbsencesRepository.save(compteurAbsences);
        compteurAbsencesRepository.save(compteurAbsencesManager);
        compteurAbsencesRepository.save(compteurAbsencesAdministrateur);

        var passwordEncoder = new BCryptPasswordEncoder();

        var salarie = new Salarie()
            .setNom("Salarié")
            .setPrenom("Prénom")
            .setEmail("Salarie")
            .setPassword(passwordEncoder.encode("Salarie"))
            .setCompteurAbsences(compteurAbsences)
            .setDepartement(departement)
            .setAbsences(absences);

        salarieRepository.save(salarie);

        var manager = new Manager();
        manager
            .setNom("Manager")
            .setPrenom("Prénom")
            .setEmail("Manager")
            .setPassword(passwordEncoder.encode("Manager"))
            .setCompteurAbsences(compteurAbsencesManager)
            .setDepartement(departement);

        managerRepository.save(manager);

        var administrateur = new Administrateur();
        administrateur
            .setNom("Administrateur")
            .setPrenom("Prénom")
            .setEmail("Administrateur")
            .setPassword(passwordEncoder.encode("Administrateur"))
            .setCompteurAbsences(compteurAbsencesAdministrateur)
            .setDepartement(departement);

        administrateurRepository.save(administrateur);
    }

    private void populateEnums()
    {
        typeAbsenceRepository.saveAll(Arrays.stream(TypeAbsenceEnum.values()).map(v -> new TypeAbsence().setLibelle(v)).toList());

        statutAbsenceRepository.saveAll(Arrays.stream(StatutAbsenceEnum.values()).map(v -> new StatutAbsence().setLibelle(v)).toList());

        typeJourFerieRepository.saveAll(Arrays.stream(TypeJourFerieEnum.values()).map(v -> new TypeJourFerie().setLibelle(v)).toList());
    }

    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void populateDatabase() throws IOException
    {
        populateEnums();
        populateUsers();
        populateClosedDays();
    }

    private void populateClosedDays() throws IOException
    {
        // Source: https://www.data.gouv.fr/fr/datasets/jours-feries-en-france/#resources
        var file = new File(ClassLoader.getSystemClassLoader().getResource("jours_feries_metropole.csv").getFile());
        var type = typeJourFerieRepository.findByLibelle(TypeJourFerieEnum.Ferie).get();

        var lines = FileUtils.readLines(file, "UTF-8");
        lines.remove(0); // Delete header.

        for (var line : lines)
        {
            var tokens = line.split(",");
            var day = tokens[0];
            var libelle = tokens[3];

            var closedDay = new JourFerie()
                .setDate(LocalDate.parse(day))
                .setType(type)
                .setLibelle(libelle);

            jourFerieRepository.save(closedDay);
        }
    }
}
