package dao.face;

import java.util.List;

import dto.Emp;

public interface EmpDao {

	/**
	 * 전체 행을 조회한다
	 * PK인 empno를 기준으로 정렬한다
	 * 
	 * @return List<Emp> - 테이블 전체를 조회한 행을 리스트 객체로 반환한다
	 */
	public List<Emp> selectAll();
	
	
}
