package com.framework.agents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DOMAgent {

    private WebDriver driver;
    private WebDriverWait wait;

    public DOMAgent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void loadPage(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }

    public boolean switchToFrame(String frameIdOrName) {
        try {
            wait.until(ExpectedConditions
                    .frameToBeAvailableAndSwitchToIt(By.id(frameIdOrName)));
            System.out.println("Switched into iframe: " + frameIdOrName);
            return true;
        } catch (Exception e) {
            System.out.println("Could not switch to iframe '" + frameIdOrName
                               + "': " + e.getMessage());
            return false;
        }
    }

    public void switchToMainPage() {
        driver.switchTo().defaultContent();
        System.out.println("Switched back to main page");
    }

    public List<WebElement> fetchElements() {
        List<WebElement> allElements = new ArrayList<>();
        String[] tagsWeWant = {"input", "button", "a", "select", "textarea"};
        for (String tag : tagsWeWant) {
            allElements.addAll(driver.findElements(By.tagName(tag)));
        }
        return allElements;
    }
}