package com.sl.booking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import javax.naming.spi.DirStateFactory.Result;

public class DbOperations {
	
	static Scanner sc = new Scanner(System.in);
	public static Connection dbConnection() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		//System.out.println("Driver loaded");
		Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop","root","root");
		return con;
	}
	
	public static void loginMenu(String uName) {
		System.out.println("Select an Option\n"
				+"1.show buses\n"
				+"2.Book Ticket\n"
				+"3.Booking History\n"
				+"4.Logout");
		int choice = sc.nextInt();
		switch(choice) {
		case 1:
			DbOperations.showBuses(uName);
			break;
		case 2:
			DbOperations.bookTickect(uName);
			break;
		case 3:
			DbOperations.bookingHistoey(uName);
			break;
		case 4:
			TicketBooking.main(null);
		}
	}
	
	public static void login() {
		System.out.println("Enter the username:");
		String uName = sc.next();
		System.out.println("Enter the password:");
		String pwd = sc.next();
		String sql = "select * from bus_customers where userName=? and password=?";
		try {
		Connection con= dbConnection();
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, uName);
		psmt.setString(2, pwd);
		ResultSet rs = psmt.executeQuery();
		if(rs.next()) {
			System.out.println("Login successful!");
			loginMenu(uName);
		}
		else {
			System.out.println("Login unsuccessful! please check your credentials");
			login();
		}
				}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void register() {
		System.out.println("Enter the name:");
		String cname = sc.next();
		System.out.println("Enter the age:");
		int age = sc.nextInt();

		System.out.println("Enter the gender:");
		String gender = sc.next();

		System.out.println("Enter the username:");
		String uname = sc.next();

		System.out.println("Enter the password:");
		String password = sc.next();
		
		String sql = "insert into bus_customers values(?,?,?,?,?)";
		try {
			Connection con= dbConnection();
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, cname);
			psmt.setInt(2, age);
			psmt.setString(3, gender);
			psmt.setString(4, uname);
			psmt.setString(5, password);
			int x = psmt.executeUpdate();
			if(x>0) {
				System.out.println("Registration successful!\n"
						+"Please Login!");
			    TicketBooking.main(null);
			}
			else {
				System.out.println("Registration unsuccessful! Please try Again");
				DbOperations.register();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		

	}
	
	public static void showBus(String uName) {
		System.out.println("Enter the starting point:");
		String start = sc.next();
		System.out.println("Enter the Destination:");
		String dest = sc.next();
		System.out.println("Enter the journy date(yyyy-mm-dd):");
		String journyDate = sc.next();
		String sql = "select * from bus_seats where station_from=? and to_station=? and journy_date=?";
		try {
			Connection con= dbConnection();
			PreparedStatement psmt= con.prepareStatement(sql);
			psmt.setString(1, start);
			psmt.setString(2, dest);
			psmt.setString(3, journyDate);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()) {
				System.out.println(rs.getInt(1)+"\t"+rs.getInt(2));
			
			}
			else {
				System.out.println("Sorry No buses Available!");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void showBuses(String uName) {
		String sql = "select * from bus_seats";
		try {
			Connection con= dbConnection();
			PreparedStatement psmt= con.prepareStatement(sql);
			
			ResultSet rs = psmt.executeQuery();
			if(rs.next()) {
				
				System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getString(3)
				+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6));
				
				
			}
			else {
				System.out.println("Sorry No buses Available!");
				
			}
			loginMenu(uName);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateSeats(int busNo, int seatNo) {
		
		String sql = "update table bus_seats set seats=(seats-?) where bus_no=?";
			
			try {
				Connection con= dbConnection();
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setInt(1, seatNo);
			psmt.setInt(2, busNo);
			int x = psmt.executeUpdate();
			
			}catch(Exception e) {
				e.printStackTrace();
			}
		
	}
	
	public static void bookTickect(String uName) {
		System.out.println(uName+" Welcome to ticket Booking");
		String start = "";
		String ending = "";
		System.out.println("Select the starting point:\n"
				+ "1.Banglore\n"
				+ "2.Delhi\n"
				+ "3.Hyderabad\n"
				+ "4.Mumbai");
		int dep = sc.nextInt();
		switch(dep) {
		case 1:
			start = "Banglore";
			break;
		case 2:
			start = "Delhi";
			break;
		case 3:
			start = "Hyderabad";
			break;
		case 4:
			start = "Mumbai";
			break;
		}
		System.out.println("Select the Destination point:\n"
				+ "1.Banglore\n"
				+ "2.Delhi\n"
				+ "3.Hyderabad\n"
				+ "4.Mumbai");
		int arr = sc.nextInt();
		switch(arr) {
		case 1:
			ending = "Banglore";
			break;
		case 2:
			ending = "Delhi";
			break;
		case 3:
			ending = "Hyderabad";
			break;
		case 4:
			ending = "Mumbai";
			break;
		}
		if(start.equals(ending)) {
			bookTickect(uName);
		}
		else {
	
		System.out.println("Enter the journy date(yyyy-mm-dd):");
		String journyDate = sc.next();
		String sql = "select * from bus_seats where station_from=? and to_station=? and journy_date=?";
		try {
			Connection con= dbConnection();
			PreparedStatement psmt= con.prepareStatement(sql);
			psmt.setString(1, start);
			psmt.setString(2, ending);
			psmt.setString(3, journyDate);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()) {
				System.out.println(rs.getInt(1)+"\t"+rs.getInt(2));
				System.out.println("Enter the number of seats:");
				int seatNo = sc.nextInt();
				if(seatNo<=rs.getInt(2)) {
					System.out.println("Tickets booked!\n"
							+uName +"Thank you! Visit Again.");
					saveToHistory(uName, start, ending, journyDate, seatNo, rs.getInt(1));
					updateSeats(rs.getInt(1), seatNo);
					loginMenu(uName);
				}
				else{
					System.out.println("Seats not available:\n"
							+ "Number of Available seats are "+rs.getInt(2));
					bookingHistoey(uName);
				}
			
			}
			else {
				System.out.println("Sorry No buses Available!");
				loginMenu(uName);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		}
		
	}
	public static void saveToHistory(String uName, String start, String dest, String journyDate,int busNo, int seats) {
		String sql = "insert into bus_bookings values(?,?,?,?,?,?)";
		try {
			Connection con= dbConnection();
			PreparedStatement psmt= con.prepareStatement(sql);
			psmt.setString(1, uName);
			psmt.setString(2, start);
			psmt.setString(3, dest);
			psmt.setInt(4, busNo);
			psmt.setInt(5, seats);
			psmt.setString(6, journyDate);
			psmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void bookingHistoey(String uName) {
		String sql = "select * from bus_bookings where username=?";
		try {
			Connection con= dbConnection();
			PreparedStatement psmt= con.prepareStatement(sql);
			psmt.setString(1, uName);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()) {
				System.out.println(uName+" Your past Journy");
				System.out.println("Username\tfromjourny\ttojourny\tseats\tbusNo\tdate");
				System.out.println(rs.getString(1)+"\t\t"+rs.getString(2)+"\t"
						+rs.getString(3)+"\t"+rs.getInt(4)+"\t"
						+rs.getInt(5)+"\t"+rs.getString(6));
			}
			else {
				System.out.println("No past journy available!");
			}
			loginMenu(uName);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void adminMenu(String uName) {
		System.out.println("Select an Option\n"
				+"1.show buses\n"
				+"2.Add buses\n"
				+"3.Logout");
		int choice = sc.nextInt();
		switch(choice) {
		case 1:
			DbOperations.showBuses(uName);
			break;
		case 2:
			DbOperations.addBus(uName);
			break;
		
		case 3:
			TicketBooking.main(null);
		}
	}
	
	public static void addBus(String uName) {
		String sql = "insert into bus_seats values(?,?,?,?,?,?)";
		System.out.println("Enter the bus Number:");
		int busNo = sc.nextInt();
		System.out.println("Enter the nmber of seats:");
		int seats = sc.nextInt();
		System.out.println("Enter the travels Name:");
		String travels = sc.next();
		System.out.println("Enter the Starting point:");
		String dep = sc.next();
		System.out.println("Enter the Destination:");
		String dest = sc.next();
		System.out.println("Enter the Journy Date(yyyy-mm-dd):");
		String jDate = sc.next();
		try {
			Connection con= dbConnection();
			PreparedStatement psmt= con.prepareStatement(sql);
			psmt.setInt(1, busNo);
			psmt.setInt(2, seats);
			psmt.setString(3, travels);
			psmt.setString(4, dep);
			psmt.setString(5, dest);
			psmt.setString(6, jDate);
			
			int x = psmt.executeUpdate();
			
			if(x>1) {
				System.out.println("Bus details added successfully!");
				adminMenu(uName);
			}
			else {
				System.out.println("Bus deatils not added! Please try again!");
				addBus(uName);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void adminLogin() {
		System.out.println("Enter the username:");
		String uName = sc.next();
		System.out.println("Enter the password:");
		String pwd = sc.next();
		String sql = "select * from bus_admin where userName=? and password=?";
		try {
		Connection con= dbConnection();
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, uName);
		psmt.setString(2, pwd);
		ResultSet rs = psmt.executeQuery();
		if(rs.next()) {
			System.out.println("Login successful!");
			adminMenu(uName);
		}
		else {
			System.out.println("Login unsuccessful! please check your credentials");
			adminLogin();
		}
				}catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void adminRegister() {
		System.out.println("Enter the name:");
		String cname = sc.next();
		System.out.println("Enter the age:");
		int age = sc.nextInt();

		System.out.println("Enter the gender:");
		String gender = sc.next();

		System.out.println("Enter the username:");
		String uname = sc.next();

		System.out.println("Enter the password:");
		String password = sc.next();
		
		String sql = "insert into bus_admin values(?,?,?,?,?)";
		try {
			Connection con= dbConnection();
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, cname);
			psmt.setInt(2, age);
			psmt.setString(3, gender);
			psmt.setString(4, uname);
			psmt.setString(5, password);
			int x = psmt.executeUpdate();
			if(x>0) {
				System.out.println("Registration successful!\n"
						+"Please Login!");
			    TicketBooking.main(null);
			}
			else {
				System.out.println("Registration unsuccessful! Please try Again");
				adminRegister();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
