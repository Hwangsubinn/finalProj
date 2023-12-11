package kr.or.ddit.mail.service;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import kr.or.ddit.alarm.dao.AlarmDAO;
import kr.or.ddit.alarm.vo.AlarmVO;
import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.employee.dao.EmpDAO;
import kr.or.ddit.employee.vo.EmpVO;
import kr.or.ddit.mail.MailNotFoundException;
import kr.or.ddit.mail.dao.AttatchDAO;
import kr.or.ddit.mail.dao.MailDAO;
import kr.or.ddit.mail.vo.AttatchVO;
import kr.or.ddit.mail.vo.MailVO;
import kr.or.ddit.paging.vo.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

	@Inject
	private MailDAO dao;

	@Inject
	private AttatchDAO attatchDAO;

	@Inject
	private AlarmDAO alarmDAO;

	@Inject
	private EmpDAO empDAO;

	@Value("#{appInfo.mailFiles}")
	private Resource mailFiles;

	private void procesMailFiles(MailVO mailVO) {
		log.info("첨부파일");
		List<AttatchVO> attatchList = mailVO.getAttatchList();
		log.info("이거봐{}", attatchList);
		if (attatchList != null) {
			attatchList.forEach((atch) -> {
				try {
					atch.setMailNo(mailVO.getMailNo());
					attatchDAO.insertAttatch(atch);
					atch.saveTo(mailFiles.getFile());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

			});
		}

	}

	@Override
	public List<MailVO> retrieveMailList(PaginationInfo<MailVO> paging) {
		log.info("다오ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ{}", dao.selectMailList(paging));
		int totalRecord = dao.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		if (paging.getTotalPage() < paging.getCurrentPage()) {
			int cpage = paging.getTotalPage() <= 0 ? 1 : paging.getTotalPage();
			paging.setCurrentPage(cpage);
		}
		return dao.selectMailList(paging);
	}

	@Override
	public List<MailVO> retrieveUMailList(PaginationInfo<MailVO> paging) {
		int totalRecord = dao.selectTotalURecord(paging);
		paging.setTotalRecord(totalRecord);
		if (paging.getTotalPage() < paging.getCurrentPage()) {
			int cpage = paging.getTotalPage() <= 0 ? 1 : paging.getTotalPage();
			paging.setCurrentPage(cpage);
		}
		return dao.selectUMailList(paging);
	}

	@Override
	public List<MailVO> retrieveSMailList(PaginationInfo<MailVO> paging) {
		int totalRecord = dao.selectTotalSRecord(paging);
		paging.setTotalRecord(totalRecord);
		if (paging.getTotalPage() < paging.getCurrentPage()) {
			int cpage = paging.getTotalPage() <= 0 ? 1 : paging.getTotalPage();
			paging.setCurrentPage(cpage);
		}
		return dao.selectSMailList(paging);
	}

	@Override
	public MailVO retrieveMail(int mailNo, String empCd) {
		MailVO mailVO = dao.selectMail(mailNo);
		log.info("zzzzzzzzzzzzzzzzzzzzzz{}", empCd);
		if (mailVO == null) {
			throw new MailNotFoundException(HttpStatus.NOT_FOUND, String.format("%d 해당 메일이 없음.", mailNo));
		} else if (!mailVO.getMailRec().equals(empCd) && !mailVO.getMailSen().equals(empCd)) {
			throw new MailNotFoundException(HttpStatus.NOT_FOUND, String.format("%d 해당 메일이 없음.", mailNo));
		}
		log.info("88888888888888888zzzz{}", mailNo);
		mailVO.setEmpCd(empCd);
		dao.readYN(mailVO);
		return mailVO;

	}

	@Override
	public ServiceResult createMail(MailVO mailVO) {
		String recs = mailVO.getMailRec();
		AlarmVO alarmVO = new AlarmVO();
		log.info("gggggggggg88888888888888888ggg{}", mailVO);
		log.info("gggggggggg4444444444444488ggg{}", mailVO.getMailRec());
		// 쉼표로 문자열을 분할
		String[] rec = recs.split(",");

		int cnt = 0;

		// 분할된 결과 출력
		for (String rrec : rec) {
			mailVO.setMailRec(rrec);

			alarmVO.setAlarmSender(mailVO.getMailSen());

			String sender = empDAO.getName(mailVO.getMailSen());
			String receiver = empDAO.getName(rrec);

			mailVO.setSenNm(sender);
			mailVO.setRecNm(receiver);

			alarmVO.setSenNm(sender);
			alarmVO.setRecNm(receiver);
			alarmVO.setAlarmReceiver(rrec);
			alarmVO.setAlarmCont(sender + "님이 메일을 보냈습니다.");
			log.info("qqqqqqqqqqqqqqqqwwwwwwwwwwww{}", mailVO);
			cnt = dao.insertMail(mailVO);
			procesMailFiles(mailVO);
			alarmDAO.insertMailAlarm(alarmVO);

		}

		ServiceResult result = null;
		if (cnt > 0) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}

		return result;
	}

	@Override
	public AttatchVO retrieveAttatch(int mailAtchNo) {
		AttatchVO atch = attatchDAO.selectAttatch(mailAtchNo);
		if (atch == null)
			throw new MailNotFoundException(HttpStatus.NOT_FOUND, String.format("%d 해당 파일이 없음.", mailAtchNo));

		return atch;
	}

	@Override
	public List<EmpVO> mailAddr(int deptNo) {
		return dao.mailAddr(deptNo);
	}

	@Override
	public ServiceResult removeSentMail(int[] mailNos) {
		int cnt = 0;
		for (int mailNo : mailNos) {
			cnt = dao.updateSentMail(mailNo);
		}
		ServiceResult result = null;
		if (cnt > 0) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public ServiceResult removeInbox(int[] mailNos) {
		int cnt = 0;
		for (int mailNo : mailNos) {
			cnt = dao.updateInbox(mailNo);
		}
		ServiceResult result = null;
		if (cnt > 0) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public ServiceResult removeUnreadMail(int[] mailNos) {
		int cnt = 0;
		for (int mailNo : mailNos) {
			cnt = dao.updateUnreadMail(mailNo);
		}
		ServiceResult result = null;
		if (cnt > 0) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

}
