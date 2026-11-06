# Reporte de Ejecución de Pruebas — Laboratorio 05

**Proyecto:** MenteEnCasa · **Curso:** Ingeniería de Software II (UNSA) · **Rama:** `development`  
**Entorno:** Java 21, Spring Boot 3.3.2, JUnit 5, Mockito, Maven Surefire 3.2.5 (`mvn clean test`) · **Fecha:** 2026-11-05

## 1. Resultado global: APROBADO

| Alcance | Clases de prueba | Pruebas | Aprobadas | Fallos | Errores | Omitidas | Tiempo |
|---|---:|---:|---:|---:|---:|---:|---:|
| Proyecto completo | 24 | 147 | 147 | 0 | 0 | 0 | 6.40 s |

## 2. Pruebas por módulo

| Módulo | Clases de prueba | Pruebas | Aprobadas | Fallos | Errores | Omitidas | Tiempo |
|---|---:|---:|---:|---:|---:|---:|---:|
| BC1 Autenticación | 6 | 33 | 33 | 0 | 0 | 0 | 5.66 s |
| BC2 Seguimiento Emocional | 8 | 52 | 52 | 0 | 0 | 0 | 0.29 s |
| BC4 Recomendaciones | 3 | 13 | 13 | 0 | 0 | 0 | 0.06 s |
| BC5 Notificaciones | 2 | 6 | 6 | 0 | 0 | 0 | 0.17 s |
| BC6 Privacidad y Seguridad | 5 | 43 | 43 | 0 | 0 | 0 | 0.22 s |

> Sin clases de prueba al momento de este reporte: BC3 Soporte Chat (sus pruebas corresponden a los issues #12 a #14, de otro integrante).

## 3. Pruebas por capa (regla 3x1)

| Capa | Clases de prueba | Pruebas | Aprobadas | Fallos | Errores | Omitidas | Tiempo |
|---|---:|---:|---:|---:|---:|---:|---:|
| Dominio | 16 | 90 | 90 | 0 | 0 | 0 | 2.41 s |
| Repositorio (SetUp/TearDown) | 5 | 34 | 34 | 0 | 0 | 0 | 0.15 s |
| Servicio de aplicación (Mocks) | 2 | 18 | 18 | 0 | 0 | 0 | 0.22 s |
| Presentación | 1 | 5 | 5 | 0 | 0 | 0 | 3.63 s |

## 4. Detalle por clase de prueba

| Módulo | Capa | Clase de prueba | Pruebas | Resultado | Tiempo |
|---|---|---|---:|---|---:|
| BC1 Autenticación | Dominio | `BC1_Autenticacion.AutenticacionServiceTest` | 7 | OK | 1.91 s |
| BC1 Autenticación | Dominio | `BC1_Autenticacion.CredencialesInstitucionalesTest` | 5 | OK | 0.04 s |
| BC1 Autenticación | Dominio | `BC1_Autenticacion.DatosAuditoriaTest` | 3 | OK | 0.01 s |
| BC1 Autenticación | Dominio | `BC1_Autenticacion.SesionTest` | 5 | OK | 0.02 s |
| BC1 Autenticación | Presentación | `presentation.BC1_Autenticacion.AutenticacionControllerTest` | 5 | OK | 3.63 s |
| BC1 Autenticación | Repositorio (SetUp/TearDown) | `BC1_Autenticacion.SesionRepositoryTest` | 8 | OK | 0.05 s |
| BC2 Seguimiento Emocional | Dominio | `BC2_SeguimientoEmocional.AnalisisEmocionalServiceTest` | 4 | OK | 0.04 s |
| BC2 Seguimiento Emocional | Dominio | `BC2_SeguimientoEmocional.BitacoraEmocionalFactoryTest` | 2 | OK | 0.01 s |
| BC2 Seguimiento Emocional | Dominio | `BC2_SeguimientoEmocional.BitacoraEmocionalTest` | 10 | OK | 0.02 s |
| BC2 Seguimiento Emocional | Dominio | `BC2_SeguimientoEmocional.EmocionTest` | 6 | OK | 0.04 s |
| BC2 Seguimiento Emocional | Dominio | `BC2_SeguimientoEmocional.EscalaEmocionalTest` | 5 | OK | 0.01 s |
| BC2 Seguimiento Emocional | Dominio | `BC2_SeguimientoEmocional.ResumenSemanalTest` | 4 | OK | 0.01 s |
| BC2 Seguimiento Emocional | Repositorio (SetUp/TearDown) | `BC2_SeguimientoEmocional.InMemoryBitacoraEmocionalRepositoryTest` | 11 | OK | 0.04 s |
| BC2 Seguimiento Emocional | Servicio de aplicación (Mocks) | `application.BC2_SeguimientoEmocional.BitacoraEmocionalAppServiceImplTest` | 10 | OK | 0.12 s |
| BC4 Recomendaciones | Dominio | `BC4_Recomendaciones.RecomendacionServiceTest` | 4 | OK | 0.02 s |
| BC4 Recomendaciones | Dominio | `BC4_Recomendaciones.RecomendacionTest` | 5 | OK | 0.02 s |
| BC4 Recomendaciones | Repositorio (SetUp/TearDown) | `BC4_Recomendaciones.InMemoryRecomendacionRepositoryTest` | 4 | OK | 0.02 s |
| BC5 Notificaciones | Dominio | `BC5_Notificaciones.RecordatorioTest` | 3 | OK | 0.15 s |
| BC5 Notificaciones | Repositorio (SetUp/TearDown) | `BC5_Notificaciones.RecordatorioRepositoryTest` | 3 | OK | 0.01 s |
| BC6 Privacidad y Seguridad | Dominio | `BC6_PrivacidadSeguridad.ConsentimientoTest` | 5 | OK | 0.01 s |
| BC6 Privacidad y Seguridad | Dominio | `BC6_PrivacidadSeguridad.DatosAuditoriaTest` | 3 | OK | 0.00 s |
| BC6 Privacidad y Seguridad | Dominio | `BC6_PrivacidadSeguridad.PoliticaPrivacidadTest` | 19 | OK | 0.09 s |
| BC6 Privacidad y Seguridad | Repositorio (SetUp/TearDown) | `BC6_PrivacidadSeguridad.InMemoryPoliticaPrivacidadRepositoryTest` | 8 | OK | 0.02 s |
| BC6 Privacidad y Seguridad | Servicio de aplicación (Mocks) | `application.BC6_PrivacidadSeguridad.PoliticaPrivacidadAppServiceImplTest` | 8 | OK | 0.10 s |

## 5. Evidencia por issue (Luis Ramos)

| Issue | Alcance | Clases de prueba | Pruebas | Resultado |
|---|---|---:|---:|---|
| #7 | Dominio, Módulo 2 | 6 | 31 | OK |
| #8 | Repositorio, Módulo 2 | 1 | 11 | OK |
| #9 | Servicio de aplicación con Mocks, Módulo 2 | 1 | 10 | OK |
| #10 | 3 capas, Módulo 6 | 5 | 43 | OK |

## 6. Cómo reproducir

```bash
mvn clean test
# Resultados: target/surefire-reports/*.txt y TEST-*.xml
```
