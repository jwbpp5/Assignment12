import java.sql.*;
import java.util.ArrayList;
import java.io.*;

public class DBAbs {
	private Connection con;
	private Statement stmt;
	
	public DBAbs() {
		connectToDB();
	}
	
	private void connectToDB() {
        String url = "jdbc:mysql://kc-sce-appdb01.kc.umkc.edu/jwbpp5";
        String userID = "jwbpp5";
        String password = "ABHtdq4kbT";
   
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
        } catch(java.lang.ClassNotFoundException e) {
            System.out.println(e);
            System.exit(0);
        }
        try {
			con = DriverManager.getConnection(url,userID,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int queryNumQs() {
		String sqlNumQs = "SELECT COUNT(*) FROM questions";
		int numQs = 0;
		try {
			ResultSet rs = stmt.executeQuery(sqlNumQs);
			rs.next();
			numQs = rs.getInt(1);
			System.out.println("Num questions: " + numQs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numQs;	
	}
	
	public int queryNumAns(int id) {
		String sqlGetNumAns = "SELECT COUNT(*) FROM answers where question_id_fk=?";
		int numAns = 0;
		try {
			ResultSet rs = stmt.executeQuery(sqlGetNumAns);
			rs.next();
			numAns = rs.getInt(1);
			System.out.println("Number of answers: " + numAns);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numAns;	
	}
	
	public boolean insertAns(int id, String answer) {
		String sqlInsert = "INSERT into Answers (question_id_fk,answer) VALUES(?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlInsert);
			pstmt.setInt(1, id);
			pstmt.setString(2, answer);
			pstmt.executeUpdate(); // execute prepared statement
			//pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean insertQ(String question) {
		String sqlInsert = "INSERT into Questions (question) VALUES(?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlInsert);
			pstmt.setString(1, question);
			pstmt.executeUpdate(); // execute prepared statement
			//pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public ArrayList<String> getAllQs() throws SQLException {
		ArrayList<String> qs = new ArrayList<String>();
		ResultSet rs = selectAllQs();
		while (rs.next()) {
			qs.add(rs.getString(1));
		}
		return qs;
	}
	
	private ResultSet selectAllQs() {
		System.out.println("select all qs entered");
		String sqlGetQs = "select question from questions";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sqlGetQs);
		} catch (SQLException e) {
			System.out.println("exception thrown");
			e.printStackTrace();
		}
		return rs;
	}
	
	public ArrayList<String> getAllAns(int i) throws SQLException {
		ArrayList<String> ans = new ArrayList<String>();
		ResultSet rs = selectAllAns(i);
		while (rs.next()) {
			ans.add(rs.getString(1));
		}
		return ans;
	}
	
	private ResultSet selectAllAns(int id) {
		String sqlGetAnswers = "select answer from Answers where question_id_fk=?";
		ResultSet result = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlGetAnswers);
			pstmt.setInt(1, id);
			System.out.println(pstmt);
			result = pstmt.executeQuery();
			//pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}