package ojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class OjdbcEx_03 {

		//OJDBC 드라이버
		private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
		
		//DB연결 정보
		private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
		private static final String USERNAME = "scott";
		private static final String PASSWORD = "tiger";
		
		//OJDBC 객체
		private static Connection conn = null;	//DB연결객체
		
		private static Statement st = null;	//SQL수행 객체
		private static PreparedStatement ps = null;	//SQL 수행 객체
		
		private static ResultSet rs = null;	//조회 결과 객체
	
	
	
	public static void main(String[] args) {
		
		//--- 드라이버 로드 ---
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		//--- 조회할 job 입력받기 ---
		Scanner sc = new Scanner(System.in);
		System.out.print("조회할 job 입력 : ");
		String job = sc.nextLine();
		
		
		//--- SQL 작성 ---
		String sql = "";
		sql += "SELECT * FROM emp";
		sql += " WHERE upper(job) = upper('" + job + "')";
		//데이터에 대소문자 섞여있어서 조회 시 검색안되는 걸 막기 위해 둘 다 대문자로 upper
		sql += " ORDER BY empno";
		
		
		
		try {
			//--- DB 연결 ---
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			//--- SQL 수행 객체 ---
			st = conn.createStatement();	//SQL 수행 객체 생성
			rs = st.executeQuery(sql);	//SQL수행 및 결과 저장
			
			//---SQL 조회 결과 처리 ---
			while(rs.next()) {
				System.out.print( rs.getString("empno")+ "\t" );
				System.out.print( rs.getString("ename")+ "\t" );
				System.out.println( rs.getString("job"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				//--- 자원 해제 ---
				if(rs!=null && !rs.isClosed() ) rs.close();
				if(st!=null && !st.isClosed() ) st.close();
				if(conn!=null && !conn.isClosed() ) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		

	}

}