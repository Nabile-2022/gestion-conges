package gestion_conges.server.services;

import gestion_conges.server.dto.JourFerieDTO;
import gestion_conges.server.entities.JourFerie;
import gestion_conges.server.enums.TypeJourFerieEnum;
import gestion_conges.server.repositories.JourFerieRepository;
import gestion_conges.server.repositories.TypeJourFerieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Stream;
@Service
@AllArgsConstructor
public class JourFerieService {
    private JourFerieRepository jourFerieRepository;
    private TypeJourFerieRepository typeJourFerieRepository;
    public Stream<JourFerie> list() {

        return jourFerieRepository.findAll().stream();
    }

    public JourFerie add(JourFerieDTO jourFerieDTO) {
        var jourFerie = new JourFerie()
                .setDate(jourFerieDTO.getDate())
                .setType(typeJourFerieRepository.findByLibelle(jourFerieDTO.getType()).get())
                .setLibelle(jourFerieDTO.getLibelle());
        switch (jourFerieDTO.getType()){
            case Ferie -> {
                return addJourFerie(jourFerie);
            }
            case RTT -> {
                throw new RuntimeException("RTT non implemente ");
            }
        }
        throw new RuntimeException();
    }

    private JourFerie addJourFerie(JourFerie jourFerie) {
        var currentDate= LocalDate.now();
        if (!jourFerie.getDate().isAfter(currentDate)){
            throw new RuntimeException("Le jour ferie doit etre apres la date actuelle");
        }
        jourFerieRepository.save(jourFerie);
        return jourFerie;
    }


    public JourFerie get(int id) {
        return jourFerieRepository.findById(id).get();
    }
    public JourFerie update(JourFerieDTO jourFerieDTO) {

        var jourFerie= jourFerieRepository.findById(jourFerieDTO.getId()).get();

        jourFerie
                .setLibelle(jourFerieDTO.getLibelle())
                .setType(typeJourFerieRepository.findByLibelle(jourFerieDTO.getType()).get())
                .setDate(jourFerieDTO.getDate());
        jourFerieRepository.save(jourFerie);
        return jourFerie;
    }

    public void delete(int id) {
        jourFerieRepository.delete(jourFerieRepository.findById(id).get());

    }
}
