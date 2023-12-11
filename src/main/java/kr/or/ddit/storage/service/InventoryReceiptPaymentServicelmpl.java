package kr.or.ddit.storage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import kr.or.ddit.storage.dao.InventoryReceiptPaymentDAO;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class InventoryReceiptPaymentServicelmpl implements InventoryReceiptPaymentService {
	
	@Inject
	private InventoryReceiptPaymentDAO irpDao;
	
	
	@Override
	public List<HashMap<String, Object>> retrieveInventory(Map<String, Object> searchMap) {
		
		String inputSData = (String)searchMap.get("rmstSdate");
		String inputLData = (String)searchMap.get("rmstLdate");
		
		if (StringUtils.isNotBlank(inputSData) && StringUtils.isNotBlank(inputLData)) {
			//-에서 /로 변환
			String outputSDate = inputSData.replace("-", "/");
			String outputLDate = inputLData.replace("-", "/");
			
			searchMap.put("rmstSdate", outputSDate);
			searchMap.put("rmstLdate", outputLDate);
		}

		
		return irpDao.retrieveInventory(searchMap);
		
	}

	
}
