package gestion_conges.server.services;

import gestion_conges.server.dto.JourFerieDTO;
import gestion_conges.server.entities.JourFerie;
import gestion_conges.server.helpers.DateHelpers;
import gestion_conges.server.repositories.JourFerieRepository;
import gestion_conges.server.repositories.TypeJourFerieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class JourFerieService
{
    private JourFerieRepository jourFerieRepository;
    private TypeJourFerieRepository typeJourFerieRepository;

    public Stream<JourFerie> list()
    {
        return jourFerieRepository.findAll().stream();
    }

    private void checkJourFerie(JourFerie jourFerie)
    {
        if (!jourFerie.getDate().isAfter(LocalDate.now()))
            throw new RuntimeException("Le jour férié doit être après la date actuelle.");

        if (jourFerieRepository.findByDate(jourFerie.getDate()).isPresent())
            throw new RuntimeException("Le jour férié existe déjà.");

        switch (jourFerie.getType().getLibelle())
        {
            case Ferie ->
            {
                // Rien de particulier.
            }
            case RTT ->
            {
                if (DateHelpers.isWeekEnd(jourFerie.getDate()))
                    throw new RuntimeException("Une RTT employeur ne peut être émise un week-end.");

                // Si on ajoute une absence à tous les employés, ça veut dire que si on ajoute un employé en cours de route, il faudra faire attention à lui affecter les RTT employeurs avant son arrivée. ??? Non seulement on doit faire ça, mais ça fait aussi une explosion d'entrées alors qu'on pourrait simplement vérifier si le jour actuel est une RTT et faire en sorte que tous les employés sont considérés absent.
            }
            default -> { throw new RuntimeException("Type de jour férié inconnu."); }
        }

    }

    public JourFerie add(JourFerieDTO jourFerieDTO)
    {
        var jourFerie = new JourFerie()
            .setDate(jourFerieDTO.getDate())
            .setType(typeJourFerieRepository.findByLibelle(jourFerieDTO.getType()).get())
            .setLibelle(jourFerieDTO.getLibelle());

        checkJourFerie(jourFerie);
        jourFerieRepository.save(jourFerie);
        return jourFerie;
    }

    public JourFerie get(int id)
    {
        return jourFerieRepository.findById(id).get();
    }

    public JourFerie update(JourFerieDTO jourFerieDTO)
    {

        var jourFerie = jourFerieRepository.findById(jourFerieDTO.getId()).get();
        jourFerie
            .setLibelle(jourFerieDTO.getLibelle())
            .setType(typeJourFerieRepository.findByLibelle(jourFerieDTO.getType()).get())
            .setDate(jourFerieDTO.getDate());

        checkJourFerie(jourFerie);
        jourFerieRepository.save(jourFerie);
        return jourFerie;
    }

    public void delete(int id)
    {
        jourFerieRepository.delete(jourFerieRepository.findById(id).get());
    }
}
