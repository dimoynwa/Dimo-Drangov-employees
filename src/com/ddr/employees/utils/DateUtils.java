package com.ddr.employees.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DateUtils {

	public static final List<DateTimeFormatter> POSSIBLE_DATE_FORMATTERS = new LinkedList<>();
	public static DateTimeFormatter DATE_TIME_FORMATTER;
	
	public static final String CURRENT_DATE_TOKEN = "NULL";
	
	static {
		try(Scanner scan = new Scanner(DateUtils.class.getResourceAsStream("/com/ddr/employees/resources/dateformats"))) {
			while(scan.hasNextLine()) {
				POSSIBLE_DATE_FORMATTERS.add(DateTimeFormatter.ofPattern(scan.nextLine()));
			}
		} catch(Exception e) {
			throw new RuntimeException("Failed to read properties file", e);
		}
	}
	
	public static long daysBetweenDates(LocalDate d1, LocalDate d2) {
		return java.time.temporal.ChronoUnit.DAYS.between(d1, d2);
	}
	
	public static void validateDateString(String dateString) {
		if(CURRENT_DATE_TOKEN.equals(dateString)) {
			return;
		}
		for(Iterator<DateTimeFormatter> iterator = POSSIBLE_DATE_FORMATTERS.iterator(); iterator.hasNext(); ) {
			DateTimeFormatter formatter = iterator.next();
			try {
				formatter.parse(dateString);
			} catch(Exception e) {
				iterator.remove();
			}
		}
		if(POSSIBLE_DATE_FORMATTERS.isEmpty()) {
			throw new RuntimeException("Date format for " + dateString + " is not supported");
		}
	}
	
	public static boolean isBefore(LocalDate date, LocalDate dateToCompare) {
		return date.isBefore(dateToCompare);
	}
	
	public static LocalDate parseDate(String dateString) {
		return CURRENT_DATE_TOKEN.equals(dateString) ? LocalDate.now() : LocalDate.parse(dateString, DATE_TIME_FORMATTER);
	}
	
	public static LocalDate previousDate(LocalDate d1, LocalDate d2) {
		return d1.isBefore(d2) ? d1 : d2;
	}
	
	public static LocalDate nextDate(LocalDate d1, LocalDate d2) {
		return d1.isAfter(d2) ? d1 : d2;
	}
}
