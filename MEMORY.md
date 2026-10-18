# MEMORY.md — Mente en casa
Memoria personal entre sesiones (en .gitignore; OJO: hoy NO está ignorado y se versiona — decidir, ver Próximos pasos). Máx. ~50 líneas: resume o elimina lo que ya no aporte.
Mi BC: BC5_Notificaciones (rama `Bounded-05`). Rol: líder de equipo (lectura de todos los BC, escritura solo BC5).

## Estado actual
- Spec 001 `specs/001-limpieza-redundancias-dominio/` — COMPLETADA: limpieza de las 5 interfaces de repositorio huérfanas de la raíz de `src/domain/` y de la rama Git duplicada `Bounded_05`.
  - T1–T10 hechas; **6/6 criterios de finalización cumplidos**, incluido el 4 (rama duplicada eliminada).
  - Código: todo commiteado en la rama local `spec-001-limpieza-repositorios` (creada desde `main`, `add2ef0`): 5 commits, uno por interfaz (`975e150`, `768e3f9`, `201ef18`, `86a3af1`, `891f875`) + el commit de cierre documental = **7 commits sobre `main`**. Diff del baseline de `mvn -q package` vacío (T1 vs T8).
  - Validación: validada por el @reviewer con veredicto **APROBADO** (RF por RF): RF-1, RF-2, RF-3, RF-5 y RNF-1 CUMPLEN; RNF-2 cumple en su parte de código.
  - T9 (la ejecutó el usuario en su consola, donde sí tiene credenciales): rama remota duplicada `Bounded_05` eliminada con `git push origin --delete`; PR asociado: **no existe ningún PR abierto** → RF-7 satisfecho sin cerrar nada; salvaguarda previa `git rev-list --count main..origin/Bounded_05` = 0.
  - Aviso de honestidad: **la verificación del remoto la hizo el usuario**, no este entorno (aquí no hay credenciales SSH: `git ls-remote` falla con `Permission denied (publickey)`). Es evidencia reportada, no comprobada por el agente.
  - Remanente: la rama local `Bounded-05` (HEAD `3a69c37`) no se usa ni se borra sin permiso; fuera del alcance de la spec.
  - Estado global: **nada está en `main`**; el remoto ya no tiene la rama duplicada.

## Decisiones (y por qué)
- Las canónicas son las de cada BC: las 5 copias raíz (`package domain;`) eran código muerto (0 imports en `src/`) y se eliminaron todas, incluidas las 4 con firmas divergentes.
- Trabajar en la rama nueva `spec-001-limpieza-repositorios` (desde `main`) y NO en `Bounded-05`: esa rama es antigua (HEAD `3a69c37`), solo tiene dominio y no tuvo nunca `pom.xml` → el baseline de compilación es inejecutable allí.
- Se acepta que `mvn -q package` siga en rojo por el error ajeno de BC4 (`RecomendacionRepositoryI.java`): el criterio es «mismo error único, ninguno nuevo» (diff de baseline vacío, no un build verde).
- Un commit por interfaz eliminada (RNF-2): cada eliminación es reversible de forma aislada.
- La documentación de la spec (`specs/**` y `MEMORY.md`) se versiona en la rama de trabajo por acuerdo del usuario (trazabilidad de RNF-2).
- Identidad Git local del repo: `Diego-Canqui <dcanqui@unsa.edu.pe>` (configurada en la limpieza; el primer commit se enmendó al cambiarla).

## Aprendizajes y errores a evitar
- Los imports siguen la declaración `package`, no la carpeta: no mover archivos.
- `Bounded-05` NO es un espejo del proyecto: sin `pom.xml` y sin las capas application/infrastructure/presentation; no sirve como rama de trabajo.
- `git fetch`/`git ls-remote` no funcionan en este entorno (sin credenciales SSH): las refs remotas son cacheadas y lo del remoto hay que delegarlo en el usuario.
- `docs/generated/` está fuera del build: sus `import domain.*` (p. ej. `RecordatorioRepositoryImpl.java`) son inofensivos y no cuentan para la verificación.
- `/tmp` no es perenne: la evidencia de baseline está registrada en `tasks.md` (§ Evidencia persistente).
- El repo no tenía identidad Git configurada al empezar esta spec.

## Próximos pasos
- Revisar/fusionar la rama `spec-001-limpieza-repositorios` (**nada está en `main`**; AGENTS.md veta push a `main`).
- Registrar como spec nueva el arreglo del nombre del archivo de BC4: `RecomendacionRepositoryI.java` declara la interfaz `RecomendacionRepository` (build en rojo); requiere permiso de ese BC ajeno.
- Decidir si se versiona `AGENTS.md` (hoy sin rastrear) y si `MEMORY.md` debe ignorarse o commitearse (AGENTS.md avisa de la incoherencia).
- Añadir JUnit 5 al `pom.xml` (requiere aprobación; toca archivo compartido) y automatizar el build con Jenkins (objetivo del usuario; hoy `mvn -q package` es lo único).
- Útil para Jenkins: `origin/BoundedContext-1` es más reciente que `main` (`ae15592`, 12-oct-2026) y ya trae `Dockerfile`, `Jenkinsfile` y `tests-automatizados/` (JMeter y Postman de BC1).
