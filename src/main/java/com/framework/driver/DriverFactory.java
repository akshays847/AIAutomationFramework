package com.framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    public static WebDriver driver;

    public static WebDriver getDriver() {

        WebDriverManager.chromedriver().setup();

        // 1. options banao
        ChromeOptions options = new ChromeOptions();

        // 2. arguments add karo (CI/headless ke liye)
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        // 3. ChromeDriver ko options do
        driver = new ChromeDriver(options);

        return driver;
    }
}