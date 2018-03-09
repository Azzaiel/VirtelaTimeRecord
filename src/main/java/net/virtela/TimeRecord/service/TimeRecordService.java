package net.virtela.TimeRecord.service;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

import net.virtela.TimeRecord.model.EmployeeTimeRecord;

@Component
public class TimeRecordService {

	public List<EmployeeTimeRecord> getEmplyeeTimeRecordListByDate(String date) {
		System.out.println("Getting employee record");
		return null;
	}

}
