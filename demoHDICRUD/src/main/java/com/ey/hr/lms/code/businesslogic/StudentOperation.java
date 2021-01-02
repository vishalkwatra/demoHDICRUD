package com.ey.hr.lms.code.businesslogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.ey.hr.lms.code.entities.Student;

@Component
public class StudentOperation {

	public Connection conn;
	public Statement stmt;
	public ResultSet rs;
	
	@PostConstruct
	public void startConnection() throws SQLException {
		String url = "jdbc:sap://zeus.hana.prod.eu-central-1.whitney.dbaas.ondemand.com:23803?encrypt=true&validateCertificate=true&currentschema=21F405B7E6814B2DB3A7283339AF40ED";
		String username = "21F405B7E6814B2DB3A7283339AF40ED_05F07G6XHR6KLBB4QT6L3WHKH_RT";
		String password = "Vj60JfNbiyCKqgXSATawI8tCCav5JHocWD4iAerWomG6n9Zx0NMYQESqFc0ET62OlKASno9pkUlAWU1p_m_zS3pJZ7-08DZPl8iFfIVe9G.SwMA0MpUWVgp5vA4sR1Bn";
		conn = DriverManager.getConnection(url,username,password);
		stmt = conn.createStatement();
	}
	
	public List<Student> giveAllStudent() throws SQLException{
		List<Student> studentList = new ArrayList<Student>();
		rs = stmt.executeQuery("select top 100 * from student");
		while(rs.next()) {
			studentList.add(new Student(rs.getInt("ID"),rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),rs.getString("LOCATION_STUDENT")));
		}
		return studentList;
	}
	
	@PreDestroy
	public void endConnection() throws SQLException {
		rs.close();
		stmt.close();
		conn.close();
	}
}
