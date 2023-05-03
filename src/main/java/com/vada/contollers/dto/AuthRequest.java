package com.vada.contollers.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
	private long id;
	private String username;
	private String email;
	private List<String> roles = new ArrayList<>();
}
