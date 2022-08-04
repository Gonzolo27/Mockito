package org.gfontestad.appmockito.ejemplos.repositories;

import org.gfontestad.appmockito.ejemplos.models.Examen;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamenRepositorioOtro implements ExamenRepositoryInterface {

    @Override
    public Examen guardar(Examen examen) {
        return null;
    }

    @Override
    public List<Examen> findAll() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
