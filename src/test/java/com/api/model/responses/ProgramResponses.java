package com.api.model.responses;

import lombok.Data;

@Data
public class ProgramResponses {
	
	private int programId;
	private String programName;
	private String programDescription;
	private String programStatus;
	private String creationTime;
	private String lastModTime;
	
	

}
