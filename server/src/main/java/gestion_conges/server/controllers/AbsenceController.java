package gestion_conges.server.controllers;

import gestion_conges.server.dto.AbsenceDTO;
import gestion_conges.server.entities.Absence;
import gestion_conges.server.entities.Salarie;
import gestion_conges.server.repositories.SalarieRepository;
import gestion_conges.server.security.SalarieUserDetails;
import gestion_conges.server.services.AbsenceService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping(path = "absences", produces = MediaType.APPLICATION_JSON_VALUE)
public class AbsenceController
{
    private AbsenceService absenceService;
    private SalarieRepository salarieRepository;

    public AbsenceController(AbsenceService absenceService, SalarieRepository salarieRepository)
    {
        this.absenceService = absenceService;
        this.salarieRepository = salarieRepository;
    }

    @GetMapping
    public Stream<AbsenceDTO> list(@AuthenticationPrincipal SalarieUserDetails salarieUserDetails)
    {
        Salarie salarie = salarieUserDetails.getSalarie();

        return absenceService.listAbsences(salarie).map(a -> new AbsenceDTO(a));
    }
}
