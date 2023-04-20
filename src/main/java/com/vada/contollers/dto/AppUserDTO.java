package com.vada.contollers.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AppUserDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private List<String> roles;
}
