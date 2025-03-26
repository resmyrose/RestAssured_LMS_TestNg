package com.api.model.responses;

import lombok.Data;

@Data
public class BatchResponse {
	private int batchId;
	private String batchName;
	private String batchDescription;
	private String batchStatus;
	private int batchNoOfClasses;
	private int programId;
	private String programName;

}
