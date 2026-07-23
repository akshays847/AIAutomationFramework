package com.framework.runner;

import com.framework.driver.DriverFactory;

import pages.ConfirmationPage;
import pages.HomePage;
import pages.PurchasePage;
import pages.ResultPage;

//import com.framework.pages.HomePage;
import org.openqa.selenium.WebDriver;

public class BookingTestRunner {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = DriverFactory.getDriver();
        driver.get("https://blazedemo.com/");

        HomePage homePage = new HomePage(driver);
        homePage.searchFlights("Paris", "London");
        
        Thread.sleep(10000);

        System.out.println("Page title after search: " + driver.getTitle());

     // Page 2: results — choose the first flight
        ResultPage resultsPage = new ResultPage(driver);
        resultsPage.chooseFlight();


        // Page 3: fill form + purchase
        PurchasePage purchasePage = new PurchasePage(driver);
        purchasePage.enterDetailsPurchase(
            "John Smith", "123 Main St", "Toronto",
            "Ontario", "M5V 2T6", "4111111111111111", "aksh");
        
     // Page 4: confirmation — VERIFY the booking worked
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);

        String message = confirmationPage.getConfirmationMessage();
        String title = confirmationPage.getPageTitle();

        System.out.println("Confirmation message: " + message);
        System.out.println("Page title: " + title);

        // THE ASSERTION — did it actually work?
        if (message.contains("Thank you")) {
            System.out.println("TEST PASSED — booking confirmed!");
        } else {
            System.out.println("TEST FAILED — confirmation not found!");
        }

        driver.quit();
        
        // did we reach the purchase page? check the title
        System.out.println("Page title after choosing flight: " + driver.getTitle());
        
        driver.quit();
    }
}