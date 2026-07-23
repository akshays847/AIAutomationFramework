package com.framework.model;

public class Elementinfo {

    private String name;
    private String xpath;
    private String tag;
    private String type;

    // Constructor: to build one, you must supply all four facts
    public Elementinfo(String name, String xpath, String tag, String type) {
        this.name = name;
        this.xpath = xpath;
        this.tag = tag;
        this.type = type;
    }

    // Getters: how other classes READ the values back out
    public String getName()  { return name; }
    public String getXpath() { return xpath; }
    public String getTag()   { return tag; }
    public String getType()  { return type; }
}