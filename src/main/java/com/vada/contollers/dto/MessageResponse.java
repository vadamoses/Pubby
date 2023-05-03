package com.vada.contollers.dto;

import lombok.Data;

@Data
public class MessageResponse {

	private String message;

	public MessageResponse(String message) {
		super();
		this.message = message;
	}

}
