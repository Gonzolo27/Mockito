package org.gfontestad.appmockito.ejemplos.services;

import org.gfontestad.appmockito.ejemplos.models.Examen;
import org.gfontestad.appmockito.ejemplos.repositories.ExamenRepositoryInterface;
import org.gfontestad.appmockito.ejemplos.repositories.PreguntaRepositoryInterface;

import java.util.List;
import java.util.Optional;

public class ExamenServiceImpl implements ExamenServiceInterface {
    private ExamenRepositoryInterface examenRepository;
    private PreguntaRepositoryInterface preguntaRepositoryInterface;

    public ExamenServiceImpl(ExamenRepositoryInterface examenRepository, PreguntaRepositoryInterface preguntaRepositoryInterface) {
        this.examenRepository = examenRepository;
        this.preguntaRepositoryInterface = preguntaRepositoryInterface;
    }

    @Override
    public Optional<Examen> findExamenPorNombre(String nombre) {
        return examenRepository
                .findAll()
                .stream()
                .filter(e -> e.getNombre().equals(nombre))
                .findFirst();
    }

    @Override
    public Examen findExamenPorNombreConPreguntas(String nombre) {
        Optional<Examen> examenOptional = findExamenPorNombre(nombre);
        Examen examen = null;
        if(examenOptional.isPresent()) {
            examen = examenOptional.orElseThrow();
            List<String> preguntas = preguntaRepositoryInterface.findPreguntasPorExamenId(examen.getId());
            examen.setPreguntas(preguntas);
        }

        return examen;
    }

    @Override
    public Examen guardar(Examen examen) {
        if(!examen.getPreguntas().isEmpty()) {
            preguntaRepositoryInterface.guardarVarias(examen.getPreguntas());
        }
        return examenRepository.guardar(examen);
    }
}
