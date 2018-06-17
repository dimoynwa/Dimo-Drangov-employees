package com.ddr.employees.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.management.InvalidAttributeValueException;

import com.ddr.employees.data.Employee;
import com.ddr.employees.data.EmployeesCoWorked;
import com.ddr.employees.utils.DateUtils;

public class Service {

	private static final String LINE_DELIM = ", ";

	public Service() {
		super();
	}

	public List<EmployeesCoWorked> findMostCoWorked(String fileName) throws InvalidAttributeValueException {
		Map<Long, List<Employee>> employeesInProj = readFile(fileName);
		chooseDateFormat();
		
		List<EmployeesCoWorked> res = findEmployeesCoWorked(employeesInProj);
		
		return res;
	}

	private Map<Long, List<Employee>> readFile(String fileName) throws InvalidAttributeValueException {

		Map<Long, List<Employee>> result = new HashMap<>();

		try (Scanner scan = new Scanner(new File(fileName))) {
			String line = null;
			while (scan.hasNext()) {
				line = scan.nextLine();
				String[] lineDel = line.split(LINE_DELIM);
				Employee empl = new Employee(Long.valueOf(lineDel[0]), Long.valueOf(lineDel[1]), lineDel[2],
						lineDel[3]);
				DateUtils.validateDateString(empl.getDateFrom());
				DateUtils.validateDateString(empl.getDateTo());
				if (result.containsKey(empl.getProjectId())) {
					result.get(empl.getProjectId()).add(empl);
				} else {
					result.put(empl.getProjectId(), new LinkedList<>(Arrays.asList(empl)));
				}
			}
		} catch (NumberFormatException e) {
			throw new InvalidAttributeValueException("Invalid file format");
		} catch (FileNotFoundException e1) {
			throw new RuntimeException("Invalid File : " + fileName);
		}

		return result;
	}

	private List<EmployeesCoWorked> findEmployeesCoWorked(Map<Long, List<Employee>> employeesByProject) {
		List<EmployeesCoWorked> result = new LinkedList<>();
		
		long daysMost = -1L;
		
		for(Map.Entry<Long, List<Employee>> entry : employeesByProject.entrySet()) {
			List<Employee> list = employeesByProject.get(entry.getKey());
			Employee[] array = list.toArray(new Employee[list.size()]);
			
			for(int i = 0; i < array.length - 1; ++i) {
				for(int j = i+1; j < array.length; ++j) {
					Employee e1 = array[i];
					Employee e2 = array[j];
					
					EmployeesCoWorked coWorked = new EmployeesCoWorked(e1.getEmployeeId(), e2.getEmployeeId(), entry.getKey(), calculateDaysBetweenEmployees(e1, e2));
					
					if(coWorked.getDaysWorked() > daysMost) {
						daysMost = coWorked.getDaysWorked();
						result.clear();
						result.add(coWorked);
					} else if(coWorked.getDaysWorked() == daysMost) {
						result.add(coWorked);
					}
				}
			}

		}
		
		return result;
	}

	private long calculateDaysBetweenEmployees(Employee empl1, Employee empl2) {
		LocalDate empl1From = DateUtils.parseDate(empl1.getDateFrom());
		LocalDate empl1To = DateUtils.parseDate(empl1.getDateTo());

		LocalDate empl2From = DateUtils.parseDate(empl2.getDateFrom());
		LocalDate empl2To = DateUtils.parseDate(empl2.getDateTo());

		if (empl1From.isAfter(empl2To) || empl2From.isAfter(empl1To))
			return 0;

		return Duration.between(DateUtils.nextDate(empl1From, empl2From).atStartOfDay(), DateUtils.previousDate(empl1To, empl2To).plusDays(1).atStartOfDay())
				.toDays();

	}
	
	private void chooseDateFormat() {
		if(DateUtils.POSSIBLE_DATE_FORMATTERS.size() == 1) {
			DateUtils.DATE_TIME_FORMATTER = DateUtils.POSSIBLE_DATE_FORMATTERS.get(0);
			return;
		}
		
		System.out.println("There is more than 1 possible date formats for this file.");
		int index = 0;
		
		DateUtils.POSSIBLE_DATE_FORMATTERS.forEach(d -> {
			System.out.println((index + 1) + ") " + d.getDecimalStyle().toString());
		});
		
		System.out.println("Please select the right one by entering its number :");
		
		try(Scanner scan = new Scanner(System.in)) {
			int selected = scan.nextInt();
			DateUtils.DATE_TIME_FORMATTER = DateUtils.POSSIBLE_DATE_FORMATTERS.get(selected - 1);
		}
		
	}
}

