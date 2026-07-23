package com.framework.runner;

import com.framework.driver.DriverFactory;
import com.framework.agents.DOMAgent;
import com.framework.agents.XPathAgent;
import com.framework.agents.NamingAgent2;
import com.framework.agents.ExcelAgent2;
import com.framework.agents.LLMAgent;
import com.framework.model.Elementinfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

public class AIFrameworkRunner {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getDriver();

        // create all agents
        DOMAgent domAgent = new DOMAgent(driver);
        XPathAgent xpathAgent = new XPathAgent();
        NamingAgent2 namingAgent = new NamingAgent2();
        ExcelAgent2 excelAgent = new ExcelAgent2();
        LLMAgent llmAgent = new LLMAgent();

        System.out.println("LLM available: " + llmAgent.isAvailable());

        domAgent.loadPage("https://blazedemo.com/");

     // see what page we ACTUALLY landed on
     System.out.println("Page title: " + driver.getTitle());
     System.out.println("Current URL: " + driver.getCurrentUrl());

     List<WebElement> elements = domAgent.fetchElements();
     System.out.println("Elements found: " + elements.size());

        // give JS-heavy pages time to render
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2. THEN fetch the elements
        List<WebElement> elements1 = domAgent.fetchElements();
        System.out.println("Elements found: " + elements1.size());   // DIAGNOSTIC

        // 3. create the results list
        List<Elementinfo> results = new ArrayList<>();

        // 4. now loop over the elements
        for (WebElement el : elements1) {
            String name = namingAgent.generateName(el);

            if (isWeakName(name) && llmAgent.isAvailable()) {
                String smartName = llmAgent.generateName(el);
                if (smartName != null) {
                    name = smartName;
                }
            }

            String xpath = xpathAgent.generateXPath(el);
            String tag   = el.getTagName();
            String type  = el.getAttribute("type");
            if (type == null) type = "";

            results.add(new Elementinfo(name, xpath, tag, type));
        }

        // 5. save the results  (note: save vs saveCsv)
        excelAgent.save(results, "src/test/resources/elements.xlsx");
        excelAgent.saveCsv(results, "src/test/resources/elements.csv");

        driver.quit();
    }

    // helper: a name is "weak" if it's generic junk the LLM could improve
    private static boolean isWeakName(String name) {
        return name == null
            || name.startsWith("a_")
            || name.startsWith("input_")
            || name.startsWith("element_")
            || name.equals("a_link");
    }
}