package kr.or.ddit.mail.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.employee.dao.EmpDAO;
import kr.or.ddit.employee.service.EmpService;
import kr.or.ddit.employee.vo.EmpVO;
import kr.or.ddit.mail.service.MailService;
import kr.or.ddit.mail.vo.AttatchVO;
import kr.or.ddit.mail.vo.MailVO;
import kr.or.ddit.paging.BootstrapPaginationRenderer;
import kr.or.ddit.paging.vo.PaginationInfo;
import kr.or.ddit.paging.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/mail")
public class MailController {
	@Inject
	private MailService service;
	@Inject
	private EmpService empService;
	
	@Inject
	private EmpDAO empDAO;
	
	@GetMapping("/list")
	public String mailListRetrieve() throws Exception {
		return "mail/mailList";
	}
	

	@GetMapping("/list2")
	public String mailListRetrieve(Authentication principal, @ModelAttribute("simpleCondition") SearchVO searchVO,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
			@RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord, Model model)
			throws Exception {

		String empCd = principal.getName();
		// searchVO.setSearchType(searchType);
		// searchVO.setSearchWord(searchWord);
		PaginationInfo<MailVO> paging = new PaginationInfo<>(5, 3);
		// paging.setSimpleCondition(simpleCondition); // 키워드 검색 조건
		paging.setCurrentPage(currentPage);
		paging.setSimpleCondition(searchVO);
		paging.setCurrentPage(currentPage);
		paging.setEmpCd(empCd);
		List<MailVO> mailVO = service.retrieveMailList(paging);
		paging.setDataList(mailVO);
		paging.setRenderer(new BootstrapPaginationRenderer());
		model.addAttribute("paging", paging);
		model.addAttribute("simpleCondition", paging.getSimpleCondition());
		log.info("서치브이오444444444444444444444{}",searchType);
		log.info("심플컨디션444444444444444444444{}", paging.getSimpleCondition());
		return "jsonView";
	}

	@GetMapping("/test")
	public String mailtest() {
		return "mail/test";
	}

	
	
	@GetMapping("/ulist")
	public String mailUListRetrieve() throws Exception {
		return "mail/mailUList";
	}
	
	@GetMapping("/ulist2")
	public String mailUListRetrieve
			(
			Authentication principal,
			@ModelAttribute("simpleCondition") SearchVO searchVO,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
			@RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord, 
			Model model
			)
			throws Exception {

		String empCd = principal.getName();
		// searchVO.setSearchType(searchType);
		// searchVO.setSearchWord(searchWord);
		PaginationInfo<MailVO> paging = new PaginationInfo<>(5, 3);
		// paging.setSimpleCondition(simpleCondition); // 키워드 검색 조건
		paging.setCurrentPage(currentPage);
		paging.setSimpleCondition(searchVO);
		paging.setCurrentPage(currentPage);
		paging.setEmpCd(empCd);
		List<MailVO> mailVO = service.retrieveUMailList(paging);
		paging.setDataList(mailVO);
		paging.setRenderer(new BootstrapPaginationRenderer());
		model.addAttribute("paging", paging);
		model.addAttribute("simpleCondition", paging.getSimpleCondition());
		log.info("서치브이오444444444444444444444{}",searchType);
		log.info("심플컨디션444444444444444444444{}", paging.getSimpleCondition());
		return "jsonView";
	}

	
	@GetMapping("/slist")
	public String mailSListRetrieve() throws Exception {
		return "mail/mailSList";
	}

	@GetMapping("/slist2")
	public String mailSendListRetrieve(Authentication principal, @ModelAttribute("simpleCondition") SearchVO searchVO,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
			@RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord, Model model)
			throws Exception {

		String empCd = principal.getName();
		// searchVO.setSearchType(searchType);
		// searchVO.setSearchWord(searchWord);
		PaginationInfo<MailVO> paging = new PaginationInfo<>(5, 3);
		// paging.setSimpleCondition(simpleCondition); // 키워드 검색 조건
		paging.setCurrentPage(currentPage);
		paging.setSimpleCondition(searchVO);
		paging.setCurrentPage(currentPage);
		paging.setEmpCd(empCd);
		List<MailVO> mailVO = service.retrieveSMailList(paging);
		paging.setDataList(mailVO);
		paging.setRenderer(new BootstrapPaginationRenderer());
		model.addAttribute("paging", paging);
		model.addAttribute("simpleCondition", paging.getSimpleCondition());
		return "jsonView";
	}

	@GetMapping("/{mailNo}")
	public String mailRetrieve(@PathVariable int mailNo) throws Exception {
		return "mail/mailView";
	}

	@GetMapping("/view/{mailNo}")
	public String mailRetrieve(Authentication principal, @PathVariable int mailNo, Model model) throws Exception {
		String empCd = principal.getName();
		MailVO mailVO = service.retrieveMail(mailNo, empCd);
		List<AttatchVO> att = mailVO.getAttatchList();
		log.info("555555555555555555555{}", att);
		model.addAttribute("mailVO", mailVO);

		return "jsonView";
	}

	// 메일 보내기
	@ModelAttribute("sendMail")
	public MailVO mail(Authentication authentication) {
		MailVO mail = new MailVO();
		EmpVO emp = new EmpVO();
		mail.setMailSen(authentication.getName());
		emp = empService.retrieveEmp(authentication.getName());
		String SenNm = emp.getEmpNm();
		mail.setSenNm(SenNm);
		return mail;
	}

	// 메일 보내기
//	@ModelAttribute("sendMail")
//	public EmpVO emp( Authentication authentication) {
//		EmpVO empVO = new EmpVO();
//		String empCd = authentication.getName();
//		empVO.setEmpCd(authentication.getName());
//		empVO = empDao.selectEmp(empCd);
//		return empVO;
//	}

	@GetMapping("/send")
	public String mailSend( ) {
		return "mail/mailSend";
	}

	@PostMapping("/send")
	@ResponseBody
	public MailVO mailSend(Authentication authentication, 
			MailVO mailVO
			) {
		String mailSen = authentication.getName();
		log.info("수정 체킁: {}",mailVO);
		System.out.println("#####################################3 --- mailSend ");
		//List<EmpVO> empVO = empService.retrieveEmpMList();
		
		mailVO.setMailSen(mailSen);
		
		String senNm = empDAO.getName(mailSen);
		String recNm = empDAO.getName(mailVO.getMailRec());
		MailVO retMailVO = new MailVO();
		retMailVO.setMailRec(mailVO.getMailRec());
		retMailVO.setMailSen(mailSen);
		retMailVO.setRecNm(recNm);
		retMailVO.setSenNm(senNm);
		
		
		
//		log.info("nnnnnnnnnnnnn{}", mail);
//		model.addAttribute("empVO", empVO);
//		model.addAttribute(mail);
//		mail.setMailSen(mailVO.getMailSen());
		service.createMail(mailVO);
		
//		model.addAttribute(result);
		
		//model.addAttribute(alarmVO);
		
//		if (result.equals(ServiceResult.OK)) {
//			ra.addFlashAttribute("message", "메일이 등록 처리되었습니다!");
//			return "redirect:/mail/slist";
//		} else {
//			ra.addFlashAttribute("message", "서버에서, 다시 시도해주세요!");
//			return "redirect:/mail/send";
//		}
		
		
		
		
		return retMailVO;
	}

	@Value("{appInfo.mailImagesUrl")
	private String mailImagesUrl;

	@Value("{appInfo.mailImagesUrl")
	private Resource mailImages;

	
	public String imageUpload(MultipartFile upload, Model model, HttpServletRequest req) throws IOException {
		if (!upload.isEmpty()) {
			String saveName = UUID.randomUUID().toString();
			File saveFolder = mailImages.getFile();
			File saveFile = new File(saveFolder, saveName);
			upload.transferTo(saveFile); // upload 완료

			String url = req.getContextPath() + mailImagesUrl + "/" + saveName;
			model.addAttribute("uploaded", 1);
			model.addAttribute("fileName", upload.getOriginalFilename());
			model.addAttribute("url", url);
		} else {
			model.addAttribute("uploaded", 0);
			model.addAttribute("error", Collections.singletonMap("xmessage", "업로드된 파일 없음."));
		}
		return "jsonView";
	}
	/*
	 * @PostMapping("imsi")
	 * 
	 * @ResponseBody public Map<String, Object> imageUpload(MultipartFile upload,
	 * HttpServletRequest req) throws IOException {
	 * 
	 * Map<String, Object> retMap = new HashMap<String, Object>();
	 * 
	 * if (!upload.isEmpty()) { String saveName = UUID.randomUUID().toString(); File
	 * saveFolder = mailImages.getFile(); File saveFile = new File(saveFolder,
	 * saveName); upload.transferTo(saveFile); // upload 완료
	 * 
	 * String url = req.getContextPath() + mailImagesUrl + "/" + saveName;
	 * retMap.put("uploaded", 1); retMap.put("fileName",
	 * upload.getOriginalFilename()); retMap.put("url", url); } else {
	 * retMap.put("uploaded", 0); retMap.put("error",
	 * Collections.singletonMap("message", "업로드된 파일 없음.")); } return retMap; }
	 * 
	 */
	
	@Value("#{appInfo.mailFiles}")
	private Resource mailFiles;

	@GetMapping("/{mailNo}/mailFile/{mailAtchNo}")
	public ResponseEntity<Resource> download(@PathVariable int mailAtchNo) throws IOException {
		AttatchVO atch = service.retrieveAttatch(mailAtchNo);
		if (atch == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		Resource mailFile = mailFiles.createRelative(atch.getMailSvNm());
		if (!mailFile.exists()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 파일이 없음.");
		}

		ContentDisposition disposition = ContentDisposition.attachment()
				.filename(atch.getMailOrgNm(), Charset.defaultCharset()).build();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(disposition);
		headers.setContentLength(atch.getMailAtchSize());
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return ResponseEntity.ok().headers(headers).body(mailFile);

	}

	@GetMapping("/addr")
	public String mailAddr(@RequestParam("what") int deptNo, Model model) throws Exception {
		log.info("5555555555555555555{}ffff", deptNo);
		List<EmpVO> empVO = service.mailAddr(deptNo);
		model.addAttribute("empVO", empVO);
		return "jsonView";
	}
	

	@DeleteMapping("/delete")
	public String deleteInbox(@RequestBody int[] mailNos, Model model) {
		service.removeInbox(mailNos);
		model.addAttribute("success", true);
		return "jsonView";
	}

	@DeleteMapping("/sdelete")
	public String deleteSentMail(@RequestBody int[] mailNos, Model model) {
		service.removeSentMail(mailNos);
		model.addAttribute("success", true);
		return "jsonView";
	}
	
	@DeleteMapping("/udelete")
	public String deleteUnreadMail(@RequestBody int[] mailNos, Model model) {
		service.removeUnreadMail(mailNos);
		model.addAttribute("success", true);
		return "jsonView";
	}

}