package kr.or.ddit.mail.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.mail.vo.AttatchVO;

@Mapper
public interface AttatchDAO {
	public int insertAttatch(AttatchVO attatch);
	public AttatchVO selectAttatch(int attNo);
	public int deleteAttatch(int attNo);
}
