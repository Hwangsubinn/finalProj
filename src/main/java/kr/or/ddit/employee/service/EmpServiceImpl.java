package kr.or.ddit.employee.service;

import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.employee.dao.EmpDAO;
import kr.or.ddit.employee.vo.EmpVO;
import kr.or.ddit.paging.vo.PaginationInfo;
import lombok.extern.slf4j.Slf4j;


/**
 * @author 이수정
 * @since 2023. 11. 10.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 10.      이수정       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {
	@Inject
	private EmpDAO dao;
	
	@Inject
	private PasswordEncoder encoder;
	
	@Inject
	@Named("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	
	private ServiceResult authenticated(String password) {
		try {
			Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
			
			Authentication inputData = 
					UsernamePasswordAuthenticationToken.unauthenticated(currentUser.getName(), password);
			
			
			
			authenticationManager.authenticate(inputData);
			return ServiceResult.OK;
		}catch(Exception e) {
			return ServiceResult.INVALIDPASSWORD;
		}
	}
	
		@Override
		public EmpVO retrieveEmp(String empCd) {
			EmpVO emp = dao.selectEmp(empCd);
//			if(emp==null)
//				throw new UserNotFoundException(empCd);
			return emp;
		}
		
		//비밀번호 찾기 이메일발송
		@Override
		public void sendEmail(EmpVO vo) throws Exception {
			String host = "smtp.naver.com";	
			String user = "sapphire219@naver.com";	//발신자 아이디
			String password = "aim1129!";		//발신자 비밀번호
			
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.naver.com");
			props.put("mail.smtp.port", 587);		//네이버는 587
			props.put("mail.smtp.auth", true);
			
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});
		
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(user));
			
				//한명
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(vo.getEmpMail()));
				//메일제목
				message.setSubject("AIM 임시번호 발급");
				//메일 내용
				message.setText(vo.getEmpCd() + "님의 임시 비밀번호입니다.\n" 
				+ vo.getEmpPpw());
				
				Transport.send(message);
				System.out.println(vo);
				System.out.println("메일 발송 성공!");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		//비밀번호찾기
		@Override
		public String findPw(EmpVO vo) throws Exception {
			EmpVO ck = dao.selectEmp(vo.getEmpCd());
			// 가입된 아이디가 없으면
			if(dao.idCheck(vo.getEmpCd()) == null) {
				return "no";
			}
			// 가입된 이메일이 아니면
			else if(!vo.getEmpMail().equals(ck.getEmpMail())) {
				return "noMatch";
			}else {
				// 임시 비밀번호 생성
				String pw = "";
				for (int i = 0; i < 12; i++) {
					pw += (char) ((Math.random() * 26) + 97);
				}
				vo.setEmpPpw(pw);
				String epw=encoder.encode(pw);
				
				vo.setEmpPw(epw);
				// 비밀번호 변경
				dao.updatePw(vo);
				// 비밀번호 변경 메일 발송
				sendEmail(vo);
				return "yes";

			}
		}
		
		@Override
		public List<EmpVO> retrieveEmpMList() {
			return dao.selectEmpList();
		}
		
		
		
		@Override
		public List<EmpVO> retrieveEmpList(PaginationInfo<EmpVO> paging) {
			int totalRecord = dao.selectTotalRecord(paging);
			paging.setTotalRecord(totalRecord);
			List<EmpVO> dataList = dao.selectEmployeeList(paging);
			paging.setDataList(dataList);
			return dataList;
		}
		

		@Override
		public ServiceResult createEmp(EmpVO emp) throws Exception {
			log.info("66666666666666666");
			int empRowcnt = dao.insertEmp(emp);
			ServiceResult result = null;
			if(empRowcnt > 0 ) {
				result = ServiceResult.OK;
				String epw=encoder.encode(emp.getEmpPpw());
				emp.setEmpPw(epw);
				dao.updatePw(emp);
			} else { 
				result = ServiceResult.FAIL;
			}
			log.info("666666666666666666{}",result);
			
			
			return result;
		}

		
		
		@Override
		public ServiceResult updateEmp(EmpVO emp) {
			int empRowcnt = dao.updateEmp(emp);
			ServiceResult result = null;
			if(empRowcnt > 0 ) {
				result = ServiceResult.OK;
			} else { 
				result = ServiceResult.FAIL;
			}
			
			
			log.info("666666666666666666{}",result);
			log.info("688888888888888888888{}",emp.getEmpPpw());
			
			return result;
		}

		@Override
		public List<EmpVO> newEmpList() {
			return dao.newEmpList();
		}

		/**
		 * 사용자의 상세 정보를 조회한다
		 * @param empVO 조회하려는 사용자
		 * @return 사용자 상세 정보
		 */
		@Override
		public EmpVO detail(EmpVO empVO) {
			return this.dao.detail(empVO);
		};

		/**
		 * 사용자의 권한정보를 포함한 상세 정보를 조회한다
		 * @param empVO 조회하려는 사용자
		 * @return 사용자 상세 정보
		 */
		@Override
		public EmpVO detailUserAuth(EmpVO empVO) {
			return this.dao.detailUserAuth(empVO);
		};
		
}
