package com.api.model.requests;

import lombok.Data;

@Data 
public class Batch {
	
	  private String batcDescription;
	  private String batchName;
	  private int batchNoOfClasses; 
	  private String batchStatus;
	  private int programId;
	public Batch(String batcDescription, String batchName, int batchNoOfClasses, String batchStatus, int programId) {
		super();
		this.batcDescription = batcDescription;
		this.batchName = batchName;
		this.batchNoOfClasses = batchNoOfClasses;
		this.batchStatus = batchStatus;
		this.programId = programId;
	} 
	  
	  
}
