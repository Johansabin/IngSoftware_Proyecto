# MenteEnCasa

MenteEnCasa es un proyecto de software orientado al apoyo emocional de estudiantes universitarios. El sistema permite registrar emociones, visualizar el seguimiento emocional y organizar recomendaciones de bienestar.

## Propósito

Brindar una herramienta digital que ayude a los estudiantes a monitorear su estado emocional y detectar patrones relacionados con estrés, ansiedad o bienestar.

## Funcionalidades principales

- Registro e inicio de sesión de usuarios.
- Seguimiento emocional diario.
- Visualización de resumen emocional.
- Soporte mediante chat.
- Recomendaciones personalizadas.
- Notificaciones.
- Gestión de privacidad y seguridad.

## Modelo de dominio

El proyecto está organizado en módulos o bounded contexts:

- BC1_Autenticacion
- BC2_SeguimientoEmocional
- BC3_SoporteChat
- BC4_Recomendaciones
- BC5_Notificaciones
- BC6_PrivacidadSeguridad

## Arquitectura

El proyecto sigue una organización por capas:

- application
- domain
- infrastructure
- presentation

## Diagramas UML

Los diagramas UML se encuentran en:

- docs/uml/casos-uso.png
- docs/uml/modelo-dominio.png
- docs/uml/arquitectura-general.png

## Prototipo GUI

El prototipo de interfaz se encuentra en:

- docs/prototipo/gui.png

## Tablero Kanban/Scrum

El tablero de gestión del proyecto se documentará en:

- docs/trello.md

## Convenciones de Codificación - BC3 Soporte Chat

Responsable: Johansabina  
Bounded Context: BC3_SoporteChat

En la implementación del soporte por chat se aplicaron las siguientes convenciones de Java:

- Clases e interfaces en `UpperCamelCase`: `ChatSession`, `Message`, `ChatSessionRepository`.
- Métodos y atributos en `lowerCamelCase`: `sendMessage`, `psychologistId`, `createdAt`.
- Constantes de enumeraciones en mayúsculas: `ACTIVE`, `CLOSED`, `STUDENT`, `PSYCHOLOGIST`.
- Encapsulamiento de atributos con `private`.
- Validación de referencias obligatorias con `Objects.requireNonNull`.
- Uso de `enum` para evitar strings mágicos en estados y roles.
- Exposición controlada de colecciones con `Collections.unmodifiableList`.

Fragmento aplicado en `ChatSession`:

```java
public void sendMessage(Message message) {
    Objects.requireNonNull(message, "The message is required");

    if (status == ChatSessionStatus.CLOSED) {
        throw new IllegalStateException("Cannot send messages to a closed chat session");
    }

    messages.add(message);
}
```

La práctica aplicada es mantener la regla de negocio dentro del agregado `ChatSession`, evitando que una sesión cerrada reciba nuevos mensajes.
