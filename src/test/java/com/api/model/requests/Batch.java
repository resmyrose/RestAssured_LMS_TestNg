package com.api.model.requests;

import lombok.Data;

@Data 
public class Batch {
	
	private String batchName; 
	  private String batchDescription; 
	  private int batchNoOfClasses; 
	  private int programId; 
	  private String programName;

}
