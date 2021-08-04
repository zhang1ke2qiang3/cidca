package com.cidca.handleData;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cidca.utils.JDBCUtil;


public class Basedata {
	

	/**
	 * 读取Xlsx类型的文件--20180919
	 * 
	 * @param sheet.getLastRowNum（）；//最后一行行标，比行数小1
	 * @param sheet.getLastCellNum（）；//获取列数，比最后一列列标大1
	 * @param sheet.getLastCellNum 是获取最后一个不为空的列是第几个。
	 * @throws SQLException 
	 */
	public void handleExcel() throws SQLException {
//		String fileName = "D:\\工作资料\\合作署\\基础数据\\1.xlsx";  //修改d盘的1.xlsx文件
//		Connection connection = JDBCUtil.getConnection();
//		Statement statement32=connection.createStatement();
//		try {
//			XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(fileName));
//			XSSFSheet xSheet = xwb.getSheetAt(0);  //获取excel表的第一个sheet
//			int num=0;
//			for (int i = 1; i <= xSheet.getLastRowNum(); i++) {//从第2列开始遍历所有的行；i = 1表示从第二行开始否则会覆盖了标题
//				num++;
//				String a = (xSheet.getRow(i)).getCell(0).toString();//获取第一列单元格的数据
//				String b  = (xSheet.getRow(i)).getCell(1).toString();//获取第二列单元格的数据
//				statement32.addBatch("insert into t_base_data (code,name) values('"+a+"','"+b+"')");
//				if (num==5) {
//					statement32.executeBatch();
//					statement32.clearBatch();
//					num=0;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			JDBCUtil.close(connection, statement32);
//		}
	}

	
	public void handleExcel2() throws SQLException {
//		String fileName = "D:\\工作资料\\合作署\\基础数据\\1.xlsx";  //修改d盘的1.xlsx文件
//		Connection connection = JDBCUtil.getConnection();
//		Statement statement32=connection.createStatement();
//		try {
//			XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(fileName));
//			XSSFSheet xSheet = xwb.getSheetAt(2);  //获取excel表的第一个sheet
//			int num=0;
//			int code=2000;
//			for (int i = 1; i <= xSheet.getLastRowNum(); i++) {//从第2列开始遍历所有的行；i = 1表示从第二行开始否则会覆盖了标题
//				num++;
//				code++;
//				String shortname = (xSheet.getRow(i)).getCell(0)+"";//获取第一列单元格的数据
//				String name  = (xSheet.getRow(i)).getCell(1)+"";//获取第二列单元格的数据
//				name=getStr(name);
//				String pcode  = (xSheet.getRow(i)).getCell(2)+"";//获取第二列单元格的数据
//				statement32.addBatch("insert into t_base_data (code,shortname,name,pcode,remark) values('"+code+"','"+shortname+"','"+name+"','"+pcode+"','1')");
//				if (num==5) {
//					statement32.executeBatch();
//					statement32.clearBatch();
//					num=0;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			JDBCUtil.close(connection, statement32);
//		}
	}

	public String getStr(String str){
		if (StringUtils.isNotEmpty(str)) {
			str=str.replaceAll("'", "''");
		}
		return str;
	}
	
	public static void main(String[] args) {
		Basedata bd=new Basedata();
		try {
			bd.handleExcel2();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
}
