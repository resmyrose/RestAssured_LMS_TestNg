package com.api.model.responses;

import java.util.List;

import lombok.Data;

@Data
public class LoginResponse {
	
	private String token;
	private String type;
	private String userId;
	private String email;
	private List<String> roles;
	private String status;
	private boolean passwordExpired;
	
	
	
}
