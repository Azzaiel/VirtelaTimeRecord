package net.virtela.TimeRecord.model;

import java.util.Date;

public class EmployeeTimeRecord {

	private Long employeeCode;
	private String emloyeeName;
	private Date date;
	private Date timeIn;
	private Date timeOut;

	public Long getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(Long employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmloyeeName() {
		return emloyeeName;
	}

	public void setEmloyeeName(String emloyeeName) {
		this.emloyeeName = emloyeeName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(Date timeIn) {
		this.timeIn = timeIn;
	}

	public Date getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Date timeOut) {
		this.timeOut = timeOut;
	}

}
