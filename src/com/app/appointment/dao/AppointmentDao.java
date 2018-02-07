package com.app.appointment.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.appointment.model.AppointmentDTO;

@Repository
public class AppointmentDao {
	
	@Autowired
	ServletContext context; 
	
	public  Connection getSQLLiteConnection() throws ClassNotFoundException, SQLException{
		String realPath = context.getRealPath("/");
		Class.forName("org.sqlite.JDBC");
		String dbURL = "jdbc:sqlite:"+realPath+"appointment.db";
		Connection conn = DriverManager.getConnection(dbURL);
		return conn;
		
	}

	public void saveAppointmentDtl(AppointmentDTO appointmentDTO)
			throws ClassNotFoundException, SQLException {
		 Connection conn = getSQLLiteConnection();		
		if (conn != null) {
			String sql = "INSERT INTO appointment VALUES(?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, appointmentDTO.getDate()+"#"+appointmentDTO.getTime());
			pstmt.setString(2, appointmentDTO.getDesc());
			pstmt.executeUpdate();
			conn.close();

		}

	}
	
	public List<AppointmentDTO> getAppointmentDtl(String searchParam) {
		List<AppointmentDTO> appointmentDTOs = new ArrayList<AppointmentDTO>();
		AppointmentDTO appointmentDTO = null;
		   Statement stmt = null;
		   try {
			   Connection conn = getSQLLiteConnection();		  
			   conn.setAutoCommit(false);
		       stmt = conn.createStatement();
		     // ResultSet rs = stmt.executeQuery( "SELECT * FROM appointment;" );	
		      	String sql = "SELECT * FROM appointment WHERE datetime LIKE ? or desc LIKE ?;";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchParam+"%");
				pstmt.setString(2, searchParam+"%");
				ResultSet rs = pstmt.executeQuery();
			
				//pstmt.setString(2, appointmentDTO.getDesc());
		      while ( rs.next() ) {
		    	  appointmentDTO = new AppointmentDTO();		    	  
		    	  appointmentDTO.setDate(rs.getString("datetime"));
		    	  appointmentDTO.setDesc(rs.getString("desc"));
		    	  appointmentDTOs.add(appointmentDTO);
		    	  System.out.println(appointmentDTO.getDate() +" ::: "+appointmentDTO.getDesc());
   
		      }
		      rs.close();
		      stmt.close();
		      conn.close();
		   } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		   }
	
		return appointmentDTOs;
	}

	
	public void createTable(){
		
		
	      Statement stmt = null;
	      
	      try {
	    	  Connection conn = getSQLLiteConnection();	

	         stmt = conn.createStatement();
	         String sql = "CREATE TABLE appointment " +
	                        " (datetime TEXT, " + 
	                        " desc TEXT)";
	         //String sql = "DROP TABLE appointment";
	         stmt.executeUpdate(sql);
	         stmt.close();
	         conn.close();
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Table created successfully");
	   }

	public void createSchema() {
		createTable();
		
	}
	
	public void setServletContext(ServletContext servletContext) {
	    this.context = servletContext;
	}
	

}
