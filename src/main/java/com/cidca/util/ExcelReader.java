package com.cidca.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFRow row;
	private XSSFWorkbook xb;
	private XSSFRow xssRow;
	public static XSSFSheet xssSheet;
	public XSSFSheet getXssSheet() {return xssSheet;}
	
	@SuppressWarnings("deprecation")
	public String[] readExcelTitle(InputStream is) {
		try {
			this.fs = new POIFSFileSystem(is);
			this.wb = new HSSFWorkbook(this.fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.sheet = this.wb.getSheetAt(0);
		this.row = this.sheet.getRow(0);

		int colNum = this.row.getPhysicalNumberOfCells();
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			title[i] = getCellFormatValue(this.row.getCell((short) i));
		}
		return title;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public Map<Integer, Map<Integer, String>> readExcelContent(InputStream is) {
		Map content = new HashMap();
		Map str = new HashMap();
		try {
			this.fs = new POIFSFileSystem(is);
			this.wb = new HSSFWorkbook(this.fs);
			this.sheet = this.wb.getSheetAt(0);
			int rowNum = this.sheet.getLastRowNum();
			this.row = this.sheet.getRow(1);
			int colNum = this.row.getPhysicalNumberOfCells();
			
			for (int i = 1; i <= rowNum; i++) {
				this.row = this.sheet.getRow(i);
				int j = 0;
				while (j < colNum) {
					str.put(Integer.valueOf(j), getCellFormatValue(this.row.getCell((short) j)).trim());
					j++;
				}
				content.put(Integer.valueOf(i), str);
				str = new HashMap();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

	private String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		if(cell != null) {
			switch (cell.getCellType()) {
			case 1:
				strCell = cell.getStringCellValue();
				break;
			case 0:
				strCell = String.valueOf(cell.getNumericCellValue());
				break;
			case 4:
				strCell = String.valueOf(cell.getBooleanCellValue());
				break;
			case 3:
				strCell = "";
				break;
			case 2:
			default:
				strCell = "";
			}
		}

		if ("".equals(strCell)) {
			return "";
		}
		if (cell == null) {
			return "";
		}
		return strCell;
	}
	
	@SuppressWarnings({ "unused", "deprecation" })
	private String getDateCellValue(HSSFCell cell) {
		String result = "";
		try {
			int cellType = cell.getCellType();
			if (cellType == 0) {
				Date date = cell.getDateCellValue();
				result = date.getYear() + 1900 + "-" + (date.getMonth() + 1)
						+ "-" + date.getDate();
			} else if (cellType == 1) {
				String date = getStringCellValue(cell);
				result = date.replaceAll("[年月]", "-").replace("日", "").trim();
			} else if (cellType == 3) {
				result = "";
			}
		} catch (Exception e) {
			System.out.println("日期格式不正确!");
			e.printStackTrace();
		}
		return result;
	}

	private String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case 0:
			case 2:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);
				} else {
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			case 1:
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			default:
				cellvalue = " ";
				break;
			}
		} else
			cellvalue = "";

		return cellvalue;
	}
	
	private String getCellFormatValueForXSSFCell(XSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case 0:
			case 2:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);
				} else {
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			case 1:
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			default:
				cellvalue = " ";
				break;
			}
		} else
			cellvalue = "";
		
		return cellvalue;
	}
	
	/**解决excel存储的日期格式为自定义返回为数字的情况**/
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public Map<Integer, Map<Integer, String>> readExcelContentDate(InputStream is) {
		Map content = new HashMap();
		Map str = new HashMap();
		try {
			this.fs = new POIFSFileSystem(is);
			this.wb = new HSSFWorkbook(this.fs);
			this.sheet = this.wb.getSheetAt(0);
			int rowNum = this.sheet.getLastRowNum();
			this.row = this.sheet.getRow(1);
			int colNum = this.row.getPhysicalNumberOfCells();
			
			for (int i = 1; i <= rowNum; i++) {
				this.row = this.sheet.getRow(i);
				int j = 0;
				while (j < colNum) {
					if(j ==3){ //第三行日期列，excel中日期返回格式为数字型
						str.put(Integer.valueOf(j), getCellFormatValueDate(this.row.getCell((short) j)).trim());
					}else{
						str.put(Integer.valueOf(j), getCellFormatValue(this.row.getCell((short) j)).trim());
					}
					j++;
				}
				content.put(Integer.valueOf(i), str);
				str = new HashMap();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}
	
	/**
	 * 读取Xlsx类型的文件--20180919
	 * 
	 * @param sheet.getLastRowNum（）；//最后一行行标，比行数小1
	 * @param sheet.getLastCellNum（）；//获取列数，比最后一列列标大1
	 * @param sheet.getLastCellNum 是获取最后一个不为空的列是第几个。
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	public Map<Integer, Map<Integer, String>> readXlsxExcel(InputStream is) {
		Map content = new HashMap();
		Map str = new HashMap();
		
		try {
			this.xb = new XSSFWorkbook(is);
			this.xssSheet= xb.getSheetAt(0);
			int rowNum = this.xssSheet.getLastRowNum();////取得最后一行的行号
			this.xssRow = this.xssSheet.getRow(1);//行
			int colNum=this.xssRow.getPhysicalNumberOfCells();//获取不为空的列  的个数。
			
			for (int i = 1; i <= rowNum; i++) {
				this.xssRow = this.xssSheet.getRow(i);
				int j = 0;
				while (j < colNum) {
					if(j ==3){ 
						str.put(Integer.valueOf(j), getCellFormatValueDateForXSSFCell(this.xssRow.getCell((short) j)).trim());
					}else{
						str.put(Integer.valueOf(j), getCellFormatValueForXSSFCell(this.xssRow.getCell((short) j)).trim());
					}
					j++;
				}
				content.put(Integer.valueOf(i), str);
				str = new HashMap();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	/**解决excel存储的日期格式为自定义返回为数字的情况**/
	private String getCellFormatValueDate(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case 0:
				Date date0 = cell.getDateCellValue();
				SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
				cellvalue = sdf0.format(date0);
				break;
			case 2:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);
				} else {
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			case 1:
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			default:
				cellvalue = " ";
				break;
			}
		} else
			cellvalue = "";

		return cellvalue;
	}
	
	/**解决excel存储的日期格式为自定义返回为数字的情况**/
	private String getCellFormatValueDateForXSSFCell(XSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case 0:
				Date date0 = cell.getDateCellValue();
				SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
				cellvalue = sdf0.format(date0);
				break;
			case 2:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);
				} else {
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			case 1:
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			default:
				cellvalue = " ";
				break;
			}
		} else
			cellvalue = "";
		
		return cellvalue;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public Map<Integer, Map<Integer, String>> readExcelContent1(InputStream is) {
		Map content = new HashMap();
		Map str = new HashMap();
		try {
			this.fs = new POIFSFileSystem(is);
			this.wb = new HSSFWorkbook(this.fs);
			this.sheet = this.wb.getSheetAt(0);
			int rowNum = this.sheet.getLastRowNum();
			this.row = this.sheet.getRow(1);
			int colNum = this.row.getPhysicalNumberOfCells();
			
			for (int i = 1; i <= rowNum; i++) {
				this.row = this.sheet.getRow(i);
				int j = 0;
				while (j < colNum) {
					str.put(Integer.valueOf(j), getCellFormatValueDate(this.row.getCell((short) j)).trim());
					j++;
				}
				content.put(Integer.valueOf(i), str);
				str = new HashMap();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}
	
	@SuppressWarnings("unused")
	private String getStringCellValue1(HSSFCell cell) {
		String strCell = "";
		if(cell != null) {
	        DecimalFormat df = new DecimalFormat("#");  
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:  
				strCell = cell.getRichStringCellValue().getString().trim();  
	            break;  
	        case HSSFCell.CELL_TYPE_NUMERIC:  
	        	strCell = df.format(cell.getNumericCellValue()).toString();  
	            break;  
	        case HSSFCell.CELL_TYPE_BOOLEAN:  
	        	strCell = String.valueOf(cell.getBooleanCellValue()).trim();  
	            break;  
	        case HSSFCell.CELL_TYPE_FORMULA:  
	        	strCell = cell.getCellFormula();  
	            break;  
	        default:  
	        	strCell = "";  
			}
		}

		if(StringUtils.isEmpty(strCell)){
			return "";
		}
		return strCell;
	}
	
	public static XSSFWorkbook getXSSFWorkbookForQuestion(String[] column2,String title) {
		XSSFWorkbook wb = new XSSFWorkbook();
		xssSheet = wb.createSheet("sheet1");
		XSSFRow xssfrow = xssSheet.createRow(0);
		XSSFCell xssfcell=xssfrow.createCell(0);
		xssfcell.setCellStyle(getXSSFCellStyle5(wb));
		xssfcell.setCellValue(title);
		CellRangeAddress cra =new CellRangeAddress(0, 0, 0, column2.length-1);
		xssSheet.addMergedRegion(cra);
		RegionUtil.setBorderBottom(1, cra, xssSheet, wb); 
		RegionUtil.setBorderLeft(1, cra, xssSheet, wb); 
		RegionUtil.setBorderRight(1, cra, xssSheet, wb); 
		RegionUtil.setBorderTop(1, cra, xssSheet, wb);
		XSSFRow rowhear3 = xssSheet.createRow(1);
		for (int i = 0; i < column2.length; i++) {
			XSSFCell cellheader3 = rowhear3.createCell(i);
			cellheader3.setCellValue(column2[i]);
			cellheader3.setCellStyle(getXSSFCellStyle5(wb));
		}
		return wb;
	}
	
	public static XSSFCellStyle getXSSFCellStyle5(XSSFWorkbook wb) {
		XSSFCellStyle style1 = wb.createCellStyle();
		style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style1.setWrapText(true); 
		return style1;
	}
	
}
