package ex;

import java.util.List;

import dao.face.EmpDao;
import dao.impl.EmpDaoImpl;
import dto.Emp;

//실행 클래스
public class EmpEx {

	
	//EmpDao객체 생성
	private static EmpDao empDao = new EmpDaoImpl();
	
	
	
	public static void main(String[] args) {

		//DAO객체를 이용하여 Emp테이블 전체 행 조회
		List<Emp> list = empDao.selectAll();
		
		//출력
		for( Emp e : list ) {
			System.out.println(e);
		}
	}

}
