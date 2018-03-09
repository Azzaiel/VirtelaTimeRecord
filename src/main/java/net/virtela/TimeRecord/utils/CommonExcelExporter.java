package net.virtela.TimeRecord.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CommonExcelExporter {
	
	private String templatePath;
	private String sheetName;
	private Integer startRowIndex;
	private List<Object[]> dataList;
	
	private CommonExcelExporter() {
		super();
		/** Not To be initialized via constructor**/
	}
	
	public static class Builder {
		
		private String templatePath;
		private String sheetName;
		private Integer startRowIndex;
		private List<Object[]> dataList;
		
		public Builder dataList(List<Object[]> dataList) {
			this.dataList = dataList;
			return this; 
		}
		
		public Builder sheetName(String sheetName) {
			this.sheetName = sheetName;
			return this; 
		}
		
		public Builder templatePath(String templatePath) {
			this.templatePath = templatePath;
			return this; 
		}
		
		public Builder startRowIndex(int index) {
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
		this.dataList = builder.dataList;
	}
	
	private Workbook workbook;
	
	public void generateExcelFile() {
		try {
		
			this.workbook = new XSSFWorkbook(new FileInputStream(this.templatePath));
			final Sheet sheet = this.workbook.getSheet(this.sheetName);
			
			int rowIndex = this.startRowIndex;
			
			for (Object[] objArr : this.dataList) {
				final Row row = this.getRowInstance(sheet, rowIndex);
				this.setRowValue(sheet, row, objArr);
				rowIndex++;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setRowValue(Sheet sheet, Row row, Object[] objArr) {	
		if (objArr == null || objArr.length == 0) {
			return;
		}
		int cellIndex = 1;
		for (Object obj : objArr) {
			final Cell cell = getCellInstance(row, cellIndex);
			this.identfyAndSetCellValue(cell, obj);
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

}
