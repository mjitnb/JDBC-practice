package ex;

import java.util.List;
import java.util.Scanner;

import dao.face.EmpDao;
import dao.impl.EmpDaoImpl;
import dto.Emp;

//실행 클래스
public class EmpEx {

	
	//EmpDao객체 생성
	//	->객체가 생성되며 DB에 연결한다
	private static EmpDao empDao = new EmpDaoImpl();
	
	
	
	public static void main(String[] args) {

		//DAO객체를 이용하여 Emp테이블 전체 행 조회
		List<Emp> list = empDao.selectAll();
		
		//출력
		for( Emp e : list ) {
			System.out.println(e);
		}
		
		
		
		System.out.println("--------------------------------");
		
		//부서 번호를 입력받아 조회하기
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("조회할 부서 번호는? : ");
		int deptno = sc.nextInt();
		
		List<Emp> resList = empDao.selectByDeptno( deptno );
		
		//조회 결과 출력
		for( Emp e : resList ) {
			System.out.println(e);
		}
		
		
		
		System.out.println("--------------------------------");

		//사원 번호, 사원 이름, 부서번호를 입력받아 데이터 삽입한다( INSERT )
		
		//INSERT할 데이터를 저장할 DTO객체
		Emp insertEmp = new Emp();
		
		System.out.print("사원 번호? ");
		insertEmp.setEmpno( sc.nextInt() );
		
		sc.nextLine(); //버퍼 비우기
		
		System.out.print("사원 이름? ");
		insertEmp.setEname( sc.nextLine() );
		
		System.out.print("부서 번호? ");
		insertEmp.setDeptno( sc.nextInt() );
		
//		System.out.println("[TEST] " + insertEmp);
		
		//DAO를 이용한 데이터 삽입
		int res = empDao.insert( insertEmp );
		
		if( res > 0 ) {
			System.out.println("데이터 삽입 완료");
		}else {
			System.out.println("데이터 삽입 실패");
		}
		
		
		
		System.out.println("--------------------------------");

		
		//삽입된 사원 조회 하기
		//	->입력된 empno(PK)를 이용하여 사원 조회
		
		Emp resEmp = empDao.selectByEmpno( insertEmp.getEmpno() );
		
		if( resEmp == null ) {
			System.out.println("데이터 삽입이 제대로 수행되지 않음");
		}else {
			System.out.println("데이터 삽입 완료");
			System.out.println(resEmp);
		}
		
		
		
	}

}






