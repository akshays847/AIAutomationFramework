package com.framework.runner;

import com.framework.agents.DOMAgent;
import com.framework.agents.TestGeneratorAgent;
import com.framework.driver.DriverFactory;
import org.openqa.selenium.WebDriver;

public class AgentRunner {

    public static void main(String[] args) throws Exception {

        // INPUT — yahan URL aur test description do
        String url = "https://demo.evershop.io/";
        String testDescription = "Click on the product named 'Modern Ceramic Vase - Black'";

        // 1. browser kholo, page load karo
        WebDriver driver = DriverFactory.getDriver();  // ya aapka method naam
        driver.get(url);
        Thread.sleep(4000);   // page load hone do

        // 2. page ka HTML padho
        String pageHtml = driver.getPageSource();
        // HTML bahut bada ho sakta hai — pehle 8000 characters bhejo (LLM limit ke liye)
        if (pageHtml.length() > 8000) {
            pageHtml = pageHtml.substring(0, 8000);
        }

        driver.quit();   // HTML mil gaya, browser band

        // 3. agent se code generate karwao
        TestGeneratorAgent agent = new TestGeneratorAgent();
        String generatedCode = agent.generateTestCode(url, pageHtml, testDescription);

        // 4. code print + save karo
        System.out.println("===== GENERATED CODE =====");
        System.out.println(generatedCode);

        agent.saveToFile(generatedCode, "GeneratedTest.java");
    }
}