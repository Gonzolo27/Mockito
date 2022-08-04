package org.gfontestad.appmockito.ejemplos.services;

import org.gfontestad.appmockito.ejemplos.models.Examen;

import java.util.Optional;

public interface ExamenServiceInterface {
    Optional<Examen> findExamenPorNombre(String nombre);
    Examen findExamenPorNombreConPreguntas(String nombre);
    Examen guardar(Examen examen);
}
