package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.face.EmpDao;
import dto.Emp;

public class EmpDaoImpl implements EmpDao {

	//OJDBC 드라이버
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	
	//DB연결 정보
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERNAME = "scott";
	private static final String PASSWORD = "tiger";
	
	//OJDBC 객체
	private static Connection conn = null;	//DB연결객체
	private static PreparedStatement ps = null;	//SQL 수행객체
	private static ResultSet rs = null;	//조회결과 객체
	
	
	
	//생성자
	public EmpDaoImpl() {
		try {
			//드라이버 로드
			Class.forName(DRIVER);
			
			//DB연결
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	@Override
	public List<Emp> selectAll() {
	//코드 엄청 길거임. 대부분 그럼. 
	//설명은 인터페이스에서 할거라서 주석은 간단히 별거 없을 예정.
		
		//--- SQL 작성---
		String sql = "";
		sql +="SELECT * FROM emp";
		sql +=" ORDER BY empno";
		
		
		//--- 전체 조회 결과 List ---
		List<Emp> list = new ArrayList<>();
		
		try {
			//--- SQL 수행 객체 생성 ---
			ps = conn.prepareStatement(sql);
			
			//--- SQL 수행 및 결과 저장 ---
			rs = ps.executeQuery();
			
			//--- 조회 결과 처리 ---
			while( rs.next()) {
				
				//조회결과의 각 행 데이터를 저장할 객체
				Emp emp = new Emp();
				
				emp.setEmpno( rs.getInt("empno") );
				emp.setEname( rs.getString("ename") );
				emp.setJob( rs.getString("job") );
				emp.setMgr( rs.getInt("mgr") );
				
				emp.setHiredate( rs.getDate("hiredate") );
				emp.setSal( rs.getDouble("sal") );
				emp.setComm( rs.getDouble("comm") );
				emp.setDeptno( rs.getInt("deptno") );
				
				//행 데이터를 리스트에 추가하기
				list.add(emp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				//--- 자원 해제 ---
				if(rs!=null && !rs.isClosed() ) rs.close();
				if(ps!=null && !ps.isClosed() ) ps.close();
//				if(conn!=null && !conn.isClosed() ) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		// --- 조회 결과 리턴 ---
		return list;
	}
	
	
	
	//ctrl +엔터로 오버라이드 할 메서드 추가
	//(인터페이스에서 기본 구현해야하는 메)
	@Override
	public List<Emp> selectByDeptno(int deptno) {
		
		//--- SQL 작성---
		String sql = "";
		sql +="SELECT * FROM emp";
		sql += " WHERE deptno = ?";
		sql +=" ORDER BY empno";
		
		
		//--- 전체 조회 결과 List ---
		List<Emp> list = new ArrayList<>();
		
		try {
			//--- SQL 수행 객체 생성 ---
			ps = conn.prepareStatement(sql);
			
			//--- SQL 쿼리의 ? 파라미터 채우기 ---
			ps.setInt(1, deptno);
			
			//--- SQL 수행 및 결과 저장 ---
			rs = ps.executeQuery();
			
			//--- 조회 결과 처리 ---
			while( rs.next()) {
				
				//조회결과의 각 행 데이터를 저장할 객체
				Emp emp = new Emp();
				
				emp.setEmpno( rs.getInt("empno") );
				emp.setEname( rs.getString("ename") );
				emp.setJob( rs.getString("job") );
				emp.setMgr( rs.getInt("mgr") );
				
				emp.setHiredate( rs.getDate("hiredate") );
				emp.setSal( rs.getDouble("sal") );
				emp.setComm( rs.getDouble("comm") );
				emp.setDeptno( rs.getInt("deptno") );
				
				//행 데이터를 리스트에 추가하기
				list.add(emp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				//--- 자원 해제 ---
				if(rs!=null && !rs.isClosed() ) rs.close();
				if(ps!=null && !ps.isClosed() ) ps.close();
//				if(conn!=null && !conn.isClosed() ) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		// --- 조회 결과 리턴 ---
		return list;
	}

	
	
	
	@Override
	public int insert(Emp insertEmp) {
		
		///---SQL 작성 ---
		String sql = "";
		sql +="INSERT INTO emp ( empno, ename, deptno )";
		sql += " VALUES ( ?, ?, ?)";
		
		
		//수행결과 저장 변수
		//	->영향받은 행의 수 저장
		int res = 0;
		
		try {
			//--- SQL 수행 객체 생성 ----
			ps = conn.prepareStatement(sql);
			
			//---SQL의 ? 파라미터 채우기 ---
			ps.setInt(1, insertEmp.getEmpno());
			ps.setString(2, insertEmp.getEname());
			ps.setInt(3, insertEmp.getDeptno());
			
			//--- SQL 수행 및 결과 저장 ---
			res = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null && !ps.isClosed()) ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//최종 결과 반환
		return res;
	}


	
	

	@Override
	public Emp selectByEmpno(int empno) {

		//--- SQL 작성 ---
		String sql = "";
		sql +="SELECT * FROM emp";
		sql += " WHERE empno = ?";
		
		
		//--- 조회 결과를 저장할 객체 ---
		Emp emp = null;
		
		try {
			//--- SQL 수행 객체 생성 ----
			ps = conn.prepareStatement(sql);
			
			//---SQL의 ? 파라미터 채우기 ---
			ps.setInt(1, empno);
			
			//--- SQL 수행 및 결과 저장 ---
			rs = ps.executeQuery();
			
			//--- 조회 결과 처리 -----
			while( rs.next() ) {
				emp = new Emp();//데이터를 저장할 객체 생성
				
				emp.setEmpno( rs.getInt("empno") );
				emp.setEname( rs.getString("ename") );
				emp.setJob( rs.getString("job") );
				emp.setMgr( rs.getInt("mgr") );
				
				emp.setHiredate( rs.getDate("hiredate") );
				emp.setSal( rs.getDouble("sal") );
				emp.setComm( rs.getDouble("comm") );
				emp.setDeptno( rs.getInt("deptno") );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				//--- 자원 해제 ---
				if(rs!=null && !rs.isClosed() ) rs.close();
				if(ps!=null && !ps.isClosed() ) ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		//--- 최종결과 반환 ---
		return emp;
	}
	
}
