package kr.or.ddit.chart.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.chart.vo.SaleChartVO;



@Mapper
public interface SaleChartDAO {
	public SaleChartVO saleChart09();
	public SaleChartVO saleChart10();
	public SaleChartVO saleChart11();
	public SaleChartVO saleChart12();
}

