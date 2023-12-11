package kr.or.ddit.chart.vo;

import lombok.Data;

@Data
public class OfficeChartVO {
	private String empCd;
	private String pplanCd;
	private String pordCd;
	private String pordDate;
	private String pordStat;
	private String dueDate;
	private String cqteCd;
	private Integer statCount;
}
