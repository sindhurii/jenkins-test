package com.tesco.dropwizard;

import io.dropwizard.Configuration;
/**
 * 
 * @author sindhuri
 *
 */
public class OrderConfiguration extends Configuration {
	
	private String bucketName;

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

}
