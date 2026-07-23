package base;

import com.framework.driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class BaseTest {

    protected WebDriver driver;   // 'protected' so child test classes can use it

    // runs automatically BEFORE each test method
    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.get("https://blazedemo.com/");
    }

    // runs automatically AFTER each test method
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}