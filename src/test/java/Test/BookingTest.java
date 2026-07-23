package Test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ConfirmationPage;
import pages.HomePage;
import pages.PurchasePage;
import pages.ResultPage;

public class BookingTest extends BaseTest {
	
	
	@DataProvider(name="flightData")
	public Object [][] getFlightData(){
		return new Object [][] {
			
			{"Paris",    "London",  "John Smith", "123 Main St",  "Toronto", "Ontario", "M5V2T6",  "4111111111111111" , "aksk"},
            {"Boston",   "Berlin",  "Jane Doe",   "456 Oak Ave",  "Berlin",  "Berlin",  "10115",   "5500005555555559", "aksk"},
            {"Portland", "Rome",    "Bob Lee",    "789 Pine Rd",  "Rome",    "Lazio",   "00100",   "4222222222222", "aksk"}
        };
		}
		
	
	
	@Test(dataProvider= "flightData")
	public void bookingFlight(String fromCity, String toCity, String name, String address,
            String city, String state, String zip, String card, String cardName) throws InterruptedException {
		
		String message = new HomePage(driver)
                .searchFlights(fromCity, toCity)       // returns ResultPage
                .chooseFlight()                         // returns PurchasePage
                .enterDetailsPurchase(name, address, city, state, zip, card, cardName)  // returns ConfirmationPage
                .getConfirmationMessage();
   
    
    Assert.assertEquals(message, "Thank you for your purchase today!",
            "Confirmation message did not match");
}}
