# Evidencias de Laboratorio 06: Pruebas Funcionales

* **Institución:** Universidad Nacional de San Agustín de Arequipa (UNSA)  
* **Facultad:** Ingeniería de Producción y Servicios  
* **Escuela Profesional:** Ciencia de la Computación  
* **Curso:** Ingeniería de Software II  
* **Docente:** DSc. Edgar Sarmiento Calisaya  
* **Tema:** Automatización de Pruebas Funcionales de Caja Negra  
* **Lenguaje:** Java 17 / 21  
* **Framework xUnit:** JUnit 6 (`org.junit.jupiter:junit-jupiter:6.1.3`)  
* **Herramienta de Cobertura:** JaCoCo (`jacoco-maven-plugin:0.8.15`)  
* **Herramientas de Automatización:** Selenium WebDriver 4.26.0, Chromium / ChromeDriver  

---

## 1. Integrantes del Equipo

| Integrante | Rol / Módulo Principal |
| :--- | :--- |
| **Ramos Mamani, Rodrigo Abraham** | BC1 – Autenticación |
| **Ramos Chambi, Luis Enrique** | BC2 – Seguimiento Emocional |
| **Huamani Sabina, Johan Diego** | BC3 – Soporte Chat |
| **Villanueva Flores, Diego Mauricio** | BC4 – Recomendaciones |
| **Canqui Ecos, Diego** | BC5 – Notificaciones |

---

## 2. Ubicación del Proyecto en el Repositorio

Todo el código fuente y ejecutables del laboratorio se encuentran organizados en la carpeta:
```text
labs/lab06-pruebas-funcionales/
├── README.md                      # Guía técnica del laboratorio
├── app/
│   └── springbootwebapp/          # Aplicación bajo prueba (Spring Boot + Thymeleaf con JaCoCo)
└── tests/                         # Suite de automatización con Selenium, JUnit 6 y JaCoCo
    ├── pom.xml                    # Configuración de dependencias (Selenium 4.26.0, JUnit 6.1.3, JaCoCo 0.8.15)
    └── src/
        ├── main/java/dev/selenium/getting_started/
        │   └── UsingSeleniumTest.java          # Script standalone (método main / exec:java)
        └── test/java/pe/edu/unsa/is2/
            └── CreateProductSeleniumXUnitTest.java # Suite formal xUnit con 5 casos de prueba
```

---

## 3. Entregable 1: Casos de Prueba Identificados (Formato Tabular)

Se aplicaron técnicas de **Caja Negra** (Partición de Clases de Equivalencia y Análisis de Valores Límite - BVA) sobre la funcionalidad **"Create Product"** (`/product/new`):

| Objeto de Prueba / Funcionalidad | Escenario / Acciones | Valores de Prueba | Resultado Esperado |
| :--- | :--- | :--- | :--- |
| **Create Product (`/product/new`)** | **TC-CP-01 (Clase de equivalencia válida estándar):**<br>Completar todos los campos del formulario con datos válidos y pulsar "Submit". | `productId`: "PRD-1001"<br>`description`: "Teclado Mecanico RGB"<br>`price`: "49.99"<br>`imageUrl`: "https://example.com/keyboard.jpg" | Redirección exitosa a `/product/{id}`. La vista de detalle muestra la descripción, precio "49.99" y la imagen asignada. |
| **Create Product (`/product/new`)** | **TC-CP-02 (Análisis de Valor Límite - BVA):**<br>Registrar un producto con el valor mínimo positivo de precio monetario (`0.01`). | `productId`: "PRD-MIN-01"<br>`description`: "Sticker Decorativo"<br>`price`: "0.01"<br>`imageUrl`: "https://example.com/sticker.png" | El producto se registra correctamente; la vista de detalle refleja el precio mínimo "0.01". |
| **Create Product (`/product/new`)** | **TC-CP-03 (Campo opcional omitido):**<br>Crear un producto omitiendo la URL de imagen (campo opcional en blanco). | `productId`: "PRD-NO-IMG"<br>`description`: "Mouse Pad Basico"<br>`price`: "15.50"<br>`imageUrl`: "" *(vacío)* | El sistema procesa la creación sin errores y muestra el producto en detalle sin imagen. |
| **Create Product & Product List (`/product/new` → `/products`)** | **TC-CP-04 (Persistencia y flujo End-to-End):**<br>Crear un nuevo producto y verificar que aparezca registrado en el catálogo general. | `productId`: "PRD-LIST-99"<br>`description`: "Audifonos Gamer 7.1"<br>`price`: "89.90"<br>`imageUrl`: "https://example.com/headset.jpg" | Tras navegar a `/products`, la tabla HTML contiene la fila con la descripción "Audifonos Gamer 7.1" y el precio "89.90". |
| **Create Product (`/product/new`)** | **TC-CP-05 (Clase de equivalencia inválida - Tipo de dato):**<br>Ingresar una cadena de texto en el campo numérico `price` y pulsar "Submit". | `productId`: "PRD-ERR-01"<br>`description`: "Producto Invalido"<br>`price`: "precio_no_numerico"<br>`imageUrl`: "https://example.com/err.jpg" | El servidor rechaza la solicitud (error 400 Bad Request / binding error) y no crea ningún registro corrupto. |

---

## 4. Entregable 2: Proyecto de Automatización con Selenium y JUnit 6

### Características de la Implementación:
* **Framework xUnit:** JUnit 6 (`org.junit.jupiter:junit-jupiter:6.1.3`).
* **Sincronización:** Uso de `WebDriverWait` y `ExpectedConditions.urlMatches(...)` para evitar condiciones de carrera por navegación asíncrona.
* **Modo Headless y Compatibilidad:** Configuración de `ChromeOptions` (`--headless=new`, `--no-sandbox`, `--disable-dev-shm-usage`) para permitir ejecución fluida tanto en entorno de escritorio como en servidores CI/CD sin pantalla gráfica.
* **Ciclo de Vida Limpio:** Anotaciones `@BeforeEach` para instanciar el driver y `@AfterEach` (`driver.quit()`) para liberar procesos de sistema.

---

## 5. Entregable 3: Evidencias de Ejecución (Reporte xUnit)

### Resumen de la Ejecución de Pruebas:
```text
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running pe.edu.unsa.is2.CreateProductSeleniumXUnitTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 8.384 s -- in pe.edu.unsa.is2.CreateProductSeleniumXUnitTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### Detalle Individual de Casos de Prueba:

| Caso de Prueba | Método de Prueba | Duración | Estado |
| :--- | :--- | :--- | :--- |
| **TC-CP-01** | `testCreateProductWithValidData()` | 2.677 s | ✅ **PASSED** |
| **TC-CP-02** | `testCreateProductWithMinimumPriceBoundary()` | 1.350 s | ✅ **PASSED** |
| **TC-CP-03** | `testCreateProductWithoutOptionalImageUrl()` | 1.124 s | ✅ **PASSED** |
| **TC-CP-04** | `testCreateProductAndVerifyInProductsList()` | 1.858 s | ✅ **PASSED** |
| **TC-CP-05** | `testCreateProductWithInvalidPriceType()` | 1.338 s | ✅ **PASSED** |
| **Total** | **5 casos de prueba ejecutados** | **8.384 s** | 🟢 **100% Exitoso (0 fallos)** |

---

## 6. Entregable 4: Reporte de Cobertura de Código (JaCoCo)

Se configuró el plugin **`jacoco-maven-plugin:0.8.15`** en las fases del ciclo de vida Maven.

### Archivos de Reporte Generados:
1. **Proyecto de Automatización de Pruebas:**  
   `labs/lab06-pruebas-funcionales/tests/target/site/jacoco/index.html`
2. **Aplicación Web SUT:**  
   `labs/lab06-pruebas-funcionales/app/springbootwebapp/target/site/jacoco/index.html`

Para abrir y visualizar los reportes interactivos en el navegador:
```bash
xdg-open labs/lab06-pruebas-funcionales/tests/target/site/jacoco/index.html
xdg-open labs/lab06-pruebas-funcionales/app/springbootwebapp/target/site/jacoco/index.html
```

---

## 7. Instrucciones Rápidas para Reproducir

1. **Compilar y arrancar la aplicación bajo prueba:**
   ```bash
   cd labs/lab06-pruebas-funcionales/app/springbootwebapp
   mvn clean package -DskipTests
   java -jar target/spring-boot-web-0.0.1-SNAPSHOT.jar --server.port=8083
   ```
2. **Ejecutar la suite de pruebas funcionales y cobertura:**
   ```bash
   cd labs/lab06-pruebas-funcionales/tests
   mvn clean test
   ```
