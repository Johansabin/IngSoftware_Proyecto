# Spec 001 — Limpieza de repositorios huérfanos en el dominio

Estado: aprobada

## Contexto y objetivo

El dominio del proyecto contiene cinco interfaces de repositorio huérfanas sueltas en la raíz del paquete de dominio (`package domain;`): `SesionRepository`, `RecomendacionRepository`, `RecordatorioRepository`, `PoliticaPrivacidadRepository` y `BitacoraEmocionalRepository`. Cada una convive con una interfaz del mismo nombre definida dentro de un Bounded Context: solo `RecordatorioRepository` es copia exacta de su par canónica (BC5); las otras cuatro presentan firmas divergentes de las de su BC. Ningún archivo de `src/` importa ninguna de las cinco (código muerto verificado); el único import del repositorio a alguna de ellas está en `docs/generated/`, que está fuera del build. Esta redundancia aumenta la superficie de mantenimiento y genera confusión sobre cuál es la definición canónica.

Adicionalmente, existe una rama Git duplicada de BC5_Notificaciones (`Bounded_05`), producto de un PR antiguo mal integrado, que genera ruido en el historial y en los flujos de revisión. Aunque su contenido difiere del de la rama oficial (`Bounded-05`) —cada rama tiene commits únicos desde su punto de bifurcación—, se ha verificado que todos los commits de `Bounded_05` son alcanzables desde `main` (0 commits inalcanzables), por lo que eliminarla no pierde trabajo.

El objetivo es eliminar las cinco interfaces huérfanas y resolver la rama Git duplicada, dejando una única fuente de verdad por contrato de repositorio y por rama de BC, sin empeorar la compilación respecto a su estado actual y sin alterar los contratos existentes entre Bounded Contexts.

Estado actual de la compilación: `mvn -q package` falla hoy por un único error preexistente, ajeno a esta spec, en `src/domain/BC4_Recomendaciones/RecomendacionRepositoryI.java`: la interfaz pública `RecomendacionRepository` está declarada en un archivo con otro nombre, lo que Java no permite. Corregirlo exigiría renombrar un archivo de un BC ajeno (vetado sin acuerdo del equipo), por lo que se documenta como preexistente y queda fuera de alcance.

## Usuarios

- **Equipo de desarrollo**: se beneficia de una base de código sin duplicados y con una sola fuente de verdad por interfaz.
- **Líder técnico / revisor**: puede rastrear cambios en un solo lugar por cada contrato de dominio.

## Historias de usuario

- HU-1. Como desarrollador del proyecto, quiero que cada interfaz de repositorio exista en un único lugar, para no tener que mantener dos copias sincronizadas ni confundirme sobre cuál es la canónica.
- HU-2. Como líder del equipo, quiero que la lista de ramas activas en el repositorio refleje solo las ramas en uso real, para que el flujo de revisión de código sea claro.

## Requisitos funcionales

- RF-1: CUANDO se eliminan las interfaces de repositorio huérfanas de la raíz del paquete de dominio, EL SISTEMA no debe contener, dentro de `src/`, ninguna referencia de importación a esas definiciones eliminadas (cero usos rotos; los archivos de `docs/generated/`, fuera del build, quedan fuera del perímetro de verificación).
- RF-2: EL SISTEMA debe mantener exactamente una definición de cada interfaz de repositorio, ubicada dentro del Bounded Context dueño de su entidad: `SesionRepository` en BC1_Autenticación, `BitacoraEmocionalRepository` en BC2_SeguimientoEmocional, `RecomendacionRepository` en BC4_Recomendaciones, `RecordatorioRepository` en BC5_Notificaciones y `PoliticaPrivacidadRepository` en BC6_PrivacidadSeguridad; tras la limpieza, ninguna de las cinco debe existir en la raíz del paquete de dominio.
- RF-3: CUANDO se ejecuta la verificación de compilación (`mvn -q package`) después de la limpieza, EL SISTEMA debe fallar, como máximo, exactamente igual que en el estado previo: con el mismo y único error preexistente (BC4, `RecomendacionRepositoryI.java`) y ningún error nuevo (criterio de «no empeorar» el baseline).
- RF-4: CUANDO se ejecuta la eliminación de la rama Git duplicada de BC5_Notificaciones (`Bounded_05`), EL FLUJO DE TRABAJO debe verificar previamente que el número de commits de `Bounded_05` inalcanzables desde `main` es 0 (`git rev-list --count main..origin/Bounded_05`), ejecutar la eliminación solo tras esa verificación y dejar, del par de ramas de BC5, únicamente la rama oficial (`Bounded-05`) con todo su historial intacto, sin afectar a ninguna otra rama del repositorio.
- RF-5: SI existe alguna referencia activa (importación, herencia, anotación) dentro de `src/` a cualquiera de las interfaces huérfanas, ENTONCES EL FLUJO DE TRABAJO debe rechazar la eliminación de esa interfaz hasta que la referencia sea resuelta.
- RF-6: SI en el momento de ejecutar la eliminación el número de commits de `Bounded_05` inalcanzables desde `main` es mayor que 0, ENTONCES EL FLUJO DE TRABAJO debe abortar esa eliminación y elevar la decisión al equipo.
- RF-7: CUANDO se elimina la rama Git duplicada `Bounded_05` y existe un pull request abierto asociado a ella en el repositorio remoto, EL FLUJO DE TRABAJO debe cerrar formalmente ese pull request, sin fusionarlo, como parte de la misma tarea de eliminación.

## Requisitos no funcionales

- RNF-1: La limpieza no debe alterar la lógica de negocio ni los contratos públicos de los Bounded Contexts que sí contienen las interfaces canónicas.
- RNF-2: La trazabilidad de la limpieza debe ser verificable de forma independiente: un commit propio por cada interfaz huérfana eliminada (cinco commits, cada eliminación reversible de forma aislada) y, para la eliminación de la rama duplicada —que no genera commit de Git—, verificación documental: la rama ya no figura en el remoto (`git ls-remote`), el pull request viejo queda cerrado sin fusionar y la tarea queda registrada en `tasks.md` y `MEMORY.md`.
- RNF-3: La eliminación de la rama duplicada y el cierre de su pull request asociado deben coordinarse con todos los miembros del equipo antes de ejecutarse, para evitar pérdida de trabajo no integrado. El acuerdo queda evidenciado en la sección «Decisiones» de esta spec, complementado con un aviso al equipo previo a la ejecución.

## Casos límite

- Cuatro de las cinco interfaces huérfanas (`SesionRepository`, `RecomendacionRepository`, `PoliticaPrivacidadRepository`, `BitacoraEmocionalRepository`) tienen firmas divergentes de las de su BC: el líder del equipo ya decidió que las canónicas son las de cada BC, por lo que se eliminan igualmente al ser código muerto (0 imports en `src/`). Escalar al equipo queda como salvaguarda únicamente para cualquier divergencia no prevista que se detecte durante la ejecución.
- La rama `Bounded_05` no es idéntica a `Bounded-05` (cada una tiene commits únicos desde su punto de bifurcación), pero hoy todos sus commits son alcanzables desde `main` (0 inalcanzables), así que borrarla no pierde trabajo. La verificación previa de esa cifra es una salvaguarda obligatoria: si en el momento de ejecutar la eliminación hubiera commits inalcanzables desde `main`, esta se aborta y se escala al equipo (RF-4, RF-6).
- Si el compilador de Maven resolviera ambigüedades entre paquetes usando una interfaz huérfana (poco probable, pero posible si algún `import` fuera por wildcard), la eliminación introduciría un error de compilación nuevo respecto al baseline → RF-5 impide llegar a ese punto y RF-3 lo detecta.
- Archivos bajo `docs/generated/` pueden seguir importando rutas eliminadas (p. ej., `RecordatorioRepositoryImpl.java` importa `domain.SesionRepository`): no se tocan ni cuentan para la verificación, al estar fuera del build (RF-1).

## Fuera de alcance

- **Clases `DatosAuditoria` duplicadas** (presentes en `comun`, BC1, BC2, BC4 y BC6): requieren acuerdo del equipo completo y una decisión de diseño sobre cuál es la fuente de verdad compartida. Se gestionarán en una spec separada.
- Agregar o modificar dependencias en `pom.xml`.
- Mover o renombrar archivos de código fuente (solo se eliminan los huérfanos).
- Configuración de CI/CD, Jenkins, SonarQube o cualquier otro tooling de infraestructura.
- Refactorización de las interfaces canónicas (nombres, firmas, Javadoc).
- Corregir el error de compilación preexistente en BC4 (`RecomendacionRepositoryI.java` declara la interfaz pública `RecomendacionRepository` en un archivo con otro nombre): es ajeno a esta spec, se trata como baseline en RF-3 y arreglarlo exige renombrar un archivo de un BC ajeno, vetado sin acuerdo del equipo.
- Archivos de `docs/generated/` (referencia fuera del build): pueden seguir importando las rutas eliminadas sin afectar a la compilación ni a la verificación (RF-1).

## Criterios de finalización

1. Las cinco interfaces huérfanas ya no existen en la raíz del paquete de dominio.
2. `mvn -q package` falla exactamente igual que antes de la limpieza: el mismo error único preexistente de BC4 y ningún error nuevo.
3. Ningún `import` dentro de `src/` apunta a las rutas eliminadas (los archivos de `docs/generated/` están fuera del perímetro de verificación).
4. La rama Git duplicada `Bounded_05` de BC5_Notificaciones ya no aparece en el repositorio remoto (verificado con `git ls-remote`), tras la verificación previa de 0 commits inalcanzables desde `main` y el aviso al equipo; el pull request viejo asociado, si seguía abierto, ha sido cerrado formalmente sin fusionar.
5. Cada una de las cinco eliminaciones de interfaz queda registrada en su propio commit (reversible de forma aislada) y la eliminación de la rama queda documentada en `tasks.md` y `MEMORY.md`.
6. `MEMORY.md` actualizado reflejando el estado de esta spec.

## Decisiones

Decisiones del equipo durante la clarificación, revisadas tras verificar los hechos contra el repositorio:

- La rama `Bounded_05` se elimina dentro de esta spec, como una tarea más del plan, con verificación previa y trazabilidad documental (RNF-2).
- Al eliminar la rama `Bounded_05`, si su pull request viejo asociado sigue abierto en el remoto, la tarea debe cerrarlo formalmente (p. ej., con `gh` o la interfaz del repositorio), sin fusionarlo, para que no quede un PR fantasma apuntando a una rama borrada (RF-7).
- Se aplica un commit por cada interfaz huérfana eliminada (cinco commits); la eliminación de la rama, que no genera commit de Git, se verifica de forma documental: la rama ya no figura en el remoto, el pull request queda cerrado sin fusionar y la tarea queda registrada en `tasks.md` y `MEMORY.md` (RNF-2).
- El líder del equipo decide que las definiciones canónicas son las interfaces de cada Bounded Context: las cinco copias raíz son código muerto (0 imports en `src/`) y se eliminan todas, incluidas las cuatro con firmas divergentes (`SesionRepository`, `RecomendacionRepository`, `PoliticaPrivacidadRepository`, `BitacoraEmocionalRepository`).
- La salvaguarda para eliminar `Bounded_05` es que 0 de sus commits sean inalcanzables desde `main` (verificado hoy: `git rev-list --count main..origin/Bounded_05` = 0). La comparación directa con `Bounded-05` se descartó porque ambas ramas divergen entre sí desde su punto de bifurcación, aunque borrar `Bounded_05` no pierde trabajo. Si al ejecutar la eliminación hubiera commits inalcanzables, se aborta y se escala al equipo (RF-4, RF-6).
- La verificación de compilación se evalúa como baseline de «no empeorar»: `mvn -q package` está hoy en rojo por un único error preexistente y ajeno a esta spec (BC4, `RecomendacionRepositoryI.java`); la limpieza es correcta si después falla exactamente igual (mismo error único, ninguno nuevo) (RF-3).

## Dudas abiertas

- (Ninguna pendiente: todas quedan resueltas en «Decisiones».)
