package net.virtela.TimeRecord.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import net.virtela.TimeRecord.model.EmployeeTimeRecord;

@Component
public class TimeRecordDao {

	public List<EmployeeTimeRecord> getEmplyeeTimeRecordListByDate(Date dateToSearch) {
		
		final List<EmployeeTimeRecord> recordList = new ArrayList<>();
		
		final Random random = new Random();
		int Low = 250;
		int High = 1500;
		
		for (long index = 101; index < random.nextInt(High-Low) + Low; index++) {
			recordList.add(new EmployeeTimeRecord( index,
					                               "Employee " + index,
					                               dateToSearch,
					                               new Date(),
					                               new Date()));
		}
		
		Low = 1000;
		High = 3000;
		
		try {
			Thread.sleep(random.nextInt(High-Low) + Low);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return recordList;
	}

}
