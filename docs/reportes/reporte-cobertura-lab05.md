# Reporte de Code Coverage — Laboratorio 05

**Proyecto:** MenteEnCasa · **Curso:** Ingeniería de Software II (UNSA) · **Rama:** `development`  
**Herramienta:** JaCoCo 0.8.15 (`mvn clean test`, reporte en `target/site/jacoco`) · **Fecha:** 2026-11-05

## 1. Resumen global

| Alcance | Instrucciones | Ramas | Líneas | Métodos | Líneas cubiertas |
|---|---:|---:|---:|---:|---:|
| Proyecto completo (62 clases) | 56.1% | 70.0% | 56.7% | 54.1% | 479/845 |

> El total incluye `presentation`, BC3 y la capa de aplicación de BC4, que no tienen pruebas en `development` al momento de este reporte; por eso el porcentaje global es menor que el de los módulos con pruebas completas. Los módulos se miden por separado más abajo.

## 2. Cobertura por módulo

| Módulo | Instrucciones | Ramas | Líneas | Métodos | Líneas cubiertas |
|---|---:|---:|---:|---:|---:|
| BC1 Autenticación | 90.4% | 83.3% | 92.9% | 96.1% | 131/141 |
| BC2 Seguimiento Emocional | 91.8% | 75.9% | 92.6% | 89.5% | 212/229 |
| BC3 Soporte Chat | 0.0% | 0.0% | 0.0% | 0.0% | 0/250 |
| BC4 Recomendaciones | 38.0% | 50.0% | 37.7% | 31.7% | 49/130 |
| BC5 Notificaciones | 83.9% | 62.5% | 90.0% | 100.0% | 18/20 |
| BC6 Privacidad y Seguridad | 100.0% | 100.0% | 100.0% | 100.0% | 65/65 |
| Común (compartido) | 37.5% | n/a | 40.0% | 25.0% | 4/10 |

## 3. Cobertura por capa

| Capa | Instrucciones | Ramas | Líneas | Métodos | Líneas cubiertas |
|---|---:|---:|---:|---:|---:|
| Dominio | 73.8% | 73.0% | 75.9% | 72.3% | 337/444 |
| Aplicación | 35.3% | 100.0% | 36.0% | 25.6% | 40/111 |
| Infraestructura | 78.9% | 61.1% | 79.4% | 85.7% | 54/68 |
| Presentación | 19.5% | 54.5% | 21.6% | 21.0% | 48/222 |

## 4. Detalle — Módulo 2 (Seguimiento Emocional)

| Capa | Clase | Instrucciones | Ramas | Líneas | Métodos |
|---|---|---:|---:|---:|---:|
| Dominio | `AnalisisEmocionalService` | 98.5% | 75.0% | 100.0% | 100.0% |
| Dominio | `BitacoraEmocional` | 98.5% | 92.9% | 98.0% | 92.3% |
| Dominio | `BitacoraEmocionalFactory` | 100.0% | n/a | 100.0% | 100.0% |
| Dominio | `DatosAuditoria` | 100.0% | n/a | 100.0% | 100.0% |
| Dominio | `Emocion` | 50.0% | 33.3% | 56.0% | 45.5% |
| Dominio | `EscalaEmocional` | 87.2% | 78.6% | 87.5% | 88.9% |
| Dominio | `ResumenSemanal` | 94.9% | 75.0% | 90.0% | 100.0% |
| Aplicación | `BitacoraEmocionalAppServiceImpl` | 100.0% | n/a | 100.0% | 100.0% |
| Aplicación | `BitacoraNotFoundException` | 100.0% | n/a | 100.0% | 100.0% |
| Infraestructura | `InMemoryBitacoraEmocionalRepository` | 100.0% | 75.0% | 100.0% | 100.0% |

**Total del módulo:** instrucciones 91.8% · ramas 75.9% · líneas 92.6% · métodos 89.5%

## 5. Detalle — Módulo 6 (Privacidad y Seguridad)

| Capa | Clase | Instrucciones | Ramas | Líneas | Métodos |
|---|---|---:|---:|---:|---:|
| Dominio | `Consentimiento` | 100.0% | n/a | 100.0% | 100.0% |
| Dominio | `DatosAuditoria` | 100.0% | n/a | 100.0% | 100.0% |
| Dominio | `PoliticaPrivacidad` | 100.0% | 100.0% | 100.0% | 100.0% |
| Aplicación | `PoliticaPrivacidadAppServiceImpl` | 100.0% | 100.0% | 100.0% | 100.0% |
| Aplicación | `PoliticaPrivacidadNotFoundException` | 100.0% | n/a | 100.0% | 100.0% |
| Infraestructura | `InMemoryPoliticaPrivacidadRepository` | 100.0% | n/a | 100.0% | 100.0% |

**Total del módulo:** instrucciones 100.0% · ramas 100.0% · líneas 100.0% · métodos 100.0%

## 6. Clases sin cobertura (0 % de líneas)

30 de 62 clases. Concentración por módulo/capa:

| Módulo | Capa | Clases sin cobertura |
|---|---|---|
| BC1 Autenticación | Dominio | `AutenticacionException` |
| BC3 Soporte Chat | Aplicación | `ChatSessionAppServiceImpl`, `ChatSessionNotFoundException`, `SendMessageRequest`, `StartChatRequest` |
| BC3 Soporte Chat | Dominio | `AnonymousPseudonym`, `AuditData`, `ChatSession`, `ChatSessionFactory`, `ChatSessionStatus`, `Message`, `SenderRole` |
| BC3 Soporte Chat | Infraestructura | `InMemoryChatSessionRepository` |
| BC3 Soporte Chat | Presentación | `ChatSessionResponse`, `MenteEnCasaModulesController`, `MenteEnCasaSupportChatApplication`, `MessageResponse`, `SendMessageHttpRequest`, `StartChatHttpRequest`, `SupportChatConfiguration`, `SupportChatController`, `SupportChatDemoRunner`, `SupportChatHomeController` |
| BC4 Recomendaciones | Aplicación | `CrearRecomendacionRequest`, `RecomendacionAppServiceImpl`, `RecomendacionNotFoundException` |
| BC4 Recomendaciones | Presentación | `CrearRecomendacionHttpRequest`, `RecomendacionController`, `RecomendacionResponse`, `RecomendacionesConfiguration` |

## 7. Cobertura parcial en los módulos 2 y 6

| Clase | Líneas | Ramas | Líneas sin cubrir |
|---|---:|---:|---:|
| `BC2_SeguimientoEmocional.BitacoraEmocional` | 98.0% | 92.9% | 1 |
| `BC2_SeguimientoEmocional.Emocion` | 56.0% | 33.3% | 11 |
| `BC2_SeguimientoEmocional.EscalaEmocional` | 87.5% | 78.6% | 3 |
| `BC2_SeguimientoEmocional.ResumenSemanal` | 90.0% | 75.0% | 2 |

## 8. Cómo reproducir

```bash
mvn clean test
# Reporte HTML: target/site/jacoco/index.html
# Datos: target/site/jacoco/jacoco.csv y jacoco.xml
```
