package ojdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OjdbcEx_06 {

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

	}

}
