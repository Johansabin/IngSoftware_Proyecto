package BC5_Notificaciones;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Implementación en memoria del repositorio para pruebas unitarias de persistencia CRUD.
 */
class InMemoryRecordatorioRepository implements RecordatorioRepository {
    private final ConcurrentMap<UUID, Recordatorio> storage = new ConcurrentHashMap<>();

    @Override
    public void adicionar(Recordatorio recordatorio) {
        if (recordatorio != null) {
            storage.put(recordatorio.getId(), recordatorio);
        }
    }

    @Override
    public void eliminar(UUID id) {
        if (id != null) {
            storage.remove(id);
        }
    }

    @Override
    public Recordatorio buscar(UUID id) {
        return id != null ? storage.get(id) : null;
    }

    @Override
    public List<Recordatorio> listarPorEstudiante(UUID estudianteId) {
        return storage.values().stream()
                .filter(r -> r.getEstudianteId().equals(estudianteId))
                .toList();
    }

    public void limpiar() {
        storage.clear();
    }
}

public class RecordatorioRepositoryTest {

    private InMemoryRecordatorioRepository repository;
    private UUID estudianteId;
    private Recordatorio recordatorioPrueba;

    @BeforeEach
    void setUp() {
        // Arrange previo para cada prueba
        repository = new InMemoryRecordatorioRepository();
        estudianteId = UUID.randomUUID();
        recordatorioPrueba = new Recordatorio(
            estudianteId, 
            "Sesión de tutoría", 
            LocalDateTime.now().plusDays(2)
        );
    }

    @AfterEach
    void tearDown() {
        // Limpieza posterior al test
        if (repository != null) {
            repository.limpiar();
        }
        repository = null;
        recordatorioPrueba = null;
    }

    @Test
    void adicionarYBuscar_RecordatorioValido_PersisteCorrectamente() {
        // Act
        repository.adicionar(recordatorioPrueba);
        Recordatorio encontrado = repository.buscar(recordatorioPrueba.getId());

        // Assert
        assertNotNull(encontrado);
        assertEquals(recordatorioPrueba.getId(), encontrado.getId());
        assertEquals("Sesión de tutoría", encontrado.getMensaje());
    }

    @Test
    void eliminar_RecordatorioExistente_LoRemueveDeLaPersistencia() {
        // Arrange
        repository.adicionar(recordatorioPrueba);
        assertNotNull(repository.buscar(recordatorioPrueba.getId()));

        // Act
        repository.eliminar(recordatorioPrueba.getId());

        // Assert
        assertNull(repository.buscar(recordatorioPrueba.getId()));
    }

    @Test
    void listarPorEstudiante_MultiplesRecordatorios_RetornaSoloDelEstudiante() {
        // Arrange
        UUID otroEstudianteId = UUID.randomUUID();
        Recordatorio r1 = new Recordatorio(estudianteId, "Recordatorio 1", LocalDateTime.now().plusDays(1));
        Recordatorio r2 = new Recordatorio(estudianteId, "Recordatorio 2", LocalDateTime.now().plusDays(3));
        Recordatorio r3 = new Recordatorio(otroEstudianteId, "Otro estudiante", LocalDateTime.now().plusDays(1));

        repository.adicionar(r1);
        repository.adicionar(r2);
        repository.adicionar(r3);

        // Act
        List<Recordatorio> recordatoriosEstudiante = repository.listarPorEstudiante(estudianteId);

        // Assert
        assertNotNull(recordatoriosEstudiante);
        assertEquals(2, recordatoriosEstudiante.size());
        assertTrue(recordatoriosEstudiante.stream().allMatch(r -> r.getEstudianteId().equals(estudianteId)));
    }
}
