package gestion_conges.server.services.impl;

import gestion_conges.server.entities.Absence;
import gestion_conges.server.entities.Salarie;
import gestion_conges.server.repositories.AbsenceRepository;
import gestion_conges.server.services.AbsenceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service
@Transactional // This ensures everything gets persisted to the DB.
@AllArgsConstructor
public class AbsenceServiceImpl implements AbsenceService
{
    private AbsenceRepository absenceRepository;

    @Override
    public void addAbsence(Salarie salarie)
    {

    }

    @Override
    public void deleteAbsence(int id)
    {

    }

    @Override
    public Absence readAbsence(int id)
    {
        return null;
    }

    @Override
    public Absence updateAbsence(Absence absence, int id)
    {
        return null;
    }

    @Override
    public Stream<Absence> listAbsences(Salarie salarie)
    {
        return salarie.getAbsences().stream();
    }
}
