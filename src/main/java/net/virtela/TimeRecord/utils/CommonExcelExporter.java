package net.virtela.TimeRecord.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonExcelExporter {
	
	private String templatePath;
	private String sheetName;
	private Integer startRowIndex;
	private Integer cellTemplateRowIndex;
	private List<Object[]> dataList;
	
	private Workbook workbook;
	private List<CellStyle> templateCellStyleList;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private CommonExcelExporter() {
		super();
		/** Not To be initialized via constructor**/
	}
	
	public static class Builder {
		
		private String templatePath;
		private String sheetName;
		private Integer startRowIndex;
		private Integer cellTemplateRowIndex;
		private List<Object[]> dataList;
		
		/**
		 *  This field is to define the row format of the excel file.
		 *  the supplied index must be a row in the template that has
		 *  the formating for the row.
		 *  <p>
		 *  <b>Note:</b> Ensure that dummy data is entered in the template cell.
		 *  this ensures that the cell is created when poi reads it.
		 *  
		 * @param cellTemplateRowIndex must be an {@link Integer} greater than 0
		 * @return
		 */
		public Builder cellTemplateRowIndex(Integer cellTemplateRowIndex) {
			this.cellTemplateRowIndex = cellTemplateRowIndex;
			return this; 
		}
		
		public Builder dataList(List<Object[]> dataList) {
			this.dataList = dataList;
			return this; 
		}
		
		public Builder sheetName(String sheetName) {
			this.sheetName = sheetName;
			return this; 
		}
		
		/**
		 * This Class assumes that the path given for this property is an Excel file.
		 * The excel file must if an .xlsx
		 * 
		 * @param templatePath file path to the excel template
		 * 
		 */
		public Builder templatePath(String templatePath) {
			this.templatePath = templatePath;
			return this; 
		}
		
		public Builder startRowIndex(Integer index) {
			this.startRowIndex = index;
			return this; 
		}
		
		public CommonExcelExporter build() {
			//TODO Throw exception if any field is missing
			return new CommonExcelExporter(this);
		}
		
	}
	
	private CommonExcelExporter(Builder builder) {
		this.templatePath = builder.templatePath;
		this.sheetName = builder.sheetName;
		this.startRowIndex = builder.startRowIndex;
		this.cellTemplateRowIndex = builder.cellTemplateRowIndex;
		this.dataList = builder.dataList;
	}
	
	/**
	 *  Create the Excel File using the local properties of the class.
	 *  <p>
	 *  This exporter assumes the an .xlsx file is the given template.
	 */
	public void generateExcelFile() {
		try {
		
			this.workbook = new XSSFWorkbook(new FileInputStream(this.templatePath));	
			final Sheet sheet = this.workbook.getSheet(this.sheetName);
			
			if (this.cellTemplateRowIndex != null && this.cellTemplateRowIndex > 0) {
				logger.debug("Initializing Tempalte Cell Stype List");
				this.initializeTemnpalteCellStyle(sheet);
			}
			
			int rowIndex = this.startRowIndex - 1;
			for (Object[] objArr : this.dataList) {
				final Row row = this.getRowInstance(sheet, rowIndex);
				this.setRowValue(sheet, row, objArr);
				rowIndex++;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initializeTemnpalteCellStyle(Sheet sheet) {
		
		int fieldLeght = 0;
		
		if (this.dataList != null 
			&& this.dataList.size() > 0
			&& this.dataList.get(0) != null
			&& this.dataList.get(0).length > 0) {
			
			fieldLeght = this.dataList.get(0).length;
		}
		
		logger.debug("Cell Template Field size: " + fieldLeght);
		
		if (fieldLeght > 0) {
			this.templateCellStyleList = new ArrayList<>();
			final Row cellTemplateRow = this.getRowInstance(sheet, this.cellTemplateRowIndex - 1);
			for (int cellIndex = 0; cellIndex < fieldLeght; cellIndex++) {
				this.templateCellStyleList.add(this.getCellInstance(cellTemplateRow, cellIndex).getCellStyle());
			}
		}
		
	}

	private void setRowValue(Sheet sheet, Row row, Object[] objArr) {	
		if (objArr == null || objArr.length == 0) {
			return;
		}
		int cellIndex = 0;
		for (Object obj : objArr) {
			final Cell cell = getCellInstance(row, cellIndex);
			this.identfyAndSetCellValue(cell, obj);
			
			if (this.templateCellStyleList != null) {
				cell.setCellStyle(this.templateCellStyleList.get(cellIndex));
			}
			
			cellIndex++;
		}
	}
	
	private void identfyAndSetCellValue(final Cell cell, final Object value) {
		if (value == null) {
			// DO nothing for now
		} else if (value.getClass() == Integer.class) {
			cell.setCellValue(Integer.parseInt(value.toString()));
		} else if (value.getClass() == Double.class) {
			cell.setCellValue(Double.parseDouble(value.toString()));
		} else if (value.getClass() == Long.class) {
			cell.setCellValue(Long.parseLong(value.toString()));
		} else if (value.getClass() == Date.class) {
			cell.setCellValue((Date) value);
		} else {
			cell.setCellValue(value.toString());
		}
	}
	
	private Cell getCellInstance(final Row row, final int cellIndex) {
		if (row.getCell(cellIndex) != null) {
			return row.getCell(cellIndex);
		} else {
			return row.createCell(cellIndex);
		}
	}

	private Row getRowInstance(Sheet sheet, final int rowIndex) {
		Row row = sheet.getRow(rowIndex);
		if (row != null) {
			return row;
		} else {
			return sheet.createRow(rowIndex);
		}
	}

	public void save(String savePath) {
		if (this.workbook == null) {
			return;
		}
		try (final FileOutputStream fileOut = new FileOutputStream(savePath)) {
			this.workbook.write(fileOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
