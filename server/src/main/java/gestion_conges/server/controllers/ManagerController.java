package gestion_conges.server.controllers;

import gestion_conges.server.entities.Manager;
import gestion_conges.server.entities.Salarie;
import gestion_conges.server.repositories.SalarieRepository;
import gestion_conges.server.security.SalarieUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "manager", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ManagerController
{
    private SalarieRepository salarieRepository;

    @GetMapping(path="salaries")
    public List<Salarie> list(@AuthenticationPrincipal SalarieUserDetails salarieUserDetails)
    {
        var manager = salarieUserDetails.getSalarie();

        if (!(manager instanceof Manager))
            throw new RuntimeException("Seuls les managers peuvent accéder à cette interface.");

        return salarieRepository.findAllByDepartement(manager.getDepartement());
    }
}
