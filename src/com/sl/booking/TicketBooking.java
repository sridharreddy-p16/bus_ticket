package com.sl.booking;

import java.util.Scanner;

public class TicketBooking {

	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Bus Ticket Booking");
		System.out.println("select the option\n"
				+"1.Admin\n"
				+"2.customer\n"
				+"3.Exit");
		int choice = sc.nextInt();
		if(choice==1) {
			System.out.println("select the option\n"
					+"1.Login\n"
					+"2.Register\n"
					+"3.Exit");
			int choice1= sc.nextInt();
			switch(choice1) {
			case 1:{
				DbOperations.adminLogin();
				break;
			}
			case 2:{
				DbOperations.adminRegister();
				break;
			}
			case 3:{
				System.exit(0);
				
			}
			}
		}
		else if(choice==2) {
			System.out.println("select the option\n"
					+"1.Login\n"
					+"2.Register\n"
					+"3.Exit");
			int choice1= sc.nextInt();
			switch(choice1) {
			case 1:{
				DbOperations.login();
				break;
			}
			case 2:{
				DbOperations.register();
				break;
			}
			case 3:{
				System.exit(0);
				
			}
			}
		}
		else if(choice==3) {
			System.exit(0);
		}
		else {
			System.out.println("Not a valid Option");
		}

	}

}
