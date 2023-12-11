package kr.or.ddit.employee.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.employee.service.EmpService;
import kr.or.ddit.employee.vo.EmpVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@Inject
	private EmpService service;
	
	@RequestMapping("/login")
	public String login(
			@RequestParam(value = "error", required = false) String error
			,@RequestParam(value="exception", required = false) String exception
			,Model model
			) {
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "/login/login/login";
	}
	

	@RequestMapping(value = "/findpw", method = RequestMethod.POST)
	@ResponseBody
	public String findPwPOST(@ModelAttribute EmpVO emp, HttpServletResponse response) throws Exception{
		String empCd = emp.getEmpCd();
		service.retrieveEmp(empCd);
		String result = service.findPw(emp);
		return result;
	}
	
}
