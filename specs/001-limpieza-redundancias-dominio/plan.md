# Plan 001 — Limpieza de repositorios huérfanos en el dominio

Espec: `specs/001-limpieza-redundancias-dominio/spec.md` (aprobada).
Rama de trabajo: rama nueva local `spec-001-limpieza-repositorios` creada desde `main` (confirmada por el usuario; ver D3).
Verificación del proyecto: `mvn -q package` desde la raíz (sin pytest ni JUnit; la estrategia de verificación está traducida a comandos más abajo).

## Archivos que se tocan

### Eliminaciones (única capa afectada: domain, raíz del paquete)

| # | Archivo a eliminar (`package domain;`) | Definición canónica (NO se toca) | RF |
|---|---|---|---|
| 1 | `src/domain/SesionRepository.java` | `src/domain/BC1_Autenticacion/SesionRepository.java` | RF-1, RF-2 |
| 2 | `src/domain/RecomendacionRepository.java` | `src/domain/BC4_Recomendaciones/RecomendacionRepositoryI.java` (declara la interfaz pública `RecomendacionRepository`) | RF-1, RF-2 |
| 3 | `src/domain/RecordatorioRepository.java` | `src/domain/BC5_Notificaciones/RecordatorioRepository.java` | RF-1, RF-2 |
| 4 | `src/domain/PoliticaPrivacidadRepository.java` | `src/domain/BC6_PrivacidadSeguridad/PoliticaPrivacidadRepository.java` | RF-1, RF-2 |
| 5 | `src/domain/BitacoraEmocionalRepository.java` | `src/domain/BC2_SeguimientoEmocional/BitacoraEmocionalRepository.java` | RF-1, RF-2 |

### Documentación/registro (sin código)

- `specs/001-limpieza-redundancias-dominio/tasks.md` (evidencia de cada tarea — RNF-2).
- `MEMORY.md` (criterio 6).

### Qué NO se toca

- Nada dentro de `src/domain/BC*/` (interfaces canónicas, entidades, `DatosAuditoria`).
- `src/application/**`, `src/infrastructure/**`, `src/presentation/**` (0 referencias a las huérfanas, verificado).
- `docs/generated/**` (fuera del build; su `import domain.SesionRepository` se queda tal cual — RF-1).
- `pom.xml`, `README.md`, `sonar-project.properties`, `.gitignore`.
- Git: nunca push a `main` (AGENTS.md); no se mueve ni renombra ningún archivo (solo se eliminan los 5 huérfanos).

## Responsabilidades por capa, entidades y endpoints

- **domain**: única capa afectada; cambio neto = −5 archivos muertos. Entidades y reglas de dominio sin cambios (RNF-1); tras la limpieza, la definición canónica de cada contrato vive solo en su BC (RF-2).
- **application / infrastructure / presentation**: sin cambios (ninguna referencia a las huérfanas).
- **frontend**: no existe en este proyecto.
- **Endpoints/API**: no aplica; no se toca ningún controlador ni el wiring manual de beans.

Hecho estructural que ordena la ejecución: las 5 huérfanas importan entidades de su BC (`BC1_Autenticacion.Sesion`, `BC4_Recomendaciones.Recomendacion`, `BC5_Notificaciones.Recordatorio`, `BC6_PrivacidadSeguridad.PoliticaPrivacidad`/`Consentimiento`, `BC2_SeguimientoEmocional.BitacoraEmocional`) y **no se importan entre sí**. La dependencia es huérfana→BC, nunca al revés: cada eliminación es independiente y deja el árbol compilando igual que antes (módulo el error preexistente de BC4, que es el baseline de RF-3).

## Decisiones (cada una con su alternativa descartada)

**D1. Un commit por interfaz (5 commits).** RNF-2.
Cada `git rm` va seguido de su commit con mensaje `spec-001: eliminar <interfaz> (huérfana; canónica en <BC>)`.
Descartado: un único commit agrupado — contradice la decisión del usuario y RNF-2 (cada eliminación reversible de forma aislada).

**D2. Eliminar las 5, incluida la copia exacta (`RecordatorioRepository`).** RF-2.
La spec aprobada registra la decisión del líder: las canónicas son las de cada BC.
Descartado: dejar la copia exacta "porque no diverge" — mantendría dos definiciones del mismo contrato y RF-2 exige exactamente una.

**D3. Rama de trabajo: rama nueva local `spec-001-limpieza-repositorios` creada desde `main`, confirmada por el usuario.**
Contexto: la limpieza es transversal (toca la raíz compartida de `src/domain/`) y no debe arrastrar a ningún BC. Se crea con `git checkout -b spec-001-limpieza-repositorios main` (T1). AGENTS.md veta el push a `main`; al trabajar en una rama propia y desechable, el trabajo queda fuera de la historia de `main` hasta la revisión del equipo.
Descartadas: (a) usar la rama existente `Bounded-05` (oficial de BC5), propuesta en una revisión anterior — revertida por el usuario por inviable: `Bounded-05` es antigua y parcial (HEAD `3a69c37`, 13-jul-2026) y solo contiene `src/domain/**` + `docs` + `README.md`; sin `pom.xml` (nunca lo tuvo en su historia), sin `.gitignore` y sin las capas `application`/`infrastructure`/`presentation`. Allí no hay build (`mvn -q package` aborta con `MissingProjectException`), el baseline de RF-3 es inejecutable y ni siquiera existe el error preexistente de BC4 que la spec usa como baseline (en esa rama el archivo se llama `RecomendacionRepository.java`, sin la «I»); además tiene un sexto huérfano raíz (`src/domain/SesionChatRepository.java`) ausente en `main` y no contemplado en la spec, que rompería el pre-check de T2 (`package domain;` → exactamente 5 archivos). (b) commitear en `main` local — deja la limpieza en la historia de main antes de la revisión del equipo y arriesga un push accidental (vetado por AGENTS.md).

**D4. Baseline de "exactamente igual" (RF-3) capturado como texto.**
Antes de tocar nada: `mvn -q package 2>&1 | tee /tmp/opencode/spec001-baseline-antes.txt`. Después (T8): igual con `spec001-baseline-despues.txt` y `diff` entre ambos; pase = diff vacío. Si apareciera ruido no determinista, el pase se evalúa manualmente y se documenta en tasks.md: exactamente el mismo error único de BC4 y ningún error nuevo.
Descartado: comparar solo el código de salida o el número de errores — más débil que el criterio de la spec ("mismo error único, ninguno nuevo").

**D5. Referencias verificadas por grep acotado a `src/` (RF-1/RF-5).**
Se buscan imports cualificados `import domain.<Nombre>Repository;` y declaraciones `^package domain;`. No se grepea el nombre simple de la interfaz (colisiona con las canónicas, que tienen el mismo nombre) ni `docs/generated/` (fuera del perímetro).
Descartado: grep global del repo — daría el falso positivo de `docs/generated/` (hallazgo QA 5).

**D6. Tarea de la rama (T9) no bloqueante, con credenciales aparte.**
Este entorno no tiene credenciales del remoto (sin SSH, sin `gh`). La eliminación remota (`git push origin --delete Bounded_05`), el borrado local (hoy existen `main` y la rama local `Bounded-05`, que es un remanente del intento anterior — ver T1) y el cierre del PR los ejecuta el usuario donde haya credenciales o vía web. T1–T8 y T10 no dependen de T9.
Descartado: marcar T9 como bloqueante — detendría la limpieza de código por una operación de credenciales; la spec permite documentar el remanente en MEMORY.md.

**D7. `git fetch` previo en T9 (aviso QA).**
Antes del `rev-list` se actualizan las refs: `git fetch origin`. Cross-check adicional: `git rev-list --count origin/main..origin/Bounded_05` por si `main` local estuviera desactualizado.
Descartado: fiarse del estado actual de las refs locales — el fetch es lo que hace vigente la salvaguarda RF-4/RF-6 en el momento de ejecutar.

## Estrategia de verificación (traducción del tooling: sin pytest/JUnit)

Comandos desde la raíz del repo. Cobertura por RF:

- **RF-5 (pre-check, T2):**
  - `grep -rn --include='*.java' -E '^\s*import\s+domain\.(SesionRepository|RecomendacionRepository|RecordatorioRepository|PoliticaPrivacidadRepository|BitacoraEmocionalRepository)\s*;' src/` → 0 resultados.
  - `grep -rln --include='*.java' -E '^package\s+domain\s*;' src/` → exactamente los 5 archivos a eliminar (nadie más vive en ese paquete). Si devuelve otra cosa: parar y escalar (RF-5).
- **RF-1 (post, T8):** mismos greps tras la limpieza → 0 resultados en ambos.
- **RF-2 (T8):** `ls src/domain/*.java` → vacío; y las 5 canónicas siguen existiendo en sus BC.
- **RF-3 (T1 + T8):** baseline-antes vs baseline-despues, `diff` vacío (mismo error único de BC4, ninguno nuevo).
- **RF-4 / RF-6 (T9):** `git fetch origin` → `git rev-list --count main..origin/Bounded_05` = 0 (si > 0: ABORTAR y escalar). Tras la eliminación: `git ls-remote --heads origin Bounded_05` → salida vacía.
- **RF-7 (T9):** PR viejo cerrado, sin fusionar (verificado en el host: web o `gh` con credenciales). Algunos hosts cierran el PR automáticamente al borrar la rama fuente; RF-7 exige verificar el estado final, no asumirlo.
- **RNF-2 (T3–T7, T9, T10):** `git log --oneline` muestra 5 commits (uno por interfaz); la eliminación de la rama se registra de forma documental en tasks.md y MEMORY.md.

## Cobertura RF → tareas

- RF-3 → T1, T8
- RF-1, RF-5 → T2, T3–T7, T8
- RF-2 → T3–T7, T8
- RF-4, RF-6, RF-7 → T9
- RNF-2 → T3–T7, T9, T10
- RNF-3 → T9 (aviso previo al equipo)
- Criterios de finalización 5 y 6 → T10
