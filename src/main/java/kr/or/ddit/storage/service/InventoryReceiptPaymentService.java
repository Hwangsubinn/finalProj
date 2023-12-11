package kr.or.ddit.storage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.storage.vo.InventoryReceiptPaymentVO;

/**
 * <pre>
 * 
 * </pre>
 * @author 유선영
 * @since 2023. 11. 19.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 19.      유선영       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */ 
public interface InventoryReceiptPaymentService {
	/**
	 * 
	 * 재고수불부 현황의 데이터를 조회
	 * 
	 * @param irpVO
	 * @return
	 */
	public List<HashMap<String, Object>> retrieveInventory(Map<String, Object> searchMap);
	
	
}
