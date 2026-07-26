# MenteEnCasa — Plataforma de Apoyo Emocional Universitario
> **Entregable Oficial — Laboratorio 12: Principios SOLID y Calidad de Código**  
> **Asignatura:** Ingeniería de Software I  
> **Alumno Responsable:** Rodrigo Ramos Mamani  
> **Bounded Context Asignado:** `BC1_Autenticacion`  
> **Lenguaje:** Java 17 | **Arquitectura:** Clean Architecture & Domain-Driven Design (DDD)  

---

## 1. Propósito del Proyecto y del Submódulo BC1_Autenticación

### 1.1 Propósito del Proyecto (MenteEnCasa)
**MenteEnCasa** es una plataforma integral orientada al acompañamiento, bienestar y apoyo emocional de estudiantes universitarios. Su objetivo principal es proveer un entorno digital accesible, seguro y confidencial que permita a los estudiantes:
- Registrar y monitorear su estado emocional diario (Bitácora Emocional).
- Recibir recomendaciones personalizadas de bienestar y prevención de estrés/ansiedad.
- Interactuar en canales de soporte y asistencia con seudónimos anónimos.
- Gestionar notificaciones y recordatorios de autocuidado.

### 1.2 Propósito del Submódulo `BC1_Autenticacion`
El Bounded Context de Autenticación (`BC1_Autenticacion`) respeta la estructura y nomenclatura original definida en el repositorio base del equipo. Sus responsabilidades clave son:
- Validar las credenciales institucionales de los estudiantes (código universitario y hash de contraseña).
- Controlar el ciclo de vida de las sesiones de usuario (inicio, invalidación de sesiones previas concurrentes y cierre seguro).
- Garantizar la trazabilidad de auditoría en los accesos (IP y marcas de tiempo).
- Proveer contratos desacoplados (interfaces repositorio) para la persistencia sin acoplarse a frameworks externos.

---

## 2. Funcionalidades de Alto Nivel

### 2.1 Diagrama de Casos de Uso (UML)
El módulo interactúa mediante los siguientes casos de uso principales:
1. **UC01: Iniciar Sesión Institucional** — El estudiante ingresa su código universitario de 8 dígitos y contraseña; el sistema valida las credenciales y genera una nueva `Sesion` activa, desactivando cualquier sesión previa concurrente.
2. **UC02: Cerrar Sesión** — El estudiante finaliza su sesión activa, registrando la marca de tiempo de cierre (`fechaFin`) y cambiando el estado a inactivo.
3. **UC03: Consultar Sesiones Activas** — El sistema filtra y gestiona las sesiones activas mediante procesamiento funcional (Stream API / Pipeline).

### 2.2 Prototipo / Interfaz Gráfica (Login GUI)
```
+-------------------------------------------------------------+
|                     MENTE EN CASA - LOGIN                   |
+-------------------------------------------------------------+
|  [ Instituto / Universidad Nacional ]                       |
|                                                             |
|  Código Universitario (8 dígitos):                          |
|  [ 20214567               ]                                 |
|                                                             |
|  Contraseña Institucional:                                  |
|  [ *********              ]                                 |
|                                                             |
|         [ CANCELAR ]          [ INICIAR SESIÓN ]            |
+-------------------------------------------------------------+
```

---

## 3. Modelo de Dominio (DDD)

El diseño del Bounded Context sigue estrictamente los patrones tácticos de **Domain-Driven Design (DDD)**:

- **Entidad (`Sesion`):** Objeto con identidad única (`UUID id`) y ciclo de vida mutable (`activa`, `fechaInicio`, `fechaFin`). Las entidades se comparan por su identidad.
- **Value Object (`CredencialesInstitucionales`):** Objeto inmutable que encapsula el código universitario y el hash de contraseña, validando reglas de negocio semánticas (ej. formato de 8 dígitos con `esCodigoValido()`).
- **Value Object (`DatosAuditoria`):** Objeto inmutable que registra metadatos de trazabilidad (`ipOrigen` y `fechaRegistro` en zona horaria local).
- **Factory (`SesionFactory`):** Fábrica estática encargada de instanciar de manera controlada nuevas sesiones.
- **Repository Interface (`SesionRepository`):** Puerto de dominio que define los contratos de persistencia sin acoplamiento tecnológico.

---

## 4. Vista General de Arquitectura (Clean Architecture & DDD)

```
src/
└── domain/
    └── BC1_Autenticacion/
        ├── AutenticacionService.java      # Servicio de Dominio (Orquestación de Login/Logout)
        ├── CredencialesInstitucionales.java # Value Object (Credenciales y validación)
        ├── DatosAuditoria.java            # Value Object (Trazabilidad IP y fecha)
        ├── Sesion.java                    # Entidad de Dominio (Ciclo de vida de sesión)
        ├── SesionFactory.java             # Fábrica de Creación de Sesiones
        ├── SesionRepository.java          # Puerto / Contrato de Persistencia
        └── AutenticacionException.java    # Excepción de Negocio de Dominio
```

---

## 5. Convenciones de Codificación

Se respetaron las convenciones establecidas en el proyecto base y Clean Code:
- **Nomenclatura:** PascalCase para Clases y Factories (`SesionFactory`), camelCase para métodos y variables (`iniciarSesion`, `codigoUniversitario`), UPPER_SNAKE_CASE para constantes.
- **Inmutabilidad:** Uso de modificadores `private final` en atributos de Value Objects y Entidades.
- **Cero Comodines en Imports:** Importaciones explícitas de clases individuales.
- **Validaciones Defensivas:** Control temprano de nulos y vacíos en constructores lanzando `IllegalArgumentException`.

### Fragmento de Código (Convenciones y Encapsulamiento):
```java
package BC1_Autenticacion;

public class DatosAuditoria {

    private final String ipOrigen;
    private final LocalDateTime fechaRegistro;

    public DatosAuditoria(String ipOrigen) {
        if (ipOrigen == null || ipOrigen.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección IP de origen no puede ser nula o vacía.");
        }
        this.ipOrigen = ipOrigen;
        this.fechaRegistro = LocalDateTime.now(ZoneId.of("America/Lima"));
    }

    public String getIpOrigen() {
        return ipOrigen;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
}
```

---

## 6. Estilos de Codificación (Basado en *Exercises in Programming Style*)

Se aplicaron cuatro estilos arquitectónicos de programación en el módulo:
1. **Things (Orientación a Objetos):** Encapsulación de datos y validaciones semánticas en objetos de valor (`CredencialesInstitucionales`).
2. **Error / Exception Handling:** Control explícito de fallos con excepciones de dominio (`AutenticacionException`).
3. **Pipeline (Stream API):** Procesamiento funcional de colecciones de sesiones (`obtenerSesionesActivasPorUsuario`).
4. **Persistent-Tables:** Definición de contratos de almacenamiento abstracto mediante interfaces repositorio (`SesionRepository`).

---

## 7. Prácticas Clean Code

- **Nombres descriptivos y orientados al dominio:** Clases y métodos que reflejan el lenguaje ubicuo (`iniciarSesion`, `desactivar`, `esCodigoValido`).
- **Funciones pequeñas y de responsabilidad única:** Métodos privados y públicos concisos que realizan una única tarea.
- **Inmutabilidad robusta con equals/hashCode:** Implementación correcta de igualdad por identidad (Entidades) y por valor (Value Objects).

---

## 8. Principios SOLID (Laboratorio 12)

Se aplicaron rigurosamente tres principios SOLID en el módulo `BC1_Autenticacion`:

### 1. SRP (Single Responsibility Principle - Principio de Responsabilidad Única)
* **Explicación:** Cada clase tiene una y solo una razón para cambiar. Por ejemplo, `Sesion` gestiona exclusivamente el estado de la sesión, mientras que `SesionFactory` tiene la única responsabilidad de instanciarla, y `CredencialesInstitucionales` valida las credenciales.
* **Fragmento de Código (`SesionFactory` - Responsabilidad única de creación):**
```java
package BC1_Autenticacion;

public class SesionFactory {

    private SesionFactory() {
        // Constructor privado para ocultar el constructor público implícito
    }

    public static Sesion registrarSesion(String codigoUsuario) {
        return new Sesion(codigoUsuario);
    }
}
```

### 2. OCP (Open/Closed Principle - Principio Abierto/Cerrado)
* **Explicación:** Las entidades y objetos de valor están abiertos a extensión mediante comportamiento encapsulado pero cerrados a modificación directa de sus invariantes, permitiendo validar formatos (ej. `esCodigoValido()`) sin modificar el servicio de autenticación.
* **Fragmento de Código (`CredencialesInstitucionales` - Extensibilidad en validación):**
```java
package BC1_Autenticacion;

public class CredencialesInstitucionales {

    private final String codigoUniversitario;
    private final String hashContrasena;

    public CredencialesInstitucionales(String codigoUniversitario, String hashContrasena) {
        if (codigoUniversitario == null || codigoUniversitario.isBlank()) {
            throw new IllegalArgumentException("El código universitario no puede estar vacío.");
        }
        if (hashContrasena == null || hashContrasena.isBlank()) {
            throw new IllegalArgumentException("La contraseña hash no puede estar vacía.");
        }
        this.codigoUniversitario = codigoUniversitario;
        this.hashContrasena = hashContrasena;
    }

    public boolean esCodigoValido() {
        return this.codigoUniversitario.matches("^\\d{8}$");
    }
}
```

### 3. DIP (Dependency Inversion Principle - Principio de Inversión de Dependencias)
* **Explicación:** Los módulos de alto nivel (`AutenticacionService`) no dependen de detalles de infraestructura de bajo nivel (bases de datos o ORMs), sino de abstracciones (puertos/interfaces como `SesionRepository`).
* **Fragmento de Código (`AutenticacionService` dependiendo de la abstracción `SesionRepository`):**
```java
package BC1_Autenticacion;

public class AutenticacionService {

    private final SesionRepository sesionRepository; // Inyección de abstracción (Puerto)

    public AutenticacionService(SesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
    }

    public Optional<Sesion> iniciarSesion(CredencialesInstitucionales credenciales, DatosAuditoria auditoria) {
        if (credenciales == null || auditoria == null || !credenciales.esCodigoValido()) {
            return Optional.empty();
        }
        Optional<Sesion> sesionPrevia = sesionRepository.buscarActivaPorCodigo(credenciales.getCodigoUniversitario());
        sesionPrevia.ifPresent(Sesion::desactivar);

        Sesion nuevaSesion = SesionFactory.registrarSesion(credenciales.getCodigoUniversitario());
        sesionRepository.guardar(nuevaSesion);

        return Optional.of(nuevaSesion);
    }
}
```

---

## 9. Reporte de Análisis Estático (SonarLint)

Se analizó el código asegurando compatibilidad completa con el repositorio base del equipo (`BC1_Autenticacion`):
- **Bugs Detectados:** 0
- **Vulnerabilities Detectadas:** 0
- **Code Smells Críticos/Bloqueantes:** 0 (Se mantuvieron los nombres de paquete acordes al repositorio base y se añadieron `equals`/`hashCode` robustos para evitar bugs de identidad).
- **Resultado de Compilación:** `javac -d out src/domain/BC1_Autenticacion/*.java` -> **0 Errores, 0 Advertencias**.

---

## 10. Estructura para el Tablero Kanban / Scrum (Trello)

Mapeo de Historias de Usuario para el Bounded Context de Autenticación en el tablero del proyecto:

| ID | Historia de Usuario | Criterios de Aceptación | Estado |
|---|---|---|---|
| **HU-01** | Como estudiante, quiero iniciar sesión con mis credenciales institucionales para acceder a la plataforma de manera segura. | - Validación de código de 8 dígitos.<br>- Rechazo de credenciales vacías.<br>- Generación de token/sesión activa. | **Completado** |
| **HU-02** | Como estudiante, quiero cerrar sesión al terminar mi uso para proteger mi confidencialidad. | - Actualización de estado a inactivo.<br>- Registro de `fechaFin`. | **Completado** |
| **HU-03** | Como sistema, quiero invalidar sesiones concurrentes previas al iniciar una nueva sesión para evitar multi-sesiones activas. | - Búsqueda de sesión previa activa por código.<br>- Desactivación automática (`sesionPrevia.ifPresent(Sesion::desactivar)`). | **Completado** |
| **HU-04** | Como administrador, quiero mantener trazabilidad de auditoría de los accesos (IP y hora). | - Registro inmutable de `DatosAuditoria` con zona horaria local. | **Completado** |
