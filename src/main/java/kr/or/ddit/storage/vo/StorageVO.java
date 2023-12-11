package kr.or.ddit.storage.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import kr.or.ddit.grouphint.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "wareCd")
@Data
public class StorageVO implements Serializable{

	@NotBlank(groups = {UpdateGroup.class})
	private String wareCd;
	@NotBlank
	private String wareNm;
	@NotBlank
	private String wareItem;
	@NotNull
	private int wareWidth;
	@NotNull
	private int wareY;
	@NotBlank
	private String empCd;
	private String empNm; //사원이름
	private String wareLatitude; //위도 db실수로 String으로 받음
	private String wareLongitude; //경도 db실수로 String으로 받음
	private String wareAddr;
	private String wareAddrDetail;
	private String wareZip;
	private String wareTemp;
	
	private List<WareSecVO> wareSecList;
	
	private String secCd;
}
