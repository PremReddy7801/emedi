//package com.app.emedi.bean;
//
//import java.time.OffsetDateTime;
//import java.util.Scanner;
//
//public class Password {
//	static String companypassword = "7801";
//	static int i = 0;
//
//	static void verification(String pass) {
//		i += 1;
//
//		if (companypassword.equals(pass)) {
//
//			i = 0;
//			System.out.println("count money before going out ");
//		}
//		if (i < 3 && i != 0) {
//			System.out.println(i + "time");
//			throw new NullPointerException("password wrong");
//		}
//
//		if (i == 3) {
//
//			throw new ArithmeticException("please try again after 24 houurs");
//		}
//
//	}
//
//	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		boolean b = true;
//		
//		
//
//		while (b) {
//			try {
//				System.out.println();
//				System.out.print("enter your password");
//				String password = sc.next();
//
//				verification(password);
//				b = false;
//
//			} catch (NullPointerException e) {
//				System.out.println(e.getMessage());
//			} catch (ArithmeticException e) {
//				System.out.println(e.getMessage());
//				b = false;
//			}
//
//		}
//
//	}
//}
