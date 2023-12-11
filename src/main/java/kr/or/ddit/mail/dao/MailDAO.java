package kr.or.ddit.mail.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.employee.vo.EmpVO;
import kr.or.ddit.mail.vo.MailVO;
import kr.or.ddit.paging.vo.PaginationInfo;

@Mapper
public interface MailDAO {
	public List<MailVO> selectMailList(PaginationInfo<MailVO> paging);
	public List<MailVO> selectUMailList(PaginationInfo<MailVO> paging);
	public List<MailVO> selectSMailList(PaginationInfo<MailVO> paging);
	public int selectTotalRecord(PaginationInfo<MailVO> paging);
	public int selectTotalURecord(PaginationInfo<MailVO> paging);
	public int selectTotalSRecord(PaginationInfo<MailVO> paging);
	public int readYN(MailVO mailVO);
	public MailVO selectMail(int mailNo);
	public int insertMail(MailVO mailVO);
	public List<EmpVO> mailAddr(int deptNo);
	//받은메일 삭제
	public int updateInbox (int mailNo);
	//보낸메일 삭제
	public int updateSentMail (int mailNo);
	//안읽은메일 삭제
	public int updateUnreadMail (int mailNo);
}
