package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PurchasePage {
	
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	
	public PurchasePage(WebDriver driver){
		this.driver= driver;
		this.wait = new WebDriverWait(driver,Duration.ofSeconds(10));
	}

	
	private By nameField = By.xpath("//*[@id=\"inputName\"]");
	private By addressField = By.xpath("//*[@id=\"address\"]");
	private By cityField        = By.id("city");
    private By stateField       = By.id("state");
    private By zipField         = By.id("zipCode");
    private By cardNumberField  = By.id("creditCardNumber");
    private By nameOnCard 	    = By.id("nameOnCard");
    private By purchaseButton   = By.xpath("//input[@type='submit']");
    
    public ConfirmationPage enterDetailsPurchase(String name, String address, String city,String state,  String zip,
    		                             String cardNumber, String nameOnCardS ) {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
    	
    	driver.findElement(nameField).sendKeys(name);
    	driver.findElement(addressField).sendKeys(address);
    	driver.findElement(cityField).sendKeys(city);
    	driver.findElement(stateField).sendKeys(state);
    	driver.findElement(zipField).sendKeys(zip);
    	driver.findElement(cardNumberField).sendKeys(cardNumber);
    	driver.findElement(nameOnCard).sendKeys(nameOnCardS);
    	driver.findElement(purchaseButton).click();
    	
    	return new ConfirmationPage(driver);
    	
    	
    }


	
	
}
