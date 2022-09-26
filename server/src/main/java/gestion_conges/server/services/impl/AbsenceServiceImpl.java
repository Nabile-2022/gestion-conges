package gestion_conges.server.services.impl;

import gestion_conges.server.dto.AbsenceDTO;
import gestion_conges.server.entities.Absence;
import gestion_conges.server.entities.CompteurAbsences;
import gestion_conges.server.entities.JourFerie;
import gestion_conges.server.entities.Salarie;
import gestion_conges.server.enums.StatutAbsenceEnum;
import gestion_conges.server.enums.TypeAbsenceEnum;
import gestion_conges.server.repositories.*;
import gestion_conges.server.services.AbsenceService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static gestion_conges.server.helpers.DateHelpers.isWeekEnd;

@Service
@Transactional // This ensures everything gets persisted to the DB.
@EnableScheduling
@AllArgsConstructor
public class AbsenceServiceImpl implements AbsenceService
{
    private AbsenceRepository absenceRepository;
    private TypeAbsenceRepository typeAbsenceRepository;
    private StatutAbsenceRepository statutAbsenceRepository;
    private JourFerieRepository jourFerieRepository;
    private SalarieRepository salarieRepository;
    private CompteurAbsencesRepository compteurAbsencesRepository;

    private boolean isClosed(LocalDate date, List<JourFerie> closedDays)
    {
        return closedDays.stream().anyMatch(d -> d.getDate().equals(date));
    }

    private void checkAbsence(Salarie salarie, Absence absence, List<JourFerie> closedDays)
    {
        var currentDate = LocalDate.now().plusDays(1);

        if (absence.getDateDebut().isBefore(currentDate) || absence.getDateFin().isBefore(currentDate))
            throw new RuntimeException("Le congé doit être pris après la date d'aujourd'hui.");

        if (absence.getDateDebut().isEqual(absence.getDateFin()) || absence.getDateDebut().isAfter(absence.getDateFin()))
            throw new RuntimeException("La date de début doit être avant la date de fin.");

        if (absence.getType().getLibelle() == TypeAbsenceEnum.CongeNonPaye && absence.getMotif().trim().isEmpty())
            throw new RuntimeException("Un congé non-payé doit posséder un motif.");

        if (Stream.of(absence.getDateDebut(), absence.getDateFin()).anyMatch(a -> isWeekEnd(a) || isClosed(a, closedDays)))
            throw new RuntimeException("Un congé ne peut être pris lors d'un week-end ou d'un jour férié");

        /*/ TODO: Unit tests.
        var testAbsences = new Absence[]
        {
            new Absence()
                .setDateDebut(LocalDate.of(2022, 1, 1))
                .setDateFin(LocalDate.of(2022, 1, 2)),
            new Absence()
                .setDateDebut(LocalDate.of(2022, 1, 2))
                .setDateFin(LocalDate.of(2022, 1, 3)),
            new Absence()
                .setDateDebut(LocalDate.of(2022, 1, 3))
                .setDateFin(LocalDate.of(2022, 1, 4)),
            new Absence()
                .setDateDebut(LocalDate.of(2022, 1, 1))
                .setDateFin(LocalDate.of(2022, 1, 4)),
            new Absence()
                .setDateDebut(LocalDate.of(2022, 1, 2))
                .setDateFin(LocalDate.of(2022, 1, 4)),
            new Absence()
                .setDateDebut(LocalDate.of(2022, 1, 1))
                .setDateFin(LocalDate.of(2022, 1, 3))
        };
        //*/

        /*/
        for (var absence : testAbsences)
        for (var otherAbsence : testAbsences)
        //*/
        for (var otherAbsence : salarie.getAbsences())
        {
            if (absence == otherAbsence)
                continue;

            if
            (
                (otherAbsence.getDateDebut().toEpochDay() <= absence.getDateDebut().toEpochDay() && absence.getDateDebut().toEpochDay() < otherAbsence.getDateFin().toEpochDay()) ||
                    (otherAbsence.getDateDebut().toEpochDay() < absence.getDateFin().toEpochDay() && absence.getDateFin().toEpochDay() <= otherAbsence.getDateFin().toEpochDay()) ||
                    (otherAbsence.getDateDebut().toEpochDay() <= absence.getDateDebut().toEpochDay() && absence.getDateFin().toEpochDay() <= otherAbsence.getDateFin().toEpochDay()) ||
                    (absence.getDateDebut().toEpochDay() < otherAbsence.getDateDebut().toEpochDay() && otherAbsence.getDateFin().toEpochDay() < absence.getDateFin().toEpochDay())
            )
            {
                System.err.println("A1: %s -> %s | A2: %s -> %s".formatted(absence.getDateDebut().toString(), absence.getDateFin().toString(), otherAbsence.getDateDebut().toString(), otherAbsence.getDateFin().toString()));
                throw new RuntimeException("L'absence en chevauche une autre.");
            }
        }
    }

    private void returnDaysToCounter(Absence absence, CompteurAbsences compteur)
    {
        if (Stream.of(StatutAbsenceEnum.EnAttente, StatutAbsenceEnum.Rejetee).anyMatch(s -> s == absence.getStatut().getLibelle()))
        {
            var date = absence.getDateDebut();
            var jours = 0;
            var closedDays = jourFerieRepository.findAll();

            while (date.isBefore(absence.getDateFin()))
            {
                if (!(isWeekEnd(date) || isClosed(date, closedDays)))
                    jours++;

                date = date.plusDays(1);
            }

            switch (absence.getType().getLibelle())
            {
                case RTT ->
                {
                    compteur.setNombreRTT(compteur.getNombreRTT() + jours);
                    compteurAbsencesRepository.save(compteur);
                }
                case CongePaye ->
                {
                    compteur.setNombreCongesPayes(compteur.getNombreCongesPayes() + jours);
                    compteurAbsencesRepository.save(compteur);
                }
            }
        }
    }

    @Override
    public Absence addAbsence(Salarie salarie, AbsenceDTO absenceDTO)
    {
        var absence = new Absence()
            .setDateDebut(absenceDTO.getDateDebut())
            .setDateFin(absenceDTO.getDateFin())
            .setMotif(absenceDTO.getMotif())
            .setType(typeAbsenceRepository.findByLibelle(absenceDTO.getType()).orElseThrow(() -> new RuntimeException("Type d'absence incorrect")))
            .setStatut(statutAbsenceRepository.findByLibelle(StatutAbsenceEnum.Initiale).get());

        checkAbsence(salarie, absence, jourFerieRepository.findAll());

        salarie.getAbsences().add(absence);
        absenceRepository.save(absence);
        salarieRepository.save(salarie);

        return absence;
    }

    @Override
    public void deleteAbsence(Salarie salarie, int id)
    {
        var absence = salarie.getAbsences().stream().filter(a -> a.getId() == id).findFirst().orElseThrow(() -> new RuntimeException("L'absence n'existe pas."));
        var compteur = salarie.getCompteurAbsences();

        absenceRepository.delete(absence);
        salarie.getAbsences().remove(absence);
        salarieRepository.save(salarie);

        if (absence.getStatut().getLibelle() != StatutAbsenceEnum.Rejetee)
            returnDaysToCounter(absence, compteur);
    }

    @Override
    public Absence readAbsence(Salarie salarie, int id)
    {
        return  salarie.getAbsences().stream().filter(a -> a.getId() == id).findFirst().orElseThrow(() -> new RuntimeException("L'absence n'existe pas."));
    }

    @Override
    public Absence updateAbsence(Salarie salarie, AbsenceDTO absenceDTO)
    {
        var absence = salarie.getAbsences().stream().filter(a -> a.getId() == absenceDTO.getId()).findFirst().orElseThrow(() -> new RuntimeException("L'absence n'existe pas."));

        if (absence.getStatut().getLibelle() != StatutAbsenceEnum.Rejetee)
            returnDaysToCounter(absence, salarie.getCompteurAbsences());

        absence
            .setDateDebut(absenceDTO.getDateDebut())
            .setDateFin(absenceDTO.getDateFin())
            .setMotif(absenceDTO.getMotif())
            .setType(typeAbsenceRepository.findByLibelle(absenceDTO.getType()).orElseThrow(() -> new RuntimeException("Type d'absence incorrect")))
            .setStatut(statutAbsenceRepository.findByLibelle(StatutAbsenceEnum.Initiale).get());

        checkAbsence(salarie, absence, jourFerieRepository.findAll());
        absenceRepository.save(absence);

        return absence;
    }

    @Override
    public Stream<Absence> listAbsences(Salarie salarie)
    {
        return salarie.getAbsences().stream();
    }

    @Override
    public Absence validate(int id)
    {
        var absence = absenceRepository.findById(id).get();
        //var salarie = salarieRepository.findAll().stream().filter(s -> s.getAbsences().contains(absence)).findFirst();

        if (!Stream.of(StatutAbsenceEnum.EnAttente, StatutAbsenceEnum.Rejetee).anyMatch(s -> s == absence.getStatut().getLibelle()))
            throw new RuntimeException("Seules les absences en attente ou rejetées peuvent être validées.");

        absence.setStatut(statutAbsenceRepository.findByLibelle(StatutAbsenceEnum.Validee).get());
        absenceRepository.save(absence);

        return absence;
    }

    @Override
    public Absence reject(int id)
    {
        var absence = absenceRepository.findById(id).get();

        if (absence.getStatut().getLibelle() != StatutAbsenceEnum.EnAttente)
            throw new RuntimeException("Seules les absences en attente peuvent être rejetées.");

        absence.setStatut(statutAbsenceRepository.findByLibelle(StatutAbsenceEnum.Rejetee).get());
        absenceRepository.save(absence);

        var salarie = salarieRepository.findAll().stream().filter(s -> s.getAbsences().contains(absence)).findFirst().get();
        returnDaysToCounter(absence, salarie.getCompteurAbsences());

        return absence;
    }

    @Scheduled(fixedDelay = 5000) // TODO: Move elsewhere.
    public void processAbsences()
    {
        var closedDays = jourFerieRepository.findAll();

        for (var absence : absenceRepository.findAll().stream().sorted(Comparator.comparing(Absence::getDateDebut)).toList())
        {
            try
            {
                var salarie = salarieRepository.findAll().stream().filter(s -> s.getAbsences().contains(absence)).findFirst().get();
                var compteur = salarie.getCompteurAbsences();

                checkAbsence(salarie, absence, closedDays);

                if (absence.getStatut().getLibelle() == StatutAbsenceEnum.Initiale)
                {
                    var date = absence.getDateDebut();
                    var jours = 0;

                    while (date.isBefore(absence.getDateFin()))
                    {
                        if (!(isWeekEnd(date) || isClosed(date, closedDays)))
                            jours++;

                        date = date.plusDays(1);
                    }

                    switch (absence.getType().getLibelle())
                    {
                        case RTT ->
                        {
                            jours = compteur.getNombreRTT() - jours;

                            if (jours < 0)
                            {
                                absence.setStatut(statutAbsenceRepository.findByLibelle(StatutAbsenceEnum.Rejetee).get());
                                absenceRepository.save(absence);
                                continue;
                            }

                            compteur.setNombreRTT(jours);
                            compteurAbsencesRepository.save(compteur);
                        }
                        case CongePaye ->
                        {
                            jours = compteur.getNombreCongesPayes() - jours;

                            if (jours < 0)
                            {
                                absence.setStatut(statutAbsenceRepository.findByLibelle(StatutAbsenceEnum.Rejetee).get());
                                absenceRepository.save(absence);
                                continue;
                            }

                            compteur.setNombreCongesPayes(jours);
                            compteurAbsencesRepository.save(compteur);
                        }
                    }

                    absence.setStatut(statutAbsenceRepository.findByLibelle(StatutAbsenceEnum.EnAttente).get());
                    absenceRepository.save(absence);
                }
            }
            catch (RuntimeException e) { continue; }
        }
    }
}
