package pe.edu.unsa.is2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Laboratorio 06 - Pruebas Funcionales con Selenium y xUnit (JUnit 6)
 * Funcionalidad bajo prueba: "Create Product" (Spring Boot Web App)
 */
public class CreateProductSeleniumXUnitTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseUrl = System.getProperty("app.url", "http://localhost:8083");

    @BeforeEach
    public void setUp() {
        // Configurar ruta al chromedriver si existe en el sistema
        if (new File("/usr/bin/chromedriver").exists()) {
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        }

        ChromeOptions options = new ChromeOptions();
        // Modo headless para ejecucion automatizada en servidores y terminales
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("TC-CP-01: Creacion exitosa de producto con todos los datos validos")
    public void testCreateProductWithValidData() {
        driver.get(baseUrl + "/product/new");

        // Completar formulario
        WebElement productIdInput = driver.findElement(By.id("productId"));
        WebElement descriptionInput = driver.findElement(By.id("description"));
        WebElement priceInput = driver.findElement(By.id("price"));
        WebElement imageUrlInput = driver.findElement(By.id("imageUrl"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        productIdInput.sendKeys("PRD-1001");
        descriptionInput.sendKeys("Teclado Mecanico RGB");
        priceInput.sendKeys("49.99");
        imageUrlInput.sendKeys("https://example.com/keyboard.jpg");

        submitButton.click();

        // Verificar redireccion al detalle del producto creado (/product/{id})
        wait.until(ExpectedConditions.urlMatches(".*/product/\\d+$"));
        assertTrue(driver.getCurrentUrl().contains("/product/"), "Debe redirigir al detalle del producto");

        // Verificar que los datos ingresados se muestran en la vista de detalle
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Teclado Mecanico RGB"), "La descripcion debe mostrarse en el detalle");
        assertTrue(pageSource.contains("49.99"), "El precio debe mostrarse en el detalle");
        assertTrue(pageSource.contains("https://example.com/keyboard.jpg"), "La URL de imagen debe coincidir");
    }

    @Test
    @DisplayName("TC-CP-02: Creacion exitosa con valor limite inferior de precio positivo (0.01)")
    public void testCreateProductWithMinimumPriceBoundary() {
        driver.get(baseUrl + "/product/new");

        driver.findElement(By.id("productId")).sendKeys("PRD-MIN-01");
        driver.findElement(By.id("description")).sendKeys("Sticker Decorativo");
        driver.findElement(By.id("price")).sendKeys("0.01");
        driver.findElement(By.id("imageUrl")).sendKeys("https://example.com/sticker.png");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.urlMatches(".*/product/\\d+$"));
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Sticker Decorativo"));
        assertTrue(pageSource.contains("0.01"));
    }

    @Test
    @DisplayName("TC-CP-03: Creacion exitosa de producto omitiendo campo opcional (sin imagen)")
    public void testCreateProductWithoutOptionalImageUrl() {
        driver.get(baseUrl + "/product/new");

        driver.findElement(By.id("productId")).sendKeys("PRD-NO-IMG");
        driver.findElement(By.id("description")).sendKeys("Mouse Pad Basico");
        driver.findElement(By.id("price")).sendKeys("15.50");
        // Se deja el campo imageUrl vacio

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.urlMatches(".*/product/\\d+$"));
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Mouse Pad Basico"));
        assertTrue(pageSource.contains("15.50"));
    }

    @Test
    @DisplayName("TC-CP-04: Persistencia y visualizacion en la lista general de productos (/products)")
    public void testCreateProductAndVerifyInProductsList() {
        driver.get(baseUrl + "/product/new");

        driver.findElement(By.id("productId")).sendKeys("PRD-LIST-99");
        driver.findElement(By.id("description")).sendKeys("Audifonos Gamer 7.1");
        driver.findElement(By.id("price")).sendKeys("89.90");
        driver.findElement(By.id("imageUrl")).sendKeys("https://example.com/headset.jpg");

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlMatches(".*/product/\\d+$"));

        // Navegar a la lista de productos
        driver.get(baseUrl + "/products");

        // Verificar que la tabla contiene el nuevo producto
        WebElement productsTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.table")));
        assertNotNull(productsTable, "La tabla de productos debe estar visible");

        List<WebElement> rows = productsTable.findElements(By.tagName("tr"));
        boolean productFound = rows.stream().anyMatch(row -> {
            String text = row.getText();
            return text.contains("Audifonos Gamer 7.1") && text.contains("89.90");
        });

        assertTrue(productFound, "El producto recien creado debe listarse en la tabla de /products");
    }

    @Test
    @DisplayName("TC-CP-05: Manejo de error al ingresar precio con formato no numerico")
    public void testCreateProductWithInvalidPriceType() {
        driver.get(baseUrl + "/product/new");

        driver.findElement(By.id("productId")).sendKeys("PRD-ERR-01");
        driver.findElement(By.id("description")).sendKeys("Producto Invalido");
        driver.findElement(By.id("price")).sendKeys("precio_no_numerico");
        driver.findElement(By.id("imageUrl")).sendKeys("https://example.com/err.jpg");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // El sistema no debe redirigir a un producto exitoso (/product/{id})
        // En su lugar, Spring lanza Bad Request (400) / Whitelabel Error Page
        String currentUrl = driver.getCurrentUrl();
        assertFalse(currentUrl.matches(".*/product/\\d+$"), "No debe crear un producto con precio no numerico");

        String pageSource = driver.getPageSource();
        boolean isErrorHandled = pageSource.contains("Bad Request")
                || pageSource.contains("Whitelabel Error Page")
                || pageSource.contains("status=400")
                || driver.getTitle().contains("Error");

        assertTrue(isErrorHandled, "La aplicacion debe rechazar el precio no numerico con error 400");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
