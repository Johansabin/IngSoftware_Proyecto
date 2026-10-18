# MEMORY.md — Mente en casa
Memoria personal entre sesiones (en .gitignore; OJO: hoy NO está ignorado y se versiona — decidir, ver Próximos pasos). Máx. ~50 líneas: resume o elimina lo que ya no aporte.
Mi BC: BC5_Notificaciones (rama `Bounded-05`). Rol: líder de equipo (lectura de todos los BC, escritura solo BC5).

## Estado actual
- Spec activa: 001 `specs/001-limpieza-redundancias-dominio/` — limpieza de las 5 interfaces de repositorio huérfanas de la raíz de `src/domain/` y de la rama Git duplicada `Bounded_05`.
  - Código: hecho y commiteado en la rama local `spec-001-limpieza-repositorios` (creada desde `main`, `add2ef0`): 5 commits, uno por interfaz (`975e150`, `768e3f9`, `201ef18`, `86a3af1`, `891f875`); T1–T8 y T10 hechas (diff del baseline de `mvn -q package` vacío).
  - Validación: validada por el @reviewer con veredicto **APROBADO** (RF por RF): RF-1, RF-2, RF-3, RF-5 y RNF-1 CUMPLEN; RNF-2 cumple en su parte de código; criterios de finalización 1, 2, 3 y 6 cumplen.
  - Pendiente solo por T9 (credenciales del usuario; NO es fallo de la implementación): RF-4, RF-6, RF-7, RNF-3 y criterio 4.
  - T9 (eliminar la rama remota `Bounded_05` + cerrar el PR viejo) — pasos del usuario: (1) aviso al equipo (RNF-3); (2) `git fetch origin`; (3) re-comprobar `git rev-list --count main..origin/Bounded_05` = 0 — si > 0, ABORTAR y escalar (RF-6); (4) `git push origin --delete Bounded_05`; (5) `git ls-remote --heads origin Bounded_05` → salida vacía; (6) cerrar el PR viejo sin fusionarlo (RF-7); (7) registrar la evidencia en `tasks.md`.
  - Remanente: la rama local `Bounded-05` (HEAD `3a69c37`) no se usa ni se borra sin permiso; fuera del alcance de la spec.
  - Estado global: nada fusionado en `main`.

## Decisiones (y por qué)
- Las canónicas son las de cada BC: las 5 copias raíz (`package domain;`) eran código muerto (0 imports en `src/`) y se eliminaron todas, incluidas las 4 con firmas divergentes.
- Trabajar en la rama nueva `spec-001-limpieza-repositorios` (desde `main`) y NO en `Bounded-05`: esa rama es antigua (HEAD `3a69c37`), solo tiene dominio y no tuvo nunca `pom.xml` → el baseline de compilación es inejecutable allí.
- Se acepta que `mvn -q package` siga en rojo por el error ajeno de BC4 (`RecomendacionRepositoryI.java`): el criterio es «mismo error único, ninguno nuevo» (diff de baseline vacío, no un build verde).
- Un commit por interfaz eliminada (RNF-2): cada eliminación es reversible de forma aislada.
- La documentación de la spec (`specs/**` y `MEMORY.md`) se versiona en la rama de trabajo por acuerdo del usuario (trazabilidad de RNF-2); antes estaba sin rastrear.
- Identidad Git local del repo: `Diego-Canqui <dcanqui@unsa.edu.pe>` (configurada en la limpieza; el primer commit se enmendó al cambiarla).

## Aprendizajes y errores a evitar
- Los imports siguen la declaración `package`, no la carpeta: no mover archivos.
- `Bounded-05` NO es un espejo del proyecto: sin `pom.xml` y sin las capas application/infrastructure/presentation; no sirve como rama de trabajo.
- `git fetch` no funciona en este entorno (sin credenciales SSH): las refs remotas (`origin/Bounded_05`) son cacheadas; la salvaguarda se verificó sobre el ref cacheado.
- `docs/generated/` está fuera del build: sus `import domain.*` (p. ej. `RecordatorioRepositoryImpl.java`) son inofensivos y no cuentan para la verificación.
- El reviewer validó con baseline propio (re-ejecutó `mvn -q package`): su captura debe registrarse porque `/tmp` no es perenne.
- El repo no tenía identidad Git configurada al empezar esta spec.

## Próximos pasos
- Tras T9: revisar/fusionar la rama `spec-001-limpieza-repositorios` (nada está en `main`) y actualizar `MEMORY.md` y `tasks.md` con el resultado.
- Registrar como spec nueva el arreglo del nombre del archivo de BC4: `RecomendacionRepositoryI.java` declara la interfaz `RecomendacionRepository` (build en rojo); requiere permiso de ese BC.
- La evidencia del baseline del diff de la spec 001 vive solo en `/tmp` (`/tmp/opencode/spec001-baseline-*.txt`): registrarla o reproducirla antes de que se pierda.
- Decidir si se versiona `AGENTS.md` (hoy sin rastrear) y si `MEMORY.md` debe ignorarse o commitearse (AGENTS.md avisa de la incoherencia).
- Añadir JUnit 5 al pom.xml (requiere aprobación; toca archivo compartido).
- Automatizar el build con Jenkins (prerrequisito: JUnit; hoy `mvn -q package` es lo único).
- Útil para Jenkins: `origin/BoundedContext-1` es más reciente que `main` (`ae15592`, 12-oct-2026) y ya trae `Dockerfile`, `Jenkinsfile` y `tests-automatizados/` (JMeter y Postman de BC1).
