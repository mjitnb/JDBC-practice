//사실 이런 테이블 만드는 툴은 이미 만들어져 있음
//이건 사용자/이용자 보다 관리자가 쓰는거
//우리는 사실 이런 테이블 만드는건 거의 안함
//일반 테이블 SELECT나 이런걸 많이 쓴다.
//이렇게 자료사전 조회..? 하고 그럴 일은 별로 없음



package ojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ojdbcEx_02 {

	//OJDBC 드라이버
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	
	//DB연결 정보
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERNAME = "scott";
	private static final String PASSWORD = "tiger";
	
	//OJDBC 객체
	private static Connection conn = null;
	private static Statement st = null;
	private static ResultSet rs = null;
	
	
	
	public static void main(String[] args) {

		
		//------- 드라이버 로드 -------
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//-----------------------------
		
		
		//-------- SQL 작성 --------
		String sql1 = "";
		sql1 += "CREATE TABLE usertest (";
		sql1 += "	idx NUMBER CONSTRAINT pk_user_test PRIMARY KEY";
		sql1 += "	,name VARCHAR2(50) NOT NULL";
		sql1 += "	,phone VARCHAR2(50) NOT NULL";
		sql1 += ")";
		
		String sql2="";
		sql2 += "CREATE SEQUENCE seq_usertest";
		sql2 += " INCREMENT BY 1";	// **앞에 탭 or 띄어쓰기 꼭  넣기!!!!!!!!!!
		sql2 += " START WITH 1";
		//---------------------------
		
		
		 
		try {
			//----- DB 연결 ----
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			//------------------
			
			
			
			//---- SQL 수행 ----
			st = conn.createStatement(); //SQL 수행 객체
			
			
			st.execute("DROP TABLE usertest");	//테이블 삭제 쿼리 수행
			st.execute(sql1);	//테이블 생성 쿼리 수행
			
			
			st.execute("DROP SEQUENCE seq_usertest");	//시퀀스 삭제 쿼리 수행
			st.execute(sql2);	//시퀀스 생성 쿼리 수행
			//-------------------
				
			
			//SQL 저장 변수
			String sql = null;
			
			//--- 테이블 생성 결과 확인 ----
			//	->user_tables 자료사전을 조회하여 생성되었는지 확인한다
			//카운트를 세면 결과값이 1(있음)인지 0(없음)인지 확인 가능
			sql = "";
			sql += "SELECT count(*) cnt FROM user_tables";
			sql += " WHERE table_name = upper('usertest')";
			
			rs = st.executeQuery(sql);	//SELECT 쿼리 수행 및 결과 저장
			
			rs.next();//조회된 첫 번째 행을 참조하도록 만들기
			
//			int tb_cnt = rs.getInt("cnt");	//컬럼명으로 조회결과 얻기 ( alias 적용됨 )
			int tb_cnt = rs.getInt(1);	//조회 결과의 컬럼 순서로 조회결과 얻기 ( 첫번째 컬럼값 )
			// 컬럼 순서 숫자의 경우 나중에 수정 시 뭐였지..? 헷갈릴 수 있으니 '컬럼명'조회 추천
			
			
			if( tb_cnt > 0 ) {
				System.out.println("테이블 생성 완료");
			}else {
				System.out.println("테이블 생성 실패");
			}
			//------------------------------
			
			
			
			//--- 시퀀스 생성 결과 확인 ----
//			->user_sequences 자료사전을 조회하여 생성되었는지 확인한다
			sql = "";
			sql += "SELECT count(*) cnt FROM user_sequences";
			sql += " WHERE sequence_name = upper('seq_usertest')"; //띄어쓰기 조심~
			
			rs = st.executeQuery(sql);
			
			rs.next();
			
			int seq_cnt = rs.getInt("cnt");
			
			if( seq_cnt > 0 ) {
				System.out.println("시퀀스 생성 완료");
			}else {
				System.out.println("시퀀스 생성 실패");
			}
			
			//------------------------------
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// --- 자원 해제 ---
				
				if( st!=null && !st.isClosed() ) st.close();
				if( conn!=null && !conn.isClosed() ) conn.close();
				//-------------------
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
	}

}
