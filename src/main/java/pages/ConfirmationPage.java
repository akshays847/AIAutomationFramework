package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class ConfirmationPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // LOCATOR — the "Thank you" heading on the confirmation page
    private By confirmationHeading = By.tagName("h1");

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // READ the confirmation message text from the page
    public String getConfirmationMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationHeading));
        WebElement heading = driver.findElement(confirmationHeading);
        return heading.getText();
    }

    // READ the page title
    public String getPageTitle() {
        return driver.getTitle();
    }
}
