# Tareas 001 — Limpieza de repositorios huérfanos en el dominio

Espec: `specs/001-limpieza-redundancias-dominio/spec.md` (aprobada).
Rama de trabajo: rama nueva local `spec-001-limpieza-repositorios`, creada desde `main` (ver plan, D3).
Convenciones: comandos desde la raíz del repo; commits locales, nunca push a `main` (AGENTS.md). T9 requirió credenciales del remoto que este entorno no tiene: la ejecutó el usuario (consola con credenciales) y ya está hecha; no bloqueó a T1–T8 ni a T10.

- [x] **T1. Volver a `main`, crear la rama de trabajo y capturar el baseline de compilación.** RF-3
  - Evidencia: en `spec-001-limpieza-repositorios`, creada desde `main` (`add2ef0`); `/tmp/opencode/spec001-baseline-antes.txt` = 11 líneas, único error preexistente de BC4 (`src/domain/BC4_Recomendaciones/RecomendacionRepositoryI.java:[6,8]`).
  - Nombre de rama: `spec-001-limpieza-repositorios` (nueva; NO usar la rama local `Bounded-05` ni la duplicada `Bounded_05`).
  - Acción: el working tree está hoy en la rama local `Bounded-05` → `git checkout main`; después `git checkout -b spec-001-limpieza-repositorios main`; por último el baseline.
  - Remanente: la rama local `Bounded-05` (creada por el checkout del intento anterior) NO se usa y NO se borra sin permiso; queda como remanente a limpiar después, fuera del alcance de esta spec.
  - Hecho cuando: se está en la rama `spec-001-limpieza-repositorios` creada desde `main` y `/tmp/opencode/spec001-baseline-antes.txt` contiene la salida completa de `mvn -q package 2>&1` antes de tocar nada, con exactamente el error único preexistente de BC4 (`RecomendacionRepositoryI.java`).
- [x] **T2. Pre-check de referencias a las 5 huérfanas en `src/`.** RF-1, RF-5
  - Evidencia: PASA. Grep de imports a las 5 huérfanas en `src/` = 0 resultados; `grep -rln '^package domain;' src/` = exactamente los 5 archivos raíz; las 5 canónicas existían y los huérfanos no se importaban entre sí.
  - Hecho cuando: `grep -rn --include='*.java' -E '^\s*import\s+domain\.(SesionRepository|RecomendacionRepository|RecordatorioRepository|PoliticaPrivacidadRepository|BitacoraEmocionalRepository)\s*;' src/` devuelve 0 resultados y `grep -rln --include='*.java' -E '^package\s+domain\s*;' src/` devuelve exactamente los 5 archivos raíz (y nada más). Si no: parar y escalar (RF-5).
- [x] **T3. Eliminar `src/domain/SesionRepository.java` (commit 1/5).** RF-1, RF-2, RNF-2
  - Evidencia: commit `975e150` (`spec-001: eliminar SesionRepository (huérfano; canónica en BC1_Autenticacion)`, 34 líneas, autor/committer `Diego-Canqui <dcanqui@unsa.edu.pe>`); el archivo ya no existe y el grep de `import domain.SesionRepository;` en `src/` = 0.
  - Hecho cuando: el archivo no existe; `git log -1 --stat` muestra un commit que elimina solo ese archivo; `grep -rn --include='*.java' -F 'import domain.SesionRepository;' src/` da 0 resultados.
- [x] **T4. Eliminar `src/domain/RecomendacionRepository.java` (commit 2/5).** RF-1, RF-2, RNF-2
  - Evidencia: commit `768e3f9` (`spec-001: eliminar RecomendacionRepository (huérfano; canónica en BC4_Recomendaciones)`, 34 líneas); grep de `import domain.RecomendacionRepository;` en `src/` = 0; la canónica `RecomendacionRepositoryI.java` (BC4) sigue intacta.
  - Hecho cuando: igual que T3 con `import domain.RecomendacionRepository;` (la canónica de BC4 vive en `RecomendacionRepositoryI.java` y no se toca).
- [x] **T5. Eliminar `src/domain/RecordatorioRepository.java` (commit 3/5).** RF-1, RF-2, RNF-2
  - Evidencia: commit `201ef18` (`spec-001: eliminar RecordatorioRepository (huérfano; canónica en BC5_Notificaciones)`, 37 líneas); grep de `import domain.RecordatorioRepository;` en `src/` = 0; la canónica de BC5 sigue intacta.
  - Hecho cuando: igual que T3 con `import domain.RecordatorioRepository;` (la canónica de BC5 no se toca).
- [x] **T6. Eliminar `src/domain/PoliticaPrivacidadRepository.java` (commit 4/5).** RF-1, RF-2, RNF-2
  - Evidencia: commit `86a3af1` (`spec-001: eliminar PoliticaPrivacidadRepository (huérfano; canónica en BC6_PrivacidadSeguridad)`, 30 líneas); grep de `import domain.PoliticaPrivacidadRepository;` en `src/` = 0.
  - Hecho cuando: igual que T3 con `import domain.PoliticaPrivacidadRepository;`.
- [x] **T7. Eliminar `src/domain/BitacoraEmocionalRepository.java` (commit 5/5).** RF-1, RF-2, RNF-2
  - Evidencia: commit `891f875` (`spec-001: eliminar BitacoraEmocionalRepository (huérfano; canónica en BC2_SeguimientoEmocional)`, 36 líneas); grep de `import domain.BitacoraEmocionalRepository;` en `src/` = 0.
  - Hecho cuando: igual que T3 con `import domain.BitacoraEmocionalRepository;`.
- [x] **T8. Verificación final: raíz vacía, greps, baseline y commits.** RF-1, RF-2, RF-3, RNF-2
  - Evidencia: PASA. `ls src/domain/*.java` = 0 (solo carpetas `BC*/` y `comun/`); los dos greps de T2 = 0 resultados; `diff` baseline-antes vs `/tmp/opencode/spec001-baseline-despues.txt` = VACÍO (11 líneas cada uno, mismo y único error de BC4, ninguno nuevo); 5 commits (uno por interfaz); las 5 canónicas siguen en su BC. Único `import domain.*` restante del repo: `docs/generated/LayeredArchitecture_Packages/infrastructure.mysql/RecordatorioRepositoryImpl.java:3` (fuera del build; previsto por la spec).
  - Hecho cuando: `ls src/domain/*.java` no devuelve nada; los dos greps de T2 devuelven 0 resultados; `mvn -q package 2>&1 | tee /tmp/opencode/spec001-baseline-despues.txt` produce `diff` vacío contra el baseline de T1 (mismo error único de BC4, ninguno nuevo); `git log --oneline` muestra 5 commits, uno por interfaz; las 5 canónicas siguen existiendo.
- [x] **T9. Eliminar la rama remota `Bounded_05` y cerrar el PR viejo (credenciales; NO bloqueante).** RF-4, RF-6, RF-7, RNF-2, RNF-3
  - Previo obligatorio (RNF-3): aviso al equipo antes de ejecutar.
  - Hecho cuando: tras `git fetch origin`, `git rev-list --count main..origin/Bounded_05` = 0 (si > 0: ABORTAR y escalar — RF-6); `git ls-remote --heads origin Bounded_05` devuelve salida vacía tras `git push origin --delete Bounded_05` ejecutado con credenciales (usuario, consola o web); el PR viejo queda cerrado, sin fusionar (verificado en el host, no asumido); la rama local `Bounded-05` (remanente del intento anterior) queda fuera del alcance de esta spec: no se borra sin permiso del usuario; y la evidencia queda registrada en este archivo y en MEMORY.md (T10).
  - Evidencia (REPORTADA por el usuario desde su consola con credenciales; este entorno no tiene SSH y no puede verificarla): salvaguarda previa verificada, `git rev-list --count main..origin/Bounded_05` = 0; rama remota duplicada `Bounded_05` eliminada con `git push origin --delete Bounded_05` ejecutado por el usuario con credenciales; PR asociado: **no existe ningún PR abierto** para la cabecera `Bounded_05` (comprobado por el usuario con `gh`/web) → RF-7 queda satisfecho sin necesidad de cerrar nada; la rama local `Bounded-05` permanece intacta, fuera del alcance de esta spec.
  - Honestidad de la evidencia: el agente no pudo comprobar el remoto (un `git ls-remote --heads origin Bounded_05` en este entorno falla con `ssh_askpass: ... No such file or directory` / `git@github.com: Permission denied (publickey)` → `fatal: No se pudo leer del repositorio remoto`, código 128). La eliminación de la rama y la ausencia de PR son, por tanto, evidencia **reportada por el usuario**, no verificada por el agente.
- [x] **T10. Registro documental y `MEMORY.md`.** RNF-2; criterios de finalización 5 y 6
  - Evidencia: `MEMORY.md` actualizado con el estado real de la spec 001 (5 commits hechos; T9 pendiente de credenciales; remanente de la rama local `Bounded-05` anotado; pendiente de validación por el reviewer) y este `tasks.md` con la evidencia de T1–T8 y T10.
  - Hecho cuando: `MEMORY.md` refleja la spec 001 (5 commits hechos y estado de T9: completada o pendiente de credenciales, único remanente anotado) y las tareas de este archivo quedan marcadas con su evidencia.

## Evidencia persistente (el baseline vivía en /tmp)

Los archivos `/tmp/opencode/spec001-baseline-antes.txt` y `/tmp/opencode/spec001-baseline-despues.txt` no son perennes; su contenido se registra aquí para no perderlo.

- Comando: `mvn -q package 2>&1` (11 líneas de salida).
- Error único (idéntico antes y después de la limpieza): `src/domain/BC4_Recomendaciones/RecomendacionRepositoryI.java:[6,8] interface RecomendacionRepository is public, should be declared in a file named RecomendacionRepository.java` + `Failed to execute goal ...maven-compiler-plugin:3.13.0:compile (default-compile) on project menteencasa-bc3: Compilation failure`.
- Conclusión (RF-3): baseline-antes y baseline-despues idénticos (`diff` vacío); el @reviewer lo reprodujo de forma independiente con la misma salida.
