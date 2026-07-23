package com.framework.agents;

import com.framework.model.Elementinfo;
import com.framework.utils.ExcelWriter;

import java.util.List;

public class ExcelAgent2 {
	// add this inside ExcelAgent2
	public void saveCsv(List<Elementinfo> elements, String filePath) {
	    try {
	        new ExcelWriter().writeCsv(elements, filePath);
	        System.out.println("Saved CSV to: " + filePath);
	    } catch (Exception e) {
	        System.out.println("Failed to write CSV: " + e.getMessage());
	    }
	}

	public void save(List<Elementinfo> results, String string) {
		// TODO Auto-generated method stub
		
	}
        
        
    }
