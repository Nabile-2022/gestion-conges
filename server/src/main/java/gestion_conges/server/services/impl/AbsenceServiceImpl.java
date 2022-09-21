package gestion_conges.server.services.impl;

import gestion_conges.server.dto.AbsenceDTO;
import gestion_conges.server.entities.Absence;
import gestion_conges.server.entities.Salarie;
import gestion_conges.server.enums.StatutAbsenceEnum;
import gestion_conges.server.enums.TypeAbsenceEnum;
import gestion_conges.server.repositories.*;
import gestion_conges.server.services.AbsenceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Stream;

import static gestion_conges.server.helpers.DateHelpers.isWeekEnd;

@Service
@Transactional // This ensures everything gets persisted to the DB.
@AllArgsConstructor
public class AbsenceServiceImpl implements AbsenceService
{
    private AbsenceRepository absenceRepository;
    private TypeAbsenceRepository typeAbsenceRepository;
    private StatutAbsenceRepository statutAbsenceRepository;
    private JourFerieRepository jourFerieRepository;
    private SalarieRepository salarieRepository;

    private boolean isClosed(LocalDate date)
    {
        return jourFerieRepository.findAll().stream().anyMatch(d -> d.getDate().equals(date));
    }

    private void checkAbsence(Salarie salarie, Absence absence)
    {
        var currentDate = LocalDate.now().plusDays(1);

        if (absence.getDateDebut().isBefore(currentDate) || absence.getDateFin().isBefore(currentDate))
            throw new RuntimeException("Le congé doit être pris après la date d'aujourd'hui.");

        if (absence.getDateDebut().isEqual(absence.getDateFin()) || absence.getDateDebut().isAfter(absence.getDateFin()))
            throw new RuntimeException("La date de début doit être avant la date de fin.");

        if (absence.getType().getLibelle() == TypeAbsenceEnum.CongeNonPaye && absence.getMotif().trim().isEmpty())
            throw new RuntimeException("Un congé non-payé doit posséder un motif.");

        if (Stream.of(absence.getDateDebut(), absence.getDateFin()).anyMatch(a -> isWeekEnd(a) || isClosed(a)))
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

    @Override
    public Absence addAbsence(Salarie salarie, AbsenceDTO absenceDTO)
    {
        var absence = new Absence()
            .setDateDebut(absenceDTO.getDateDebut())
            .setDateFin(absenceDTO.getDateFin())
            .setMotif(absenceDTO.getMotif())
            .setType(typeAbsenceRepository.findByLibelle(absenceDTO.getType()).orElseThrow(() -> new RuntimeException("Type d'absence incorrect")))
            .setStatut(statutAbsenceRepository.findByLibelle(StatutAbsenceEnum.Initiale).get());

        checkAbsence(salarie, absence);

        salarie.getAbsences().add(absence);
        absenceRepository.save(absence);
        salarieRepository.save(salarie);

        return absence;
    }

    @Override
    public void deleteAbsence(Salarie salarie, int id)
    {
        var absence = salarie.getAbsences().stream().filter(a -> a.getId() == id).findFirst().orElseThrow(() -> new RuntimeException("L'absence n'existe pas."));

        absenceRepository.delete(absence);
        salarie.getAbsences().remove(absence);
        salarieRepository.save(salarie);
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

        absence
            .setDateDebut(absenceDTO.getDateDebut())
            .setDateFin(absenceDTO.getDateFin())
            .setMotif(absenceDTO.getMotif())
            .setType(typeAbsenceRepository.findByLibelle(absenceDTO.getType()).orElseThrow(() -> new RuntimeException("Type d'absence incorrect")))
            .setStatut(statutAbsenceRepository.findByLibelle(StatutAbsenceEnum.Initiale).get());

        checkAbsence(salarie, absence);
        absenceRepository.save(absence);

        return absence;
    }

    @Override
    public Stream<Absence> listAbsences(Salarie salarie)
    {
        return salarie.getAbsences().stream();
    }
}
