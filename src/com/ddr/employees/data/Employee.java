package com.ddr.employees.data;

public class Employee {
	
	private Long employeeId;
	private Long projectId;
	//Date will be Strings because date format is unknown
	private String dateFrom;
	private String dateTo;
	
	public Employee() {
		super();
	}

	public Employee(Long employeeId, Long projectId, String dateFrom, String dateTo) {
		super();
		this.employeeId = employeeId;
		this.projectId = projectId;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	
	@Override
	public int hashCode() {
		return employeeId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() != this.getClass())
			return false;
		return employeeId.equals(((Employee)obj).employeeId);
	}
}
