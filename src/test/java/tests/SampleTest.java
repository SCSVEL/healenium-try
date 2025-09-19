package tests;

import com.epam.healenium.SelfHealingDriver;
import com.epam.healenium.SelfHealingDriverWait;
import io.cucumber.java.BeforeAll;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;


public class SampleTest {
    private static final Logger log = LoggerFactory.getLogger(SampleTest.class);
    WebDriver webDriver;
    WebDriverWait webDriverWait;

    SelfHealingDriver driver;
    SelfHealingDriverWait wait;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        webDriver = new ChromeDriver();
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        //Healenium delegation setup
        driver = SelfHealingDriver.create(webDriver);
        wait = new SelfHealingDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void teardown() {
        webDriver.quit();
        driver.quit();
    }

    @Test
    public void test1() throws InterruptedException {
        //sample test
        driver.get(String.valueOf(Objects.requireNonNull(getClass().getResource("/healenium_sample_site.html"))));
        driver.findElement(By.id("submit")).click();
        Assertions.assertTrue(driver.findElement(By.tagName("p")).getText().contains("Submit"));

        //This will change the submit button attributes
        driver.findElement(By.id("change")).click();

        //Again try the submit button
        driver.findElement(By.id("submit")).click();
        Assertions.assertTrue(driver.findElement(By.tagName("p")).getText().contains("Send"));
    }    
}

