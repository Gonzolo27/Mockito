package org.gfontestad.appmockito.ejemplos.services;

import org.gfontestad.appmockito.ejemplos.models.Examen;
import org.gfontestad.appmockito.ejemplos.repositories.ExamenRepositoryInterface;
import org.gfontestad.appmockito.ejemplos.repositories.PreguntaRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) //Con esto nuestro ejecutor de JUNIT se habilitan las anotaciones sin usar el setup
class ExamenServiceImplTest {

    @Mock
    ExamenRepositoryInterface repository;

    @Mock
    PreguntaRepositoryInterface preguntaRepository;

    // ### CON EL USO DE ANOTACIONES ###
    @InjectMocks

    ExamenServiceImpl service;

    // ### SIN EL USO DE ANOTACIONES ###
    //ExamenServiceInterface service;

    @BeforeEach
    void setUp() {
        // ### CON EL USO DE ANOTACIONES ###
        //MockitoAnnotations.openMocks(this); //habilitamos el uso de anotaciones @ para esta clase (@Mock y @InjectMocks)


        // ### SIN EL USO DE ANOTACIONES ###
        //repository = mock(ExamenRepositorioOtro.class); //Aunque se esté usando esa clase ExamenRepositorioOtro, nunca se invoca el método que implementa esa clase,
                                                        // puesto que se está invocando la simulación del mock
        //preguntaRepositoryInterface = mock(PreguntaRepository.class);
        //service = new ExamenServiceImpl(repository, preguntaRepository);

    }

    @Test
    void findExamenPorNombre() {
        List<Examen> datos = Datos.EXAMENES;
        when(repository.findAll()).thenReturn(datos); //Cuando se invoque findAll, devolverá datos. findAll se invoca en ExamenServiceImpl en findExamenPorNombre

        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");

        assertTrue(examen.isPresent());
        assertEquals(5L, examen.orElseThrow().getId());
        assertEquals("Matematicas", examen.orElseThrow().getNombre());
    }

    @Test
    void findExamenPorNombreListaVacia() {

        List<Examen> datos = Collections.emptyList();
        when(repository.findAll()).thenReturn(datos);
        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");

        assertFalse(examen.isPresent());
    }

    @Test
    void testPreguntasExamen() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);

        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Integrales"));
    }

    @Test
    void testPreguntasExamenVerify() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);

        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Integrales"));

        verify(repository).findAll(); //Se verifica que se está llamando al findAll del objeto repository. Puede ser que no se llame puesto que la llamada está dentro de un if.
        verify(preguntaRepository).findPreguntasPorExamenId(5L);
    }

    @Test
    void testNoExisteExamenVerify() {
        // Given
        when(repository.findAll()).thenReturn(Collections.emptyList());
        when(preguntaRepository.findPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);

        // When
        //Examen examen = service.findExamenPorNombreConPreguntas("Fisica y Quimica");
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");

        // Then
        assertNull(examen);

        verify(repository).findAll(); //Se verifica que se está llamando al findAll del objeto repository. Puede ser que no se llame puesto que la llamada está dentro de un if.
        //verify(preguntaRepositoryInterface).findPreguntasPorExamenId(5L);
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
    }

    @Test
    void testGuardarExamen() {
        // Given
        Examen examenFromDatos = Datos.EXAMEN;
        examenFromDatos.setPreguntas(Datos.PREGUNTAS);

        when(repository.guardar(any(Examen.class))).then(new Answer<Examen>() {
            Long secuencia = 8L;

            @Override
            public Examen answer(InvocationOnMock invocationOnMock) throws Throwable {
                Examen examen = invocationOnMock.getArgument(0);
                examen.setId(secuencia++);
                return examen;
            }
        });

        // When
        Examen examen = service.guardar(examenFromDatos);

        // Then
        assertNotNull(examen.getId());
        assertEquals(8L, examen.getId());
        assertEquals("Fisica", examen.getNombre());

        verify(repository).guardar(any(Examen.class));
        verify(preguntaRepository).guardarVarias(anyList());
    }

    @Test
    void testManejoExceptions() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NULL);
        when(preguntaRepository.findPreguntasPorExamenId(isNull())).thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            //service.findExamenPorNombreConPreguntas("Matematicas");
            service.findExamenPorNombreConPreguntas("Matematicas");
        });
        assertEquals(IllegalArgumentException.class, exception.getClass());
        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(isNull());
    }

    @Test
    void testArgumentMatchers() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        service.findExamenPorNombreConPreguntas("Matematicas");

        verify(repository).findAll();
        //verify(preguntaRepository).findPreguntasPorExamenId(argThat((arg) -> { return arg != null && arg.equals(5L); }));
        verify(preguntaRepository).findPreguntasPorExamenId(argThat((arg) -> { return arg != null && arg >= 5; }));
        //verify(preguntaRepository).findPreguntasPorExamenId(eq(5L));
    }
    @Test
    void testArgumentMatchers2() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        service.findExamenPorNombreConPreguntas("Matematicas");

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(argThat(new MiArgsMatchers()));
    }

    @Test
    void testArgumentMatchers3() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        service.findExamenPorNombreConPreguntas("Matematicas");

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(argThat((argument) -> {
            return argument != null && argument > 0;
        }));
    }

    public static class MiArgsMatchers implements ArgumentMatcher<Long> {

        private Long argumento;
        @Override
        public boolean matches(Long argument) {
            this.argumento = argument;
            return argument != null && argument > 0;
        }

        @Override
        public String toString() {
            return "Es para un mensaje personalizado de error que imprime mockito en caso de que falle el test" +
                    " debe ser un entero positivo. El valor ha sido: " + argumento;
        }
    }
}