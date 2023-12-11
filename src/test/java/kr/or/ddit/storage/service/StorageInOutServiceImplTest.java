package kr.or.ddit.storage.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import kr.or.ddit.order.dao.OrderPlayDAO;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringJUnitWebConfig(locations="file:src/main/resources/kr/or/ddit/spring/conf/*-context.xml")

class StorageInOutServiceImplTest {
	@Inject
	private OrderPlayDAO dao;
	
	@Test
	void test() {
		List<Map<String,Object>> list =   dao.selectPurOrderList();
		log.info("list",list);
	}

}
