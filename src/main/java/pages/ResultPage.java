package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ResultPage {
	
	
	private WebDriver driver;
	
	private By chooseFlightButton = By.xpath("/html/body/div[2]/table/tbody/tr[1]/td[1]/input");
	
	

	public ResultPage(WebDriver driver) {
        this.driver = driver;
    }
	
     public PurchasePage chooseFlight() {
    	 
    	 WebElement buttons = driver.findElement(chooseFlightButton);
    	 
    	//WebElement firstButton= buttons.get(0);
    	
    	buttons.click();
    	return new PurchasePage(driver);
     }
}
