package com.framework.agents;

import org.openqa.selenium.WebElement;

public class XPathAgent {

    // Takes one element, returns the best XPath string for it
    public String generateXPath(WebElement el) {

        String tag = el.getTagName();   // e.g. "input", "button", "a"

        // 1. BEST: does it have an id?
        String id = el.getAttribute("id");
        if (isUsable(id)) {
            return "//*[@id='" + id + "']";
        }

        // 2. does it have a name?
        String name = el.getAttribute("name");
        if (isUsable(name)) {
            return "//" + tag + "[@name='" + name + "']";
        }

        // 3. does it have a placeholder? (common on inputs)
        String placeholder = el.getAttribute("placeholder");
        if (isUsable(placeholder)) {
            return "//" + tag + "[@placeholder='" + placeholder + "']";
        }

        // 4. does it have visible text? (common on buttons/links)
        String text = el.getText();
        if (isUsable(text) && text.length() < 40) {
            return "//" + tag + "[text()='" + text.trim() + "']";
        }

        // 5. weak fallback: use the type attribute
        String type = el.getAttribute("type");
        if (isUsable(type)) {
            return "//" + tag + "[@type='" + type + "']";
        }

        // 6. nothing useful found — return just the tag (we'll improve later)
        return "//" + tag;
    }

    // Helper: is this attribute actually present and not blank?
    private boolean isUsable(String value) {
        return value != null && !value.trim().isEmpty();
    }
}