package kr.or.ddit.alarm.service;

import java.util.List;

import kr.or.ddit.alarm.vo.AlarmVO;
import kr.or.ddit.common.enumpkg.ServiceResult;

public interface AlarmService {
	public List<AlarmVO> retrieveMailAlarmList(String empCd);
	
	
	
}
