package com.framework.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/register")
public class RegisetController {
	@GetMapping("/step1")
	public String regisetStep1() {
		return "register/step1";
	}
	@GetMapping("/step2")
	public String regisetStep2Get() {
		return "redirect:/register/step1";
	}
	@PostMapping("/step2")
	public String registerStep2(@RequestParam(value="agree",defaultValue="false") Boolean agree) {
		if( !agree) {return "register/step1";}
		return "register/step2";
	}
	@PostMapping("/step3")
	public String registerStep3(RegisterRequest regReq) {
		return "register/step3";
	}
}
//public String registerStep2(HttpServletRequest request) {
//	String agreeParam = request.getParameter("agree");
//	if( agreeParam == null || ! agreeParam.equals("true")) {return "register/step1";}
//	return "register/step2";
//}