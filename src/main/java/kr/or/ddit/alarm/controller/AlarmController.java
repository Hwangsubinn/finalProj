package kr.or.ddit.alarm.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.alarm.dao.AlarmDAO;
import kr.or.ddit.alarm.service.AlarmService;
import kr.or.ddit.alarm.vo.AlarmVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AlarmController {
	
	@Inject
	private AlarmService service;
	
	@Inject
	private AlarmDAO dao;
	
	@RequestMapping("/alarm/mail")
	public String mailAlarmList(Model model, Authentication authentication) {
		String empCd = authentication.getName();
		List<AlarmVO> alarmVO = service.retrieveMailAlarmList(empCd);
		log.info("77777777777{}", empCd);
		log.info("알ㄹ람브이ㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣ오{}", alarmVO);
		model.addAttribute("alarmVO",alarmVO);
		return "jsonView";
	}
	
	@PutMapping("/alarm/chk")
	@ResponseBody
	public String mailAlarmChk(Model model) {
		dao.updateAlarmChk();
		return "success";
	}
	
	
}
