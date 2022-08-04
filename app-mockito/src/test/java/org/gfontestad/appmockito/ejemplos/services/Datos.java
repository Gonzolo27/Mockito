package org.gfontestad.appmockito.ejemplos.services;

import org.gfontestad.appmockito.ejemplos.models.Examen;

import java.util.Arrays;
import java.util.List;

public class Datos {
    public final static List<Examen> EXAMENES = Arrays.asList(new Examen(5L, "Matematicas"), new Examen(6L, "Castellano"), new Examen(7L, "Historia"));
    public final static List<Examen> EXAMENES_ID_NULL = Arrays.asList(new Examen(null, "Matematicas"), new Examen(null, "Castellano"), new Examen(null, "Historia"));
    public final static List<Examen> EXAMENES_ID_NEGATIVES = Arrays.asList(new Examen(-5L, "Matematicas"), new Examen(-6L, "Castellano"), new Examen(-7L, "Historia"));
    public final static List<String> PREGUNTAS = Arrays.asList("Aritmetica", "Integrales", "Derivadas", "Trigonometria", "Geometria");
    public final static Examen EXAMEN = new Examen(null, "Fisica");
}
