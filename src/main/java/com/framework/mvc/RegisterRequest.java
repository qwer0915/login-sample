package com.framework.mvc;

import lombok.*;

@Getter
@Setter
public class RegisterRequest {
	private String email;
	private String name;
	private String password;
	private String confirmPassword;
}
