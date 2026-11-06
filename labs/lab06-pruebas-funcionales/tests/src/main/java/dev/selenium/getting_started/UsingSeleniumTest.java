package dev.selenium.getting_started;

import java.io.File;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Script de prueba funcional independiente (standalone con metodo main)
 * Basado en la seccion 6.d del Laboratorio 06.
 */
public class UsingSeleniumTest {

    public static void main(String[] args) {
        if (new File("/usr/bin/chromedriver").exists()) {
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            String targetUrl = System.getProperty("app.url", "http://localhost:8083") + "/product/new";
            System.out.println("Navegando a: " + targetUrl);
            driver.get(targetUrl);

            String title = driver.getTitle();
            System.out.println("Titulo de pagina: " + title);

            WebElement productId = driver.findElement(By.id("productId"));
            WebElement description = driver.findElement(By.id("description"));
            WebElement price = driver.findElement(By.id("price"));
            WebElement imageUrl = driver.findElement(By.id("imageUrl"));
            WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

            productId.sendKeys("PRD-DEMO-01");
            description.sendKeys("Monitor Gamer 144Hz");
            price.sendKeys("199.99");
            imageUrl.sendKeys("https://example.com/monitor.jpg");

            System.out.println("Enviando formulario de creacion de producto...");
            submitButton.click();

            Thread.sleep(1000);
            System.out.println("URL final tras submit: " + driver.getCurrentUrl());
            System.out.println("Prueba de Create Product ejecutada exitosamente!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
