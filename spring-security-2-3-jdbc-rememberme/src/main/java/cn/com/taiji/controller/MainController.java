package cn.com.taiji.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Max;

/**
 * 主页控制器.
 *
 */
@Controller
public class MainController {
	
	@GetMapping("/")
	public String root() {
		return "redirect:/index";
	}
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		model.addAttribute("errorMsg", "登陆失败，用户名或者密码错误！");
		return "login";
	}
	
	@GetMapping("/403")
	public String accesssDenied() {
		return "403";
	}

}
