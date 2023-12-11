package kr.or.ddit.mail.service;

import java.util.List;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.employee.vo.EmpVO;
import kr.or.ddit.mail.vo.AttatchVO;
import kr.or.ddit.mail.vo.MailVO;
import kr.or.ddit.paging.vo.PaginationInfo;

public interface MailService {
	public List<MailVO> retrieveMailList(PaginationInfo<MailVO> paging);
	
	public List<MailVO> retrieveUMailList(PaginationInfo<MailVO> paging);

	public List<MailVO> retrieveSMailList(PaginationInfo<MailVO> paging);
	
	public MailVO retrieveMail(int mailNo, String empCd);

	public ServiceResult createMail(MailVO mailVO);
	
	public AttatchVO retrieveAttatch(int mailAtchNo);
	
	public List<EmpVO> mailAddr(int deptNo);
	
	public ServiceResult removeInbox (int[] mailNos);
	
	public ServiceResult removeSentMail (int[] mailNos);
	
	public ServiceResult removeUnreadMail (int[] mailNos);
}
