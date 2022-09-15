package gestion_conges.server.controllers;

import gestion_conges.server.dto.AbsenceDTO;
import gestion_conges.server.security.SalarieUserDetails;
import gestion_conges.server.services.AbsenceService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
