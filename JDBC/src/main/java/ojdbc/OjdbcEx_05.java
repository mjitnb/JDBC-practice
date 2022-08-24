package ojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dto.Emp;

public class OjdbcEx_05 {

	//OJDBC 드라이버
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	
	//DB연결 정보
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERNAME = "scott";
	private static final String PASSWORD = "tiger";
	
	//OJDBC 객체
	private static Connection conn = null;	//DB연결객체
	private static PreparedStatement ps = null;	//SQL 수행객체
	private static ResultSet rs = null;	//조회결과 수행객체

	
	
	public static void main(String[] args) {
		
		
		//--- 드라이버 로드 ---
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		//--- 조회할 부서 번호 입력받기 ---
		Scanner sc = new Scanner(System.in);
		System.out.print("조회할 부서 번호 입력 : ");
		int deptno = sc.nextInt();
		
		
		//--- SQL 작성 ---
		String sql = "";
		sql +="SELECT * FROM emp";
		sql +=" WHERE deptno = ?";
		sql +=" ORDER BY empno";
		
		
		//-- 조회 결과를 저장할 리스트 ---
		List<Emp> list = new ArrayList<>();
		
		
		try {
			//--- DB 연결 ---
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			//--- SQL 수행 객체 ---
			ps = conn.prepareStatement(sql);
			
			//--- ? 데이터 파라미터 채우기 ---
			ps.setInt(1, deptno);
			
			//--- SQL 수행 및 결과 저장 ---
			rs = ps.executeQuery();
			
			//--- 조회 결과 처리 ---
			while( rs.next() ) {
				
				//조회결과의 각 행의 데이터를 저장할 객체
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
				list.add( emp );
				
			}
			
			//전체 행 데이터 출력
			for(Emp e : list) {
				System.out.println( e );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null && !rs.isClosed()) rs.close();
				if(ps!=null && !ps.isClosed()) ps.close();
				if(conn!=null && !conn.isClosed()) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		

	}

}
