package com.tesco.util;

import java.util.UUID;

public class Utility {
	
	public static String generateUniqueId() {
		return String.valueOf(UUID.randomUUID());
	}
	
}
