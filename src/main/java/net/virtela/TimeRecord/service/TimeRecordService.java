package net.virtela.TimeRecord.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.virtela.TimeRecord.dao.TimeRecordDao;
import net.virtela.TimeRecord.model.EmployeeTimeRecord;

@Component
public class TimeRecordService {

	@Autowired
	private TimeRecordDao dao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final DateFormat STR_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	/**
	 * 
	 * @param strDate String date in format MM/DD/YYYY. Will be set to today if null
	 * @return List of Employee time record for the given date
	 */
	public List<EmployeeTimeRecord> getEmplyeeTimeRecordListByDate(String strDate) {

		Date dateToSearch = null;

		if (StringUtils.isNotBlank(strDate)) {
			try {
				dateToSearch = STR_DATE_FORMAT.parse(strDate);
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error("Failled to convert (" + strDate + ") to a date");
			}
		} else {
			dateToSearch = new Date();
			strDate = STR_DATE_FORMAT.format(dateToSearch);
			logger.debug("Request Date is set to " + strDate);
		}

		final List<EmployeeTimeRecord> recordList = this.dao.getEmplyeeTimeRecordListByDate(dateToSearch);

		if (recordList != null) {
			return recordList;
		}

		return Collections.emptyList();
	}

}
