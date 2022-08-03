package org.gfontestad.appmockito.ejemplos.services;

import org.gfontestad.appmockito.ejemplos.models.Examen;

public interface ExamenService {
    Examen findExamenPorNombre(String nombre);
}
