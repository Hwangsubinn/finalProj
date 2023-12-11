package kr.or.ddit.storage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.item.dao.ItemDAO;
import kr.or.ddit.item.service.ItemService;
import kr.or.ddit.item.vo.ItemVO;
import kr.or.ddit.paging.BootstrapPaginationRenderer;
import kr.or.ddit.paging.vo.PaginationInfo;
import kr.or.ddit.storage.dao.InventoryReceiptPaymentDAO;
import kr.or.ddit.storage.service.InventoryReceiptPaymentService;
import kr.or.ddit.storage.service.StorageService;
import kr.or.ddit.storage.vo.StorageVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/invenSituation")
public class InventoryReceiptPaymentController {
	
	@Inject
	private InventoryReceiptPaymentService invenService;
	@Inject
	private ItemService itemService;
	@Inject
	private StorageService service;
	
	@Inject
	InventoryReceiptPaymentDAO irpDao;
	
	@GetMapping("/list")
	public String invenReceiptPaymentList(@ModelAttribute("detailCondition") ItemVO detailCondition
			,@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage
			,Model model) {
		log.info("나와줘@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{}",detailCondition);
		PaginationInfo<ItemVO> paging = new PaginationInfo<>(10,5);
		paging.setCurrentPage(currentPage);
		paging.setDetailCondition(detailCondition);
		
		log.info("나와줘@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{}",paging);
		itemService.retrieveItemList(paging);
		
		paging.setRenderer(new BootstrapPaginationRenderer());
		model.addAttribute("paging", paging);			
		
		return "jsonView";
	}
	
	@GetMapping
	public String invenReceiptPaymentView(Model model) {
		
		List<StorageVO> wareList =  service.retrieveStorageList();
		model.addAttribute("wareList", wareList);				
		
		return "inventory/invenReceiptPayment";
	}
	
	@PostMapping
	public String invenReceiptPayment(Model model, @RequestBody  Map<String, Object> searchMap){
		
		log.info("@@@@@@seachMap 보자 : {}",searchMap);
		log.info("@@@@@@seachMap 보자 : {}",searchMap);
		log.info("@@@@@@seachMap 보자 : {}",searchMap);
		log.info("@@@@@@seachMap 보자 : {}",searchMap);
		log.info("@@@@@@seachMap 보자 : {}",searchMap);
		
		//--------------------------------------------------------------------------------------
		String inputSData = (String)searchMap.get("rmstSdate");
		String inputLData = (String)searchMap.get("rmstLdate");
		
		String outputSDate="";
		String outputLDate="";
		
		
		if (StringUtils.isNotBlank(inputSData) && StringUtils.isNotBlank(inputLData)) {
			//-에서 /로 변환
			outputSDate = inputSData.replace("-", "/");
			outputLDate = inputLData.replace("-", "/");
			
			searchMap.put("rmstSdate", outputSDate);
			searchMap.put("rmstLdate", outputLDate);
		}
		// 여기까진 date 변환과 입력받은 date값 변수에 저장하는 작업
		//------------------------------------------------------------------------------------------
		
		// 새로운 map에 원하는 데이터[itemCd, rmstSdate,rmstLdate,storCate]만 넣어서 보내는 작업
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("rmstSdate", outputSDate);
		dataMap.put("rmstLdate", outputLDate);
		
		List<HashMap<String, Object>> invenList = invenService.retrieveInventory(searchMap);
		log.info("값좀보@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@자꾸나 : {}",invenList);
		Map<String,Integer> totalValueMap = new HashMap<String, Integer>();
		//invenList에서 dataMap으로 데이터를 넣어줌과 동시에 irpDao를 호출하여 
		//전일재고를 위한 일자에 해당하는 입고수량, 출고수량, 보정수량을 가져와서 계산함
		for(Map<String, Object> mapInven : invenList) {
			dataMap.put("storCate", mapInven.get("storCate"));
			dataMap.put("itemCd", mapInven.get("itemCd"));
			
			Integer sValue = irpDao.sDateData(dataMap);
			Integer lValue = irpDao.LDateData(dataMap);
			Integer dValue = irpDao.dDateData(dataMap);
			//NULL오류를 피하기위해 3항연산자로 NULL일경우 0으로 만듬
			sValue = sValue != null ? sValue : 0;
		    lValue = lValue != null ? lValue : 0;
		    dValue = dValue != null ? dValue : 0;
			//전일재고 계산
			Integer totalValue =sValue-lValue - dValue;
			//계산된 데이터를 출력할 Map으로 내보냄
			totalValueMap.put((String)mapInven.get("itemCd"), totalValue);
			
		}
		
		model.addAttribute("invenList",invenList);
		model.addAttribute("totalValueMap",totalValueMap);
		log.info("값좀보자꾸나 : {}",invenList);
		log.info("값좀보자꾸나 : {}",totalValueMap);
		return "jsonView";
	}
}
