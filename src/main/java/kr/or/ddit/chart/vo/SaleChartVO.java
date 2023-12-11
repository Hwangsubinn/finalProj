package kr.or.ddit.chart.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SaleChartVO {
	private Integer saleUprc;
	private Integer saleQty;
	private Integer totalSale;
	private BigDecimal saleSum;
	private String itemCd;
	private String saleCd;
	private String saleDate;
	private String saleStat;
	private String comCd;
	
}
