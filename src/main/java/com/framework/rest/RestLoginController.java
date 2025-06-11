package com.framework.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/rest")
public class RestLoginController {
	RestLoginService serviceRest;
	public RestLoginController(RestLoginService serviceRest) {
		this.serviceRest =serviceRest;
	}
	@GetMapping("login")
	public String login() {
		log.info("REQUEST login page");
		return "login";
	}
	@PostMapping(value="/request-login",produces="application/json")
	@ResponseBody
	public Map<String,Object> requestLogin(@RequestBody Map<String,Object> param,HttpSession session){
		Map<String,Object> result=new HashMap<>();
		result=serviceRest.requestLogin(param);
		if("0000".equals((String)result.get("REPL_CD"))) {
			session.setAttribute("userId", param.get("userPw"));
		}
		return result;
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		log.info("로그아웃 요청");
		session.invalidate(); // 현재 세션 정보를 비우는 메서드
		return "redirect:/rest/login";
	}
}
