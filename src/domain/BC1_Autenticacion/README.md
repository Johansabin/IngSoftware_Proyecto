# MenteEnCasa — Plataforma de Apoyo Emocional Universitario

> **Proyecto Final de Curso — Ingeniería de Software I**  
> **Alumno Responsable:** Rodrigo Ramos Mamani  
> **Sistema Operativo:** Ubuntu Linux | **IDE:** Visual Studio Code | **Lenguaje:** Java  
> **Bounded Context Asignado:** `BC1_Autenticacion`  

---

## I. Propósito del Sistema

**MenteEnCasa** es una plataforma orientada al acompañamiento y apoyo emocional de estudiantes universitarios. Su objetivo principal es brindar una herramienta digital accesible que permita a los estudiantes monitorear su estado emocional, registrar interacciones de bienestar, gestionar alertas tempranas de estrés o ansiedad y acceder a recursos institucionales de ayuda en un entorno seguro y confidencial.

---

## II. Funcionalidades del Sistema

### 1. Funcionalidades de Alto Nivel (Casos de Uso UML)

El sistema integra los siguientes módulos principales de interacción:
* **Autenticación e Identidad (`BC1_Autenticacion`):** Gestión segura de inicio de sesión, validación de credenciales institucionales y control inmutable de sesiones activas.
* **Seguimiento Emocional (`BC2_SeguimientoEmocional`):** Registro diario del estado de ánimo e historial de bitácoras.
* **Soporte y Chat (`BC3_SoporteChat`):** Canal directo de interacción y asistencia en línea.
* **Recomendaciones de Bienestar (`BC4_Recomendaciones`):** Generación personalizada de actividades preventivas.

### 2. Prototipo / Interfaz Gráfica (GUI)

La arquitectura expone prototipos de interacción orientados a reducir la carga cognitiva del estudiante, garantizando un flujo intuitivo desde el portal de acceso seguro hasta los tableros de control personal.

---

## III. Modelo de Dominio (DDD)

El modelo sigue los principios de **Domain-Driven Design**, aislando la lógica de negocio del entorno de infraestructura:

### Diagrama de Clases del Bounded Context (`BC1_Autenticacion`)

* **`Sesion` (Entity):** Controla el estado (`activa`), identificador inmutable (`UUID`) y ventana temporal (`fechaInicio`, `fechaFin`).
* **`CredencialesInstitucionales` (Value Object):** Encapsula el código universitario y el hash de contraseña sin identidad persistente propia.
* **`DatosAuditoria` (Value Object):** Mantiene la trazabilidad inmutable (`ipOrigen`, `fechaRegistro`).
* **`SesionRepository` (Interface):** Contrato que define la persistencia del agregado sin acoplamiento a librerías externas.

---

## IV. Vista General de la Arquitectura

Se adoptó una **Arquitectura Hexagonal (Puertos y Adaptadores) acoplada a DDD**:

## V. Convenciones de Codificación (Práctica 9)

Durante la implementación se aplicaron las convenciones oficiales de desarrollo para el lenguaje **Java** y estándares de **Clean Code**:

* **Estructura de Nombres:** PascalCase para clases y fábricas (`SesionFactory`), camelCase para métodos y atributos (`registrarSesion`).
* **Inmutabilidad y Encapsulamiento Estricto:** Declaración explícita de campos mediante `private final`.
* **Cero Imports con Comodines:** Uso exclusivo de importaciones explícitas de clases (evitando `import java.util.*`).
* **Validaciones Defensivas:** Verificación previa de referencias nulas o vacías en constructores mediante excepciones estándar.

---

## VI. Estilos de Codificación (Práctica 10)

Basado en el libro *Exercises in Programming Style* (Crista Lopes, 2020) y su repositorio oficial ([crista/exercises-in-programming-style](https://github.com/crista/exercises-in-programming-style)), se aplicaron **cuatro estilos de programación** en el núcleo del módulo:

### 1. Estilo: Things (Orientación a Objetos y Encapsulación)
Encapsula datos y reglas de validación semántica en un Value Object inmutable.

```java
package BC1_Autenticacion;

/** 
 * Objeto de valor que representa las credenciales de acceso de un estudiante.
 */
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

    /**
     * Valida si el código universitario cumple con el formato requerido de 8 dígitos.
     */
    public boolean esCodigoValido() {
        return this.codigoUniversitario.matches("^\\d{8}$");
    }

    public String getCodigoUniversitario() { return codigoUniversitario; }
    public String getHashContrasena() { return hashContrasena; }
}

### 2. Estilo: Error / Exception Handling
Gestión explícita del flujo de ejecución anómala mediante excepciones de dominio personalizadas.

```java
package BC1_Autenticacion;

/**
 * Excepción de dominio para controlar errores explícitos durante la autenticación.
 */
public class AutenticacionException extends Exception {

    private static final long serialVersionUID = 1L;

    public AutenticacionException(String mensaje) {
        super(mensaje);
    }
}

### 3. Estilo: Pipeline (Method Chaining / Stream API)
Procesamiento funcional y secuencial de colecciones sin alterar el estado original.

```java
package BC1_Autenticacion;

import java.util.Collections;
import java.util.List;

public class AutenticacionService {

    private final SesionRepository sesionRepository;

    public AutenticacionService(SesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
    }

    /**
     * Aplica el estilo Pipeline (Stream API) para filtrar sesiones activas.
     */
    public List<Sesion> obtenerSesionesActivasPorUsuario(List<Sesion> listaSesiones, String codigoUsuario) {
        if (listaSesiones == null || codigoUsuario == null) {
            return Collections.emptyList();
        }
        return listaSesiones.stream()
                .filter(Sesion::isActiva)
                .filter(s -> s.getCodigoUsuario().equals(codigoUsuario))
                .toList();
    }
}

### 4. Estilo: Persistent-Tables
Modelado de la capa de almacenamiento mediante un contrato de interfaz puro tipo repositorio.

```java
package BC1_Autenticacion;

import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de interfaz (Repositorio) para persistir, actualizar y buscar sesiones en el sistema.
 */
public interface SesionRepository {

    void guardar(Sesion sesion);

    Optional<Sesion> buscarPorId(UUID id);

    Optional<Sesion> buscarActivaPorCodigo(String codigoUsuario);
}

VII. Análisis Estático y Control de Calidad
SonarLint Analysis: Se realizó el escaneo estático en Visual Studio Code sobre todos los componentes del paquete BC1_Autenticacion, obteniendo 0 Bugs, 0 Vulnerabilities y 0 Code Smells (Severidades Blocker y Critical totalmente subsanadas).

Verificación de Compilación (Ubuntu Linux):

Bash
javac -d out -Xlint:all src/domain/BC1_Autenticacion/*.java
Resultado: Compilación exitosa (0 errors, 0 warnings).

VIII. Trazabilidad de Versionado (Git Commits)
El avance de esta práctica fue registrado de manera atómica mediante commits independientes en la rama BoundedContext-1:

feat(style): apply Things style to CredencialesInstitucionales

feat(style): apply Error Handling style with custom AutenticacionException

feat(style): apply Pipeline style using Java Streams in AutenticacionService

style(clean-code): enforce Persistent-Tables style and resolve SonarLint warnings

IX. Referencias
Evans, E. (2003). Domain-Driven Design: Tackling Complexity in the Heart of Software. Addison-Wesley.

Lopes, C. V. (2020). Exercises in Programming Style. Chapman and Hall/CRC.

Repositorio Oficial de Ejemplos de Estilos: https://github.com/crista/exercises-in-programming-style

