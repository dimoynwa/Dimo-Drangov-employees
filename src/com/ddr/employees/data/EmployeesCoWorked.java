package com.ddr.employees.data;

public class EmployeesCoWorked {
	
	private Long employee1Id;
	private Long employee2Id;
	private Long projectId;
	private long daysWorked;
	
	public EmployeesCoWorked() {
		super();
	}

	public EmployeesCoWorked(Long employee1Id, Long employee2Id, Long projectId, long daysWorked) {
		super();
		this.employee1Id = Math.min(employee1Id, employee2Id);
		this.employee2Id = Math.max(employee1Id, employee2Id);
		this.projectId = projectId;
		this.daysWorked = daysWorked;
	}

	public Long getEmployee1Id() {
		return employee1Id;
	}

	public void setEmployee1Id(Long employee1Id) {
		this.employee1Id = employee1Id;
	}

	public Long getEmployee2Id() {
		return employee2Id;
	}

	public void setEmployee2Id(Long employee2Id) {
		this.employee2Id = employee2Id;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public long getDaysWorked() {
		return daysWorked;
	}

	public void setDaysWorked(long daysWorked) {
		this.daysWorked = daysWorked;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Emloyee1 : ").append(employee1Id).append(", Employee2 : ").append(employee2Id)
			.append(", ProjectId : ").append(projectId).append(", Days worked : ").append(daysWorked);
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		String id = employee1Id + " " + employee2Id;
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() != this.getClass()) {
			return false;
		}
		EmployeesCoWorked o = (EmployeesCoWorked) obj;
		return employee1Id.equals(o.employee1Id) && employee2Id.equals(o.employee2Id);
	}
}
