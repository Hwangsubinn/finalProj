package kr.or.ddit.alarm.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.alarm.dao.AlarmDAO;
import kr.or.ddit.alarm.vo.AlarmVO;
import kr.or.ddit.common.enumpkg.ServiceResult;

@Service
public class AlarmServiceImpl implements AlarmService {

	@Inject
	private AlarmDAO dao;
	
	@Override
	public List<AlarmVO> retrieveMailAlarmList(String empCd) {
		
		return dao.selectMailAlarmList(empCd);
	}




}
