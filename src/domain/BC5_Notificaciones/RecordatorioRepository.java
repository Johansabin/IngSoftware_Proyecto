package BC5_Notificaciones;

import java.util.List;
import java.util.UUID;

/**
 * Puerto del dominio para persistir Recordatorio.
 * Firma alineada con el diagrama de clases (MenteenCasa.png).
 */
public interface RecordatorioRepository {

    void adicionar(Recordatorio recordatorio);

    void eliminar(UUID id);

    Recordatorio buscar(UUID id);

    List<Recordatorio> listarPorEstudiante(UUID estudianteId);
}
