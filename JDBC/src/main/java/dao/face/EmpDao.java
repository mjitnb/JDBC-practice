package dao.face;

import java.util.List;

import dto.Emp;

public interface EmpDao {

	/**
	 * JAVA DOC
	 * 
	 */
	
	
	
	/**
	 * 전체 행을 조회한다
	 * PK인 empno를 기준으로 정렬한다
	 * 
	 * @return List<Emp> - 테이블 전체를 조회한 행을 리스트 객체로 반환한다
	 */
	public List<Emp> selectAll();
	
	
	
	
	/**
	 * 지정된 부서번호의 사원들을 조회한다
	 * 사원번호(empno)를 기준으로 정렬한다
	 * 
	 * @param deptno - 조회하려는 부서번호
	 * @return List<Emp> - 해당 부서 번호의 사원들 목록
	 */
	public List<Emp> selectByDeptno(int deptno);

	
	
	
	/**
	 * 새로운 사원 정보를 삽입한다
	 * 
	 * @param insertEmp - 새로 삽입할 사원 정보
	 * @return - 영양받은 행의 수
	 * 		0 - 데이터 삽입 실패
	 * 		1 - 데이터 삽입 성공
	 */
	public int insert(Emp insertEmp);

	
	
	/**
	 * 지정한 사원번호에 해당하는 사원 정보를 조회한다
	 * 
	 * @param empno - 조회할 사원의 사원번호
	 * @return Emp - 조회된 사원의 정보 DTO객체
	 * 		null - 조회된 사원이 없을 경우 반환
	 */
	public Emp selectByEmpno(int empno);
	
	
	
}
