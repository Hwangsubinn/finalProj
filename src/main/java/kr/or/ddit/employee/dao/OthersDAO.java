package kr.or.ddit.employee.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.employee.vo.OthersVO;

@Mapper
public interface OthersDAO {
	public List<OthersVO> selectAuthList();
	public List<OthersVO> selectUseList();
	
}
