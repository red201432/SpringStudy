package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//允许跨域访问
@CrossOrigin("*")
//注册为控制器
@Controller
@RequestMapping("/")
public class HomeController {
	@GetMapping("/")
	@ResponseBody
	public String home(){
		return "Home";
	}
	
	@GetMapping("/index")
	public String index(){
		return "index";
	}
	
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
	@GetMapping("/register")
	public String register(){
		return "register";
	}

}
