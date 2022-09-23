package gestion_conges.server.controllers;

import gestion_conges.server.dto.AbsenceDTO;
import gestion_conges.server.entities.CompteurAbsences;
import gestion_conges.server.entities.Manager;
import gestion_conges.server.security.SalarieUserDetails;
import gestion_conges.server.services.AbsenceService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping(path = "absences", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AbsenceController
{
    private AbsenceService absenceService;

    @GetMapping
    public Stream<AbsenceDTO> list(@AuthenticationPrincipal SalarieUserDetails salarieUserDetails)
    {

        var salarie = salarieUserDetails.getSalarie();

        return absenceService.listAbsences(salarie).map(AbsenceDTO::new);
    }

    @PostMapping
    public AbsenceDTO add(@AuthenticationPrincipal SalarieUserDetails salarieUserDetails, @RequestBody AbsenceDTO absence)
    {
        var salarie = salarieUserDetails.getSalarie();

        return new AbsenceDTO(absenceService.addAbsence(salarie, absence));
    }

    @DeleteMapping(path = "{id}")
    public void delete(@AuthenticationPrincipal SalarieUserDetails salarieUserDetails, @PathVariable int id)
    {
        var salarie = salarieUserDetails.getSalarie();

        absenceService.deleteAbsence(salarie, id);
    }

    @PutMapping(path = "{id}")
    public AbsenceDTO update(@AuthenticationPrincipal SalarieUserDetails salarieUserDetails, @PathVariable int id, @RequestBody AbsenceDTO absenceDTO)
    {
        var salarie = salarieUserDetails.getSalarie();
        absenceDTO.setId(id);

        return new AbsenceDTO(absenceService.updateAbsence(salarie, absenceDTO));
    }

    @GetMapping(path = "{id}")
    public AbsenceDTO get(@AuthenticationPrincipal SalarieUserDetails salarieUserDetails, @PathVariable int id)
    {
        var salarie = salarieUserDetails.getSalarie();

        return new AbsenceDTO(absenceService.readAbsence(salarie, id));
    }

    @PutMapping(path = "validate/{id}")
    public AbsenceDTO validate(@AuthenticationPrincipal SalarieUserDetails salarieUserDetails, @PathVariable int id)
    {
        var manager = salarieUserDetails.getSalarie();

        if (!(manager instanceof Manager))
            throw new RuntimeException("Seuls les managers peuvent accéder à cette interface.");

        return new AbsenceDTO(absenceService.validate(id));
    }

    @DeleteMapping(path = "reject/{id}")
    public AbsenceDTO reject(@AuthenticationPrincipal SalarieUserDetails salarieUserDetails, @PathVariable int id)
    {
        var manager = salarieUserDetails.getSalarie();

        if (!(manager instanceof Manager))
            throw new RuntimeException("Seuls les managers peuvent accéder à cette interface.");

        return new AbsenceDTO(absenceService.reject(id));
    }

    @GetMapping(path = "compteur")
    public CompteurAbsences getCompteur(@AuthenticationPrincipal SalarieUserDetails salarieUserDetails)
    {
        return salarieUserDetails.getSalarie().getCompteurAbsences();
    }
}
