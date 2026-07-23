package com.framework.agents;

import org.openqa.selenium.WebElement;

public class NamingAgent2 {

    public String generateName(WebElement el) {

        // QUESTION 1: find a meaningful base word
        String base = firstUsable(
            el.getAttribute("id"),
            el.getAttribute("name"),
            el.getAttribute("placeholder"),
            el.getAttribute("aria-label"),
            el.getText()
        );

        // if nothing was found, fall back to the tag name
        if (base == null) {
            base = el.getTagName();
        }

        // QUESTION 2: figure out the suffix from the tag/type
        String tag = el.getTagName().toLowerCase();
        String type = el.getAttribute("type");
        String suffix = decideSuffix(tag, type);

        // clean the base: lowercase, replace anything non-letter/number with _
        String clean = base.trim().toLowerCase()
                           .replaceAll("[^a-z0-9]+", "_")
                           .replaceAll("^_+|_+$", "");   // trim leading/trailing _

        return clean + "_" + suffix;
    }

    // pick the suffix based on what kind of element this is
    private String decideSuffix(String tag, String type) {
        switch (tag) {
            case "a":        return "link";
            case "button":   return "button";
            case "select":   return "dropdown";
            case "textarea": return "textarea";
            case "input":
                if ("submit".equalsIgnoreCase(type) || "button".equalsIgnoreCase(type))
                    return "button";
                if ("checkbox".equalsIgnoreCase(type)) return "checkbox";
                if ("radio".equalsIgnoreCase(type))    return "radio";
                return "input";
            default:
                return "element";
        }
    }

    // return the first value that's actually present and not blank
    private String firstUsable(String... values) {
        for (String v : values) {
            if (v != null && !v.trim().isEmpty()) {
                return v;
            }
        }
        return null;
    }
}