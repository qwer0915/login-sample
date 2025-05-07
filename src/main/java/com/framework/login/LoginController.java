package com.framework.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
	
	@GetMapping("/login-template")
	public String loginTemplate() {
		log.info("Login Page Loading");
		return "login-template"; // templates/login-template.html 경로 작성
	}
	@GetMapping("/login-xhr")
	public String loginTemplatexhr() {
		log.info("XHR");
		return "login-xhr";
	}
	@PostMapping("/request-login")
	@ResponseBody
	public Map<String,Object> requestLogin(@RequestBody Map<String,Object> params){ //@RequestParam String username,String password
		Map<String,Object> result = new HashMap<String,Object>();
		String username = (String) params.get("username");
		String password = (String) params.get("password");
		if("admin".equals(username) && "1234".equals(password)) {
			result.put("REPL_MSG", "success");
			log.info("성공");
		}else {
			result.put("REPL_MSG", "fail");
			log.error("Wrong");
		}
		return result;
	}
}
