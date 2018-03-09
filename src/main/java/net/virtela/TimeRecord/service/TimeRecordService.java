package net.virtela.TimeRecord.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.virtela.TimeRecord.dao.TimeRecordDao;
import net.virtela.TimeRecord.model.EmployeeTimeRecord;

@Component
public class TimeRecordService {
	
	@Autowired
	private TimeRecordDao dao;
	
	private static final DateFormat STR_TO_DATE_FORMAT = new SimpleDateFormat("mm/dd/yyyy");

	public List<EmployeeTimeRecord> getEmplyeeTimeRecordListByDate(String strDate) {
		
		Date dateToSearch = null;
		
		if (StringUtils.isNotBlank(strDate)) {
			try {
				dateToSearch = STR_TO_DATE_FORMAT.parse(strDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			dateToSearch = new Date();
		}
		
		final List<EmployeeTimeRecord> recordList = this.dao.getEmplyeeTimeRecordListByDate(dateToSearch);
		
		if (recordList != null) {
			return recordList;
		}
		
		return Collections.emptyList();
	}

}
