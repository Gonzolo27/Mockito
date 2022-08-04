package org.gfontestad.appmockito.ejemplos.repositories;

import java.util.List;

public interface PreguntaRepositoryInterface {
    List<String> findPreguntasPorExamenId(Long id);
    void guardarVarias(List<String> preguntas);
}
