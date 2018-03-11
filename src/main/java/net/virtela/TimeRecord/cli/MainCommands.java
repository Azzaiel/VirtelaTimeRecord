package net.virtela.TimeRecord.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import net.virtela.TimeRecord.model.EmployeeTimeRecord;
import net.virtela.TimeRecord.service.TimeRecordService;
import net.virtela.TimeRecord.utils.CommonExcelExporter;
import net.virtela.TimeRecord.utils.Constants;
import net.virtela.TimeRecord.utils.StopWatch;

@ShellComponent("Time Record commands for report generation and maitenace")
public class MainCommands {
	
	private static final String REGEX_DATE_FORMAT = "^(0[0-9]||1[0-2])/([0-2][0-9]||3[0-1])/([0-9][0-9])?[0-9][0-9]$";
	
	private static final String DEFAULT_DATE = "01/01/1900";
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${report.daily.time.record.template}")
	private String timeRecordTemplate;
	@Value("${report.daily.time.record.excel.sheet.name}")
	private String timeRecordExcelSheetName;
	@Value("${report.daily.time.record.excel.start.row.index}")
	private int timeRecordStartRowIndex;
	@Value("${report.daily.time.record.excel.cell.tempalte.row}")
	private int timeRecordCellTemplateIndex;
	@Value("${report.daily.time.record.file.name.suffix}")
	private String timeRecordFileNameSuffix;
	@Value("${report.daily.time.record.save.dir}")
	private String timeRecordSaveDir;
	
	@Autowired
	private TimeRecordService service;
	
//	@Scheduled(fixedRate = 5000)
	private void generateDailyTitmeRecord() {
		logger.info("Scheduled Today's Time Record generator triggered!");
		try {
			this.generate(DEFAULT_DATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@ShellMethod("Generate Time Record per Day into an excel file and save it to a directory.")
    public void generate(
    							@ShellOption (value= {"-dt", "-date"},
    							              help = "Date to generate the report.If empty report will be generated for today",
    							              defaultValue = DEFAULT_DATE) 
    							@Pattern (regexp = REGEX_DATE_FORMAT, 
    									  message ="Format required is mm/dd/yyyy")
    							String date			
    						 ) throws IOException {
		
		if (StringUtils.isEmpty(date) || Objects.equals(DEFAULT_DATE, date)) {
			logger.info("Generating report for today");
			date = TimeRecordService.STR_DATE_FORMAT.format(new Date());
		} else {
			logger.info("Generating report for " + date);
		}
		
		final StopWatch stopWatch = new StopWatch();
		stopWatch.startTimer();
		
		logger.info("Geting the list of Employee Record...");
		final List<EmployeeTimeRecord> empTimeRecordList = this.service.getEmplyeeTimeRecordListByDate(date);
		logger.info("Found " + empTimeRecordList.size() + " Employee time records.Duration: " + stopWatch.getLapElapsedTime());
		
		logger.info("Generating Excel Time Record File...");
		final List<Object[]> dataArrList = empTimeRecordList.stream().map(rec -> rec.toObjectArr())
																	 .collect(Collectors.toList());
																	
		final CommonExcelExporter excelExporter = new CommonExcelExporter.Builder()
															             .templatePath(timeRecordTemplate)
															             .sheetName(timeRecordExcelSheetName)
															             .startRowIndex(timeRecordStartRowIndex)
															             .cellTemplateRowIndex(timeRecordCellTemplateIndex)
															             .dataList(dataArrList)
															             .build();
		excelExporter.generateExcelFile();
		logger.info("Completed the Excel Time Record File.Duration: " + stopWatch.getLapElapsedTime());
		
		logger.info("Saving the Excel Time Record...");
		final StringBuilder reportFilePath = new StringBuilder();
		reportFilePath.append(timeRecordSaveDir).append(Constants.PATH_SEPARATOR);
		reportFilePath.append(timeRecordFileNameSuffix);
		reportFilePath.append(StringUtils.replace(date, Constants.SLASH, Constants.EMPTY_STRING));
		reportFilePath.append(Constants.DOT).append(Constants.FILE_EXTENSION_XLSX);
		Files.deleteIfExists(Paths.get(reportFilePath.toString()));
		excelExporter.save(reportFilePath.toString());
		logger.info("Done Saving saving the Report. Duration: " + stopWatch.getLapElapsedTime());
		logger.info("File is saved in: " + reportFilePath.toString());
		
		logger.info("Done Generating Time Record Report!! Total time spent: " + stopWatch.getElapsedTime());
    }
	
	@ShellMethod("Archive time record reports except for the current day report")
	public void archive() {
		
	}

}
