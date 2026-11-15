# MenteEnCasa — Plataforma de Apoyo Emocional Universitario
> **Módulo / Bounded Context:** `BC1_Autenticacion`  
> **Alumno Responsable:** Ramos Mamani, Rodrigo Abraham  
> **Curso:** Ingeniería de Software I — Proyecto Final 2026-B  
> **Lenguaje:** Java 17 | **Framework:** Spring Boot 3.3.2 | **Arquitectura:** Clean Architecture & Domain-Driven Design (DDD)

---

## Índice

1. [Propósito del Submódulo BC1_Autenticación](#1-propósito-del-submódulo-bc1_autenticación)
2. [Funcionalidades de Alto Nivel y Casos de Uso](#2-funcionalidades-de-alto-nivel-y-casos-de-uso)
3. [Endpoints REST de Autenticación](#3-endpoints-rest-de-autenticación)
4. [Tecnologías Usadas](#4-tecnologías-usadas)
5. [Modelo de Dominio (DDD)](#5-modelo-de-dominio-ddd)
6. [Vista General de Arquitectura](#6-vista-general-de-arquitectura)
7. [Justificación del Pipeline CI/CD (Jenkinsfile)](#7-justificación-del-pipeline-cicd-jenkinsfile)
8. [Ejecución del Contenedor Docker](#8-ejecución-del-contenedor-docker)
9. [Principios SOLID y Prácticas Clean Code](#9-principios-solid-y-prácticas-clean-code)
10. [Reporte de Pruebas y Análisis Estático](#10-reporte-de-pruebas-y-análisis-estático)

---

## 1. Propósito del Submódulo `BC1_Autenticacion`

El Bounded Context de Autenticación (`BC1_Autenticacion`) es el encargado de gestionar el acceso seguro de los estudiantes a la plataforma **MenteEnCasa**. Sus responsabilidades principales son:
- **Validación de Credenciales Institucionales:** Verificación semántica y formal del código universitario (8 dígitos) y contraseña cifrada.
- **Gestión del Ciclo de Vida de Sesiones:** Registro de inicios de sesión, invalidación automática de sesiones previas concurrentes y cierre seguro de sesión.
- **Trazabilidad y Auditoría:** Registro de metadatos de acceso (dirección IP de origen y marcas de tiempo con zona horaria local).
- **Desacoplamiento Tecnológico:** Definición de contratos mediante puertos (`SesionRepository`) cumpliendo con Clean Architecture.

---

## 2. Funcionalidades de Alto Nivel y Casos de Uso

1. **UC01: Iniciar Sesión Institucional** — El estudiante ingresa sus credenciales; el sistema valida el formato institucional, audita el acceso y genera una `Sesion` activa cerrando cualquier sesión concurrente previa.
2. **UC02: Cerrar Sesión** — El estudiante finaliza su sesión, registrando la fecha de cierre (`fechaFin`) y cambiando el estado a inactivo.
3. **UC03: Consulta de Sesiones** — Filtrado y gestión de sesiones activas mediante procesamiento funcional (Stream API / Pipeline).

### Prototipo de Interfaz (Login GUI)
```
+-------------------------------------------------------------+
|                     MENTE EN CASA - LOGIN                   |
+-------------------------------------------------------------+
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

## 3. Endpoints REST de Autenticación

El módulo expone una API RESTful a través de `AutenticacionController` bajo el prefijo `/api/v1/auth`:

### 3.1. Iniciar Sesión
* **Método HTTP:** `POST`
* **Ruta:** `/api/v1/auth/login`
* **Descripción:** Autentica al estudiante utilizando su código universitario y contraseña. Si es exitoso, retorna la sesión activa creada.

**Request Body (`LoginRequest`):**
```json
{
  "codigoUniversitario": "20214567",
  "contrasena": "hashSeguro123",
  "ipOrigen": "192.168.1.15"
}
```

**Response Success (`200 OK`):**
```json
{
  "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
  "codigoUsuario": "20214567",
  "fechaInicio": "2026-10-12T10:00:00",
  "fechaFin": null,
  "activa": true
}
```

**Responses Error:**
* `400 Bad Request`: Si el request es nulo, faltan campos obligatorios o el código universitario no cumple con el formato de 8 dígitos (`^\d{8}$`).
* `401 Unauthorized`: Si las credenciales no son válidas o la autenticación falla.

---

### 3.2. Cerrar Sesión
* **Método HTTP:** `POST`
* **Ruta:** `/api/v1/auth/logout`
* **Descripción:** Invalida una sesión activa mediante su identificador único de sesión (`sessionId`).

**Request Body (`LogoutRequest`):**
```json
{
  "sessionId": "f47ac10b-58cc-4372-a567-0e02b2c3d479"
}
```

**Response Success (`200 OK`):**
* Sin contenido (Empty body), indicando que la sesión fue cerrada exitosamente.

**Responses Error:**
* `400 Bad Request`: Si el request o `sessionId` es nulo.
* `404 Not Found`: Si la sesión especificada no existe en el sistema.

---

## 4. Tecnologías Usadas

El módulo está construido utilizando un stack tecnológico moderno y robusto:
* **Lenguaje:** Java 17 (Con soporte LTS, inmutabilidad y características avanzadas del lenguaje).
* **Framework Principal:** Spring Boot 3.3.2 (Inyección de dependencias, controladores REST, auto-configuración).
* **Gestión de Dependencias y Build:** Apache Maven 3.9+.
* **Pruebas Unitarias y Mocking:** JUnit 5 y Mockito.
* **Cobertura de Código:** JaCoCo Maven Plugin.
* **Contenedorización:** Docker (Multi-stage builds con `eclipse-temurin:17-jre-jammy`).
* **CI/CD Pipeline:** Jenkins (automatización de compilación, pruebas, análisis estático y seguridad).
* **Análisis Estático de Código:** SonarQube / SonarScanner.
* **Pruebas Automatizadas Complementarias:** Postman / Newman (Funcionales), Apache JMeter (Rendimiento), OWASP ZAP (Seguridad).

---

## 5. Modelo de Dominio (DDD)

El diseño respeta los patrones tácticos de **Domain-Driven Design**:
* **Entidad (`Sesion`):** Objeto con identidad única (`UUID id`) y estado mutable controlado (`activa`, `fechaInicio`, `fechaFin`).
* **Value Object (`CredencialesInstitucionales`):** Objeto inmutable que encapsula el código universitario y contraseña, autovalidando su formato institucional.
* **Value Object (`DatosAuditoria`):** Encapsula metadatos de trazabilidad (`ipOrigen` y `fechaRegistro`).
* **Factory (`SesionFactory`):** Fábrica estática encargada de instanciar de forma controlada nuevas sesiones.
* **Repository Interface (`SesionRepository`):** Puerto de dominio que desacopla la lógica de negocio de los detalles de persistencia.

---

## 6. Vista General de Arquitectura

El proyecto implementa **Clean Architecture** organizado en capas:
- **`presentation`**: Controladores REST (`AutenticacionController`, DTOs).
- **`application`**: Casos de uso y servicios de aplicación.
- **`domain`**: Entidades, Value Objects, Factories y contratos de Repositorios (Independiente de frameworks).
- **`infrastructure`**: Implementaciones concretas de persistencia y adaptadores.

---

## 7. Justificación del Pipeline CI/CD (`Jenkinsfile`)

El archivo `Jenkinsfile` ubicado en la raíz del proyecto automatiza el ciclo de vida de integración continua y entrega continua (CI/CD) para garantizar la calidad del software antes de llevarlo a producción:

1. **`Git Checkout`**: Obtiene la última versión del código fuente desde el repositorio en cada evento de commit.
2. **`Build & Unit Tests (Maven)`**: Ejecuta `mvn clean package`, compilando el código, ejecutando todas las pruebas unitarias y generando los reportes de cobertura JaCoCo.
3. **`Static Analysis (SonarQube)`**: Ejecuta `sonar-scanner` para detectar bugs, vulnerabilidades, code smells y asegurar la mantenibilidad del código.
4. **`Docker Build & Run (Deployment)`**: Construye la imagen Docker optimizada del microservicio (`menteencasa-bc1:latest`) y levanta un contenedor temporal en el puerto `8080`.
5. **`Functional Tests (Newman/Postman)`**: Valida el comportamiento correcto de los endpoints REST expuestos ejecutando colecciones de pruebas funcionales automatizadas.
6. **`Performance Tests (JMeter)`**: Ejecuta pruebas de estrés y rendimiento sobre la aplicación en ejecución.
7. **`Security Tests (OWASP ZAP)`**: Realiza un escaneo automatizado de vulnerabilidades web sobre el contenedor desplegado.
8. **`Post Actions`**: Bloque seguro `always` que detiene y elimina automáticamente el contenedor de prueba (`docker stop` / `docker rm`), y `failure` para notificar errores al equipo mediante el Kanban (GitHub Projects / Trello).

---

## 8. Ejecución del Contenedor Docker

Gracias al archivo **Dockerfile optimizado (multi-stage)** con Java 17, puedes compilar y ejecutar la aplicación en cualquier entorno contenedorizado sin necesidad de tener Maven ni JDK instalados localmente.

### 8.1. Construir la imagen Docker
Ejecuta el siguiente comando en la raíz del proyecto:
```bash
docker build -t menteencasa-bc1:latest .
```

### 8.2. Ejecutar el contenedor
Levanta el contenedor exponiendo el puerto `8080` de la aplicación:
```bash
docker run -d -p 8080:8080 --name app-bc1 menteencasa-bc1:latest
```

### 8.3. Verificar el funcionamiento
Puedes realizar una petición de prueba al endpoint de login mediante `curl`:
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"codigoUniversitario":"20214567","contrasena":"hashSeguro123","ipOrigen":"127.0.0.1"}'
```

### 8.4. Detener y eliminar el contenedor
```bash
docker stop app-bc1
docker rm app-bc1
```

---

## 9. Principios SOLID y Prácticas Clean Code

- **SRP (Responsabilidad Única):** Clases cohesivas donde `Sesion` maneja el estado, `SesionFactory` la creación y `CredencialesInstitucionales` la validación.
- **OCP (Abierto/Cerrado):** Reglas de validación encapsuladas en value objects extensibles sin alterar el servicio de autenticación.
- **DIP (Inversión de Dependencias):** El servicio de autenticación depende exclusivamente de la abstracción `SesionRepository`.
- **Clean Code:** Uso riguroso de inmutabilidad (`private final`), validaciones tempranas con `Objects.requireNonNull`, manejo de `Optional` en lugar de nulos y nombres basados en el lenguaje ubicuo del dominio.

---

## 10. Reporte de Pruebas y Análisis Estático

- **Pruebas Unitarias:** Cobertura completa de casos de éxito y fallo para controladores, servicios y value objects.
- **SonarQube / SonarLint:** 0 Bugs, 0 Vulnerabilidades y 0 Code Smells bloqueantes.
- **Compilación:** Verificada exitosamente con Java 17 y Spring Boot 3.3.2.
