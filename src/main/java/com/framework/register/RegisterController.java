package com.framework.register;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/account")
@Slf4j
public class RegisterController {
	RegisterService registerService;
	public RegisterController(RegisterService r) {
		this.registerService=r;
	}
	
	@GetMapping("/register")
	public ModelAndView r() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("register");
		return mv;
	}
	
	@PostMapping("request-register")
	@ResponseBody
	public Map<String,Object> rR(@RequestBody Map<String,Object> params){
		Map<String,Object> result=registerService.requestRegister(params);
		return result;
		
	}
	@GetMapping("/detail/{userId}")
	public ModelAndView gM(@PathVariable("userId") String userId) {
		ModelAndView mv = new ModelAndView();
		Map<String,Object> result = registerService.getMember(userId);
		mv.addObject("result",result);
		mv.setViewName("member-detail");
		return mv;
	}
	
	@PostMapping("request-modify")
	@ResponseBody
	public Map<String,Object> m(@RequestBody Map<String,Object> params){
		Map<String,Object> result = registerService.requestModify(params);
		return result;
	}
	
	@PostMapping("request-remove")
	@ResponseBody
	public Map<String,Object> r(@RequestBody Map<String,Object> params){
		Map<String,Object> result = registerService.requestRemove(params);
		return result;
	}
	//List
	@RequestMapping(value="/list",method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView list(UserForm userform) {
		ModelAndView mv = new ModelAndView();
		Map<String,Object> result = registerService.requestMemberList(userform);
		mv.addObject("result", result);
		mv.setViewName("list");
		return mv;
	}
}
