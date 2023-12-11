package kr.or.ddit.alarm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.alarm.vo.AlarmVO;

@Mapper
public interface AlarmDAO {
	
	public List<AlarmVO> selectMailAlarmList(String empCd);
	
	public List<AlarmVO> selectUMailAlarmList();
	
	public int insertMailAlarm(AlarmVO alarmVO);
	
	public int updateAlarmChk();
}
