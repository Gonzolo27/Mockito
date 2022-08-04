package org.gfontestad.appmockito.ejemplos.repositories;

import org.gfontestad.appmockito.ejemplos.models.Examen;

import java.util.List;

public interface ExamenRepositoryInterface {
    Examen guardar(Examen examen);
    List<Examen> findAll();
}
