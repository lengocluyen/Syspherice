package net.syspherice.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.syspherice.form.ExcelMetaData;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.POIXMLProperties;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelsHandles {
	public static final String xls = "xls";
	public static final String xlsx = "xlsx";
	private int titleRow = 0;

	public int getTitleRow() {
		return titleRow;
	}

	public void setTitleRow(int titleRow) {
		this.titleRow = titleRow;
	}

	String filename;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<SheetDataRow> ReadSheet(SheetInfo sheetinfo) {
		List<SheetDataRow> sheetdata = new ArrayList<SheetDataRow>();
		int countBold = 0;
		int i = 0;
		int maxBold = 0;
		if (sheetinfo.typefile == xls) {
			HSSFSheet sheet = sheetinfo.getContentXSL();
			Iterator rows = sheet.rowIterator();

			while (rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();

				Iterator cells = row.cellIterator();
				SheetDataRow dataRow = new SheetDataRow();
				countBold = 0;
				while (cells.hasNext()) {
					HSSFCell cell = (HSSFCell) cells.next();
					// check title of sheet
					HSSFCellStyle style = cell.getCellStyle();
					HSSFFont font = sheet.getWorkbook().getFontAt(
							style.getFontIndex());
					if (font.getBoldweight() >= HSSFFont.BOLDWEIGHT_BOLD) {
						countBold++;
					}

					int cellType = cell.getCellType();
			
					if(cellType == cell.CELL_TYPE_STRING){
						cell = (HSSFCell) CellUtil.translateUnicodeValues(cell);

						String str = "";
						str = cell.toString();
						dataRow.addRow(new SheetDataCell(str));
					}else if(cellType==cell.CELL_TYPE_NUMERIC){
						String str2 = new Long(
								(long) cell.getNumericCellValue()).toString();
						dataRow.addRow(new SheetDataCell(str2.toString()));
					}else{	cell = (HSSFCell) CellUtil.translateUnicodeValues(cell);
							dataRow.addRow(new SheetDataCell(""));
					}
				
				}
				sheetdata.add(dataRow);
				if (maxBold < countBold) {
					maxBold = countBold;
					titleRow = i;
				}
				i++;
			}
		}
		if (sheetinfo.typefile == xlsx) {
			XSSFSheet sheet = sheetinfo.getContentXSLX();
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				XSSFRow row = (XSSFRow) rows.next();
				Iterator cells = row.cellIterator();
				SheetDataRow dataRow = new SheetDataRow();
				countBold = 0;
				while (cells.hasNext()) {
					XSSFCell cell = (XSSFCell) cells.next();
					// check title of sheet
					XSSFCellStyle style = cell.getCellStyle();
					XSSFFont font = sheet.getWorkbook().getFontAt(
							style.getFontIndex());
					if (font.getBoldweight() >= HSSFFont.BOLDWEIGHT_BOLD) {
						countBold++;
					}
					int cellType = cell.getCellType();
					if (cellType == cell.CELL_TYPE_STRING) {
						cell = (XSSFCell) CellUtil.translateUnicodeValues(cell);
						String str = cell.getRichStringCellValue().getString();
						dataRow.addRow(new SheetDataCell(str));
					} else if (cellType == cell.CELL_TYPE_NUMERIC) {
						String str2 = new Long(
								(long) cell.getNumericCellValue()).toString();
						dataRow.addRow(new SheetDataCell(str2.toString()));
					}
				}
				sheetdata.add(dataRow);
				if (maxBold < countBold) {
					maxBold = countBold;
					titleRow = i;
				}
				i++;
			}
		}
		return sheetdata;
	}
	//read metadata of excel file
	public ExcelMetaData ReadMetadata() {
		try {
			ExcelMetaData exmetadata = new ExcelMetaData();
			SummaryInformation summaryInfo;
			FileInputStream fileInputStream = new FileInputStream(filename);
			if (FilenameUtils.isExtension(filename, xls)) {
				HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
				summaryInfo = workbook.getSummaryInformation();
				exmetadata.setTitle(summaryInfo.getTitle());
				exmetadata.setSubjet(summaryInfo.getSubject());
				exmetadata.setComments(summaryInfo.getComments());
				exmetadata.setAuthor(summaryInfo.getAuthor());
				exmetadata.setKeywords(summaryInfo.getKeywords());
				exmetadata.setCreateDateTime(summaryInfo.getCreateDateTime().toString());
				exmetadata.setLastSaveDateTime(summaryInfo.getLastSaveDateTime().toString());
			}
			if (FilenameUtils.isExtension(filename, xlsx)) {
				XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
				POIXMLProperties xmlProps = workbook.getProperties();    
				POIXMLProperties.CoreProperties coreProps =  xmlProps.getCoreProperties();
				
				exmetadata.setTitle(coreProps.getTitle());
				exmetadata.setSubjet(coreProps.getSubject());
				exmetadata.setComments(coreProps.getDescription());
				exmetadata.setAuthor(coreProps.getCreator());
				exmetadata.setKeywords(coreProps.getKeywords());
				exmetadata.setCreateDateTime(coreProps.getCreated().toString());
				exmetadata.setLastSaveDateTime(coreProps.getModified().toString());
			
			}
			return exmetadata;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<SheetInfo> ReadFile() {
		List<SheetInfo> results = new ArrayList<SheetInfo>();

		try {
			FileInputStream fileInputStream = new FileInputStream(filename);
			if (FilenameUtils.isExtension(filename, xls)) {
				HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
				for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
					results.add(new SheetInfo(i, workbook.getSheetName(i), xls,
							workbook.getSheetAt(i), null, filename));
				}
			}
			if (FilenameUtils.isExtension(filename, xlsx)) {
				XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
				for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
					results.add(new SheetInfo(i, workbook.getSheetName(i),
							xlsx, null, workbook.getSheetAt(i), filename));
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}

}
