package com.ddr.employees;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.management.InvalidAttributeValueException;

import com.ddr.employees.data.Employee;
import com.ddr.employees.data.EmployeesCoWorked;
import com.ddr.employees.services.Service;

public class Program {

	public static void main(String[] args) throws InvalidAttributeValueException {
		
		//String filePath = "D:\\Users\\User\\Desktop\\test";
		try(Scanner scan = new Scanner(System.in)) {
			System.out.println("Enter file path:");
			
			String filePath = scan.nextLine();
			Service s = new Service();
			
		 	List<EmployeesCoWorked> res = s.findMostCoWorked(filePath);
		 	
		 	res.stream().forEach(a -> {
		 		System.out.println(a);
		 	});
		}
	}
	
}
