package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class HomePage {

    private WebDriver driver;

    // LOCATORS — the 3 elements your scraper found, stored in one place
    private By fromPortDropdown = By.xpath("//select[@name='fromPort']");
    private By toPortDropdown   = By.xpath("//select[@name='toPort']");
    private By findFlightsButton = By.xpath("//input[@type='submit']");

    // CONSTRUCTOR — hand it the browser, like your agents
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // ACTION — select cities and click search, all in one method
    public ResultPage searchFlights(String fromCity, String toCity) {
        // Select is Selenium's helper for dropdowns
        Select from = new Select(driver.findElement(fromPortDropdown));
        from.selectByVisibleText(fromCity);

        Select to = new Select(driver.findElement(toPortDropdown));
        to.selectByVisibleText(toCity);

        driver.findElement(findFlightsButton).click();
        return new ResultPage(driver);
    }
}