# Laboratorio 06: Pruebas Funcionales con Selenium y xUnit

* **Institución:** Universidad Nacional de San Agustín de Arequipa (UNSA)  
* **Escuela Profesional:** Ciencia de la Computación  
* **Curso:** Ingeniería de Software II  
* **Docente:** DSc. Edgar Sarmiento Calisaya  
* **Tema:** Automatización de Pruebas Funcionales (Caja Negra)  
* **Herramientas:** Selenium WebDriver 4.26.0, JUnit 5 (xUnit), Maven, Java 21, Chromium / ChromeDriver  

---

## 1. Resumen y Objetivos

Este proyecto contiene la solución integral para el **Laboratorio 06**, cuyo objetivo es **diseñar y automatizar pruebas funcionales de caja negra** para la funcionalidad **"Create Product"** del aplicativo web [SpringBootWebApp](https://github.com/springframeworkguru/springbootwebapp), empleando **Selenium WebDriver** y el framework **xUnit (JUnit 5)**.

### Estructura del Laboratorio

```text
labs/lab06-pruebas-funcionales/
├── README.md                      # Documentación completa y reporte de entregables
├── app/
│   └── springbootwebapp/          # Aplicación bajo prueba (SUT - Spring Boot + Thymeleaf)
└── tests/                         # Proyecto de automatización con Selenium y JUnit 5
    ├── pom.xml                    # Dependencias: selenium-java 4.26.0, junit-jupiter 5.11.3
    └── src/
        ├── main/java/
        │   └── dev/selenium/getting_started/
        │       └── UsingSeleniumTest.java          # Script standalone (método main / exec:java)
        └── test/java/
            └── pe/edu/unsa/is2/
                └── CreateProductSeleniumXUnitTest.java # Suite formal xUnit con 5 casos de prueba
```

---

## 2. Entregable 1: Casos de Prueba Identificados (Formato Tabular)

Se aplicaron técnicas de **Caja Negra** (Partición de Clases de Equivalencia y Análisis de Valores Límite - BVA) sobre el formulario de alta de productos (`/product/new`), evaluando campos como identificador, descripción, precio y URL de imagen.

| Objeto de Prueba / Funcionalidad | Escenario / Acciones | Valores de Prueba | Resultado Esperado |
| :--- | :--- | :--- | :--- |
| **Create Product (`/product/new`)** | **TC-CP-01 (Clase de equivalencia válida estándar):**<br>Completar todos los campos del formulario con datos válidos y pulsar "Submit". | `productId`: "PRD-1001"<br>`description`: "Teclado Mecanico RGB"<br>`price`: "49.99"<br>`imageUrl`: "https://example.com/keyboard.jpg" | Redirección exitosa a `/product/{id}`. La página de detalle muestra la descripción, el precio "49.99" y la URL de imagen ingresada. |
| **Create Product (`/product/new`)** | **TC-CP-02 (Análisis de Valor Límite - BVA):**<br>Registrar un producto con el valor mínimo positivo de precio monetario (`0.01`). | `productId`: "PRD-MIN-01"<br>`description`: "Sticker Decorativo"<br>`price`: "0.01"<br>`imageUrl`: "https://example.com/sticker.png" | El producto se registra correctamente; la vista de detalle refleja el precio mínimo "0.01". |
| **Create Product (`/product/new`)** | **TC-CP-03 (Campo opcional omitido):**<br>Crear un producto omitiendo la URL de imagen (campo opcional en blanco). | `productId`: "PRD-NO-IMG"<br>`description`: "Mouse Pad Basico"<br>`price`: "15.50"<br>`imageUrl`: "" *(vacío)* | El sistema procesa la creación sin errores y muestra el producto en detalle sin imagen asignada. |
| **Create Product & Product List (`/product/new` → `/products`)** | **TC-CP-04 (Persistencia y flujo End-to-End):**<br>Crear un nuevo producto y verificar que aparezca registrado en el catálogo general. | `productId`: "PRD-LIST-99"<br>`description`: "Audifonos Gamer 7.1"<br>`price`: "89.90"<br>`imageUrl`: "https://example.com/headset.jpg" | Tras navegar a `/products`, la tabla HTML contiene la fila con la descripción "Audifonos Gamer 7.1" y el precio "89.90". |
| **Create Product (`/product/new`)** | **TC-CP-05 (Clase de equivalencia inválida - Tipo de dato):**<br>Ingresar una cadena alfanumérica en el campo numérico `price` y pulsar "Submit". | `productId`: "PRD-ERR-01"<br>`description`: "Producto Invalido"<br>`price`: "precio_no_numerico"<br>`imageUrl`: "https://example.com/err.jpg" | El sistema rechaza la solicitud (error 400 Bad Request / binding error) y no crea ningún producto con datos corruptos. |

---

## 3. Entregable 2: Proyecto de Automatización de Pruebas

El código de pruebas está desacoplado y estructurado según las buenas prácticas de Selenium WebDriver y JUnit 5:

1. **Ciclo de vida xUnit:**
   * `@BeforeEach`: Inicializa el `ChromeDriver` con opciones optimizadas para ejecución local o en servidores headless (`--headless=new`, `--no-sandbox`, `--disable-dev-shm-usage`).
   * `@Test`: Un método anotado por cada caso de prueba del diseño tabular con aserciones semánticas (`assertTrue`, `assertEquals`, `assertNotNull`).
   * `@AfterEach`: Ejecuta `driver.quit()` garantizando el cierre seguro de los procesos del navegador.

2. **Identificación de Elementos Web:**
   * Formulario: localizadores por ID (`By.id("productId")`, `By.id("description")`, `By.id("price")`, `By.id("imageUrl")`).
   * Botón de envío: selector CSS `By.cssSelector("button[type='submit']")`.
   * Esperas explícitas: uso de `WebDriverWait` y `ExpectedConditions.urlMatches(...)` para sincronizar las redirecciones HTTP asíncronas.

---

## 4. Instrucciones de Compilación y Ejecución

### Prerrequisitos
* Java JDK 17 o 21
* Apache Maven 3.8+
* Google Chrome o Chromium y ChromeDriver instalados

### Paso 1: Levantar la aplicación bajo prueba (SUT)

Desde la raíz del repositorio o entrando a `app/springbootwebapp`:
```bash
cd labs/lab06-pruebas-funcionales/app/springbootwebapp
mvn clean package -DskipTests
java -jar target/spring-boot-web-0.0.1-SNAPSHOT.jar --server.port=8083
```
*Verificar que responda en:* `http://localhost:8083`

### Paso 2: Ejecutar la suite de pruebas xUnit (JUnit 5)

En otra terminal, entrar a la carpeta de pruebas:
```bash
cd labs/lab06-pruebas-funcionales/tests
mvn clean test
```

### Paso 3: (Opcional) Ejecutar el script standalone vía Maven Exec Plugin

Tal como se ilustra en la guía del laboratorio:
```bash
cd labs/lab06-pruebas-funcionales/tests
mvn exec:java -Dexec.mainClass="dev.selenium.getting_started.UsingSeleniumTest"
```

---

## 5. Entregable 3: Reporte de Ejecución de Pruebas (xUnit)

### Resumen de Resultados

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

### Detalle por Caso de Prueba

| Caso de Prueba | Método Test | Duración | Estado |
| :--- | :--- | :--- | :--- |
| **TC-CP-01** | `testCreateProductWithValidData()` | 2.677 s | ✅ **PASSED** |
| **TC-CP-02** | `testCreateProductWithMinimumPriceBoundary()` | 1.350 s | ✅ **PASSED** |
| **TC-CP-03** | `testCreateProductWithoutOptionalImageUrl()` | 1.124 s | ✅ **PASSED** |
| **TC-CP-04** | `testCreateProductAndVerifyInProductsList()` | 1.858 s | ✅ **PASSED** |
| **TC-CP-05** | `testCreateProductWithInvalidPriceType()` | 1.338 s | ✅ **PASSED** |
| **Total** | **5 casos evaluados** | **8.384 s** | 🟢 **100% Exitoso** |

Los archivos XML detallados del reporte generado por Maven Surefire se encuentran en:
`tests/target/surefire-reports/TEST-pe.edu.unsa.is2.CreateProductSeleniumXUnitTest.xml`.
