package kr.or.ddit.employee.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*import jdk.internal.org.jline.utils.Log;*/
import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.employee.dao.OthersDAO;
import kr.or.ddit.employee.service.EmpService;
import kr.or.ddit.employee.vo.EmpVO;
import kr.or.ddit.employee.vo.OthersVO;
import kr.or.ddit.paging.BootstrapPaginationRenderer;
import kr.or.ddit.paging.vo.PaginationInfo;
import kr.or.ddit.paging.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/account")
public class AccountController {
	@Inject
	private EmpService service;
	@Inject
	private OthersDAO othersDAO;

	@ModelAttribute("authList")
	public List<OthersVO> authList() {
		return othersDAO.selectAuthList();
	}

	@ModelAttribute("useList")
	public List<OthersVO> useList() {
		return othersDAO.selectUseList();
	}

	@GetMapping
	public String accountListRetrieve() throws Exception {
		return "account/account";
	}

	@GetMapping("/list")
	public String accountListRetrieve(@ModelAttribute("simpleCondition") SearchVO simpleCondition,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage, Model model)
			throws Exception {
		PaginationInfo<EmpVO> paging = new PaginationInfo<>(10, 3);
		paging.setSimpleCondition(simpleCondition); // 키워드 검색 조건
		paging.setCurrentPage(currentPage);
		service.retrieveEmpList(paging);
		paging.setRenderer(new BootstrapPaginationRenderer());
		model.addAttribute("paging", paging);
		List<EmpVO> empList = service.retrieveEmpList(paging);
		model.addAttribute("empList", empList);
		return "account/account";
	}

	@PostMapping("/form")
	public String accountCreate(EmpVO emp, Model model, RedirectAttributes ra) throws Exception {
		/*
		 * if(valid) { ServiceResult result = service.createEmp(emp);
		 * System.out.println(result); switch(result) { case OK:
		 * model.addAttribute("empVO", emp); rslt="success"; errorMap.put("rslt", rslt);
		 * break; default: model.addAttribute("message", "서버오류입니다. 다시 입력해 주세요");
		 * rslt="fail"; errorMap.put("rslt", rslt); }
		 * 
		 * }
		 */
//			model.addAttribute("errors", errorMap);

		ServiceResult result = service.createEmp(emp);
		if (result.equals(ServiceResult.OK)) {
			ra.addFlashAttribute("message", "신규 사원이 등록 처리되었습니다.");
		} else {
			ra.addFlashAttribute("xmessage", "다시 시도해주세요.");
		}
		return "redirect:/account/list";
	}

	@PostMapping("/edit")
	public String accountUpdate(EmpVO emp, Model model, RedirectAttributes ra) throws Exception {
//		  if(valid) {
//			  ServiceResult result  = service.updateEmp(emp);
//			  log.info("666666666666666666{}",result);
//			  switch(result) {
//				case OK:
//					model.addAttribute("empVO", emp);
//					rslt="success";
//					errorMap.put("rslt", rslt);
//					break;
//				default:
//					model.addAttribute("message", "서버오류입니다. 다시 입력해 주세요");
//					rslt="fail";
//					errorMap.put("rslt", rslt);
//				}
//
//			}
//			model.addAttribute("errors", errorMap);

		ServiceResult result = service.updateEmp(emp);
		if (result.equals(ServiceResult.OK)) {
			ra.addFlashAttribute("message", "사원이 수정 처리되었습니다.");
		} else {
			ra.addFlashAttribute("xmessage", "다시 시도해주세요.");
		}
		return "redirect:/account/list";
	}

	@ResponseBody
	@RequestMapping(value = "/newEmpList", produces = "application/json;charset=utf-8")
	public ResponseEntity<List<EmpVO>> newEmpList() {
		List<EmpVO> empList = service.newEmpList();
		return new ResponseEntity<List<EmpVO>>(empList, HttpStatus.OK);
	}
}
