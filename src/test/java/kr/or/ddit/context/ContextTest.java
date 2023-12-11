package kr.or.ddit.context;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringJUnitWebConfig(locations = {"file:src/main/resources/kr/or/ddit/spring/conf/*-context.xml"})
/*Junit5 사용
@ExtendWith(SpringExtension.class)
//Context설정파일 지정 classpath를 사용해도 됨
@ContextConfiguration({"file:src/main/resources/kr/or/ddit/spring/conf/datasource-context.xml"
	                  ,"file:src/main/resources/kr/or/ddit/spring/conf/mapper-context.xml"
})
*/
class ContextTest {
	@Resource(name = "dataSource")
	private  DataSource dataSource;

	
	@Autowired 
	private SqlSessionTemplate sqlSession;
	 
	@Inject 
	private MessageSourceAccessor accessor;
	
	 
	@Test
	void test() {
		log.info("datasource : {}", dataSource);
		log.info("sqlSession : {}", sqlSession);
		log.debug("accessor : {}", accessor);
		//log.info("hi message : {}", accessor.getMessage("hi"));
	}
}
