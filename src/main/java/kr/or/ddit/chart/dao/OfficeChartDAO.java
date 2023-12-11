package kr.or.ddit.chart.dao;


import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.chart.vo.OfficeChartVO;


@Mapper
public interface OfficeChartDAO {
	
	public OfficeChartVO officeChartIng();
	public OfficeChartVO officeChartDone();
	public OfficeChartVO officeChartBack();
	public OfficeChartVO officeChartBefore();

	
	
}
