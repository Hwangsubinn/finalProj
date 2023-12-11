package kr.or.ddit.alarm.vo;

import lombok.Data;

@Data
public class AlarmVO {
	private String alarmSender;
	private String alarmReceiver;
	private Integer alarmNo;
	private String alarmCont;
	private String alarmCdate;
	private String alarmRdate;
	private String empCd;
	private String senNm;
	private String recNm;
	private String alarmType;
	private String alarmUrl;
	private String alarmChk;
}
