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
				if(conn!=null && !conn.isClosed() ) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		// --- 조회 결과 리턴 ---
		return list;
	}
	

}
