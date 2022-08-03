package org.gfontestad.appmockito.ejemplos.services;

import org.gfontestad.appmockito.ejemplos.models.Examen;
import org.gfontestad.appmockito.ejemplos.repositories.ExamenRepository;

import java.util.Optional;

public class ExamenServiceImpl implements ExamenService {
    private ExamenRepository examenRepository;

    public ExamenServiceImpl(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    @Override
    public Examen findExamenPorNombre(String nombre) {
        Optional<Examen> examenOptional = examenRepository
                .findAll()
                .stream()
                .filter(e -> e.getNombre().equals(nombre))
                .findFirst();

        Examen examen = null;
        if(examenOptional.isPresent()) examen = examenOptional.orElseThrow();

        return examen;
    }
}
