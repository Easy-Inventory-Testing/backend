package TestChrome;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.Map;

public class AgregarProductoTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void agregarProducto() {
        driver.get("https://easyinventoryfront.netlify.app/");
        driver.manage().window().setSize(new Dimension(761, 800));
        driver.findElement(By.linkText("Products")).click();
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys("Zapato");
        driver.findElement(By.cssSelector("#date > .p-inputtext")).click();
        driver.findElement(By.cssSelector("tr:nth-child(4) > td:nth-child(5) > span")).click();
        driver.findElement(By.cssSelector("#discount > .p-inputtext")).click();
        driver.findElement(By.cssSelector("#amount > .p-inputtext")).click();
        driver.findElement(By.cssSelector("#price > .p-inputtext")).click();
        driver.findElement(By.cssSelector("#stock > .p-inputtext")).click();
        driver.findElement(By.cssSelector(".flex > .p-button")).click();
    }
}
