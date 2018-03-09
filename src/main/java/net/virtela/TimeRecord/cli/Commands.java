package net.virtela.TimeRecord.cli;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import net.virtela.TimeRecord.model.EmployeeTimeRecord;
import net.virtela.TimeRecord.service.TimeRecordService;


@ShellComponent
public class Commands {
	
	private static final String REGEX_DATE_FORMAT = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9][0-9][0-9]$";
	
	private static final String DEFAULT_DATE = "01/01/1900";
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TimeRecordService service;
	
//	@Scheduled(fixedRate = 5000)
	private void generateDailyTitmeRecord() {
		logger.info("Scheduled Today's Time Record generator triggered!");
		this.genTimeRecord(null);
	}
	
	@ShellMethod("Generate Time Record per Day.")
    public void genTimeRecord(
    							@ShellOption (help = "Date to generate the report.If empty report will be generated for today",
    							              defaultValue = DEFAULT_DATE) 
    							@Pattern (regexp = REGEX_DATE_FORMAT, 
    									  message ="Format required is mm/dd/yyyy")
    							String date			
    						 ) {
		
		if (StringUtils.isEmpty(date) || Objects.equals(DEFAULT_DATE, date)) {
			logger.info("Generating report for today");
		} else {
			logger.info("Generating report for " + date);
		}
		
		List<EmployeeTimeRecord> empTimeRecordList = this.service.getEmplyeeTimeRecordListByDate(date);
		
    }

}
