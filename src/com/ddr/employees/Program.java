package com.ddr.employees;

import java.util.List;

import javax.management.InvalidAttributeValueException;

import com.ddr.employees.data.EmployeesCoWorked;
import com.ddr.employees.services.Service;

public class Program {

	public static void main(String[] args) throws InvalidAttributeValueException {
		
		//String filePath = "D:\\Users\\User\\Desktop\\test";
		String filePath = args[0];
		Service s = new Service();
		
	 	List<EmployeesCoWorked> res = s.findMostCoWorked(filePath);
	 	
	 	res.stream().forEach(a -> {
	 		System.out.println(a);
	 	});
	}
	
}
