package com.tesco.util;

import java.util.UUID;
/**
 * 
 * @author sindhuri
 *
 */
public class Utility {
	
	public static String generateUniqueId() {
		return String.valueOf(UUID.randomUUID());
	}
	
	private Utility(){
		
	}
	
}
