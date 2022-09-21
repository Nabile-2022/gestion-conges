package gestion_conges.server.controllers;

import gestion_conges.server.dto.JourFerieDTO;
import gestion_conges.server.entities.JourFerie;
import gestion_conges.server.services.JourFerieService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping(path = "jours-feries", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class JourFerieController
{
    private JourFerieService jourFerieService;

    @GetMapping
    public Stream<JourFerieDTO> list()
    {
        return jourFerieService.list().map(JourFerieDTO::new);
    }

    @PostMapping
    public JourFerieDTO add(@RequestBody JourFerieDTO jourFerieDTO)
    {
        return new JourFerieDTO(jourFerieService.add(jourFerieDTO));
    }

    @GetMapping(path = "{id}")
    public JourFerieDTO get(@PathVariable int id)
    {
        return new JourFerieDTO(jourFerieService.get(id));
    }

    @PutMapping(path = "{id}")
    public JourFerieDTO update(@PathVariable int id, @RequestBody JourFerieDTO jourFerieDTO)
    {
        jourFerieDTO.setId(id);
        return new JourFerieDTO(jourFerieService.update(jourFerieDTO));
    }

    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable int id)
    {
        jourFerieService.delete(id);
    }


}
