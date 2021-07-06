package com.cidca.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Row;

/**文件工具类*/
public class FileUtils {

	/**保存文件*/
	public static void saveFileFromInputStream(InputStream stream,String path) throws IOException{
		if(stream == null || stream.available() == 0 ) {
			return;
		}
		FileOutputStream fs=null;
		try {
			fs=new FileOutputStream( path );
			byte[] buffer =new byte[8192];
			int byteread = 0; 
			while ((byteread=stream.read(buffer))!=-1){
				fs.write(buffer,0,byteread);
				fs.flush();
			} 
			fs.close();
			stream.close();  
		} catch (Exception e) {
			String simplename = e.getClass().getSimpleName();
			if("ClientAbortException".equals(simplename)){
				System.out.println("客户端刷新太快了");   
			}else {
				e.printStackTrace();   
			}
		}finally {
			if (null!=fs) {
				fs.close();
			}
			if (null!=stream) {
				fs.close();
			}
		}

	}

	/**
	 * 删除文件
	 * @param path 
	 */
	public static void deleteFile(String path){
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
	}

	/**文件下载*/
	public static void download(HttpServletRequest request,  HttpServletResponse response, String contentType, String path) {
		File file =new File(path);
		if (!file.isFile() || !file.exists() || file.length() == 0) {
			return ;//防止报异常
		}

		BufferedInputStream bis = null;  
		BufferedOutputStream bos = null;  
		try {
			response.setContentType("text/html;charset=UTF-8");  
			request.setCharacterEncoding("UTF-8");  
			long fileLength = new File(path).length(); 
			String realName = path.substring(path.lastIndexOf("/")+1); 

			response.setContentType(contentType);  
			response.setHeader("Content-disposition", "attachment; filename="+ new String(realName.getBytes("utf-8"), "ISO8859-1"));  
			response.setHeader("Content-Length", String.valueOf(fileLength));

			bis = new BufferedInputStream(new FileInputStream(path));  
			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buff = new byte[8192];
			int bytesRead;  
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
				bos.write(buff, 0, bytesRead);  
			}
			if (null!=bis) {bis.close();}
			if (null!=bos) {bos.close();}
		} catch (Exception e) {
			String simplename = e.getClass().getSimpleName();
			if("ClientAbortException".equals(simplename)){
				System.out.println("客户端刷新太快了");   
			}else {
				e.printStackTrace();   
			}
		} finally {
			try {
				if (null!=bis) {bis.close();}
				if (null!=bos) {bos.close();}
			} catch (Exception e2) {
				String simplename = e2.getClass().getSimpleName();
				if("ClientAbortException".equals(simplename)){
					System.out.println("客户端刷新太快了");   
				}else {
					e2.printStackTrace();   
				}
			}
		}

	}

	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @param contentType
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static int download1(HttpServletRequest request,  HttpServletResponse response, String contentType, String path) throws Exception{  
		response.setContentType("text/html;charset=UTF-8");  
		request.setCharacterEncoding("UTF-8");  

		BufferedInputStream bis = null;  
		BufferedOutputStream bos = null;  
		int count = 0;  
		try {
			long fileLength = new File(path).length(); 
			String realName = path.substring(path.lastIndexOf("\\")+1); 
			response.setContentType(contentType);  
			response.setHeader("Content-disposition", "attachment; filename="+ new String(realName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(path));  
			bos = new BufferedOutputStream(response.getOutputStream());  
			byte[] buff = new byte[8192];  
			int bytesRead;  
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
				bos.write(buff, 0, bytesRead);  
				count++;
			}
			bis.close();  
			bos.close();  
		}catch (Exception e) {
			String simplename = e.getClass().getSimpleName();
			if("ClientAbortException".equals(simplename)){
				System.out.println("客户端刷新太快了");   
			}else {
				e.printStackTrace();   
			}
		} finally {
			try {
				if (null!=bis) {bis.close();}
				if (null!=bos) {bos.close();}
			} catch (Exception e2) {
				String simplename = e2.getClass().getSimpleName();
				if("ClientAbortException".equals(simplename)){
					System.out.println("客户端刷新太快了");   
				}else {
					e2.printStackTrace();   
				}
			}
		}
		return count;
	}

	/**
	 * 强制下载
	 * @param fileName
	 * @param realPath
	 * @param request
	 * @param response
	 */
	public static void forceDownload(String fileName,String realPath,HttpServletRequest request, HttpServletResponse response) {
		if (!"".equals(fileName) && fileName != null) {
			if(fileName.indexOf("/") != -1) {
				fileName=fileName.substring(fileName.lastIndexOf("/"));
			}
			File file = new File(realPath, fileName);
			if (file.exists()) {
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.addHeader("Content-Disposition",
						"attachment;fileName=" + fileName);// 设置文件名
				byte[] buffer = new byte[8192];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					bis.close();
					os.close();
				} catch (Exception e) {
					String simplename = e.getClass().getSimpleName();
					if("ClientAbortException".equals(simplename)){
						System.out.println("客户端刷新太快了");   
					}else {
						e.printStackTrace();   
					}
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							String simplename = e.getClass().getSimpleName();
							if("ClientAbortException".equals(simplename)){
								System.out.println("客户端刷新太快了");   
							}else {
								e.printStackTrace();   
							}
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							String simplename = e.getClass().getSimpleName();
							if("ClientAbortException".equals(simplename)){
								System.out.println("客户端刷新太快了");   
							}else {
								e.printStackTrace();   
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 判断文件是否存在
	 * @param path
	 * @return
	 */
	public static boolean isFileExists(String path){
		File file = new File(path);
		return file.exists();
	}

	/**
	 * 单个文件复制
	 * @param oldPath
	 * @param newPath
	 */
	public static void copyFile(String oldPath, String newPath) {
		InputStream inStream =null;
		FileOutputStream fs =null;
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { //文件存在时
				inStream = new FileInputStream(oldPath); //读入原文件
				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[8192];
				while((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		}catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			String simplename = e.getClass().getSimpleName();
			if("ClientAbortException".equals(simplename)){
				System.out.println("客户端刷新太快了");   
			}else {
				e.printStackTrace();   
			}
		}finally {
			try {
				if (null!=inStream) {inStream.close();}
				if (null!=fs) {fs.close();}
			} catch (IOException e) {
				System.out.println("复制单个文件操作出错");
				String simplename = e.getClass().getSimpleName();
				if("ClientAbortException".equals(simplename)){
					System.out.println("客户端刷新太快了");   
				}else {
					e.printStackTrace();   
				}
			}
		}
	}

	/**
	 * 单个文件复制
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public static boolean copyFile1(String oldPath, String newPath) {
		InputStream inStream =null;
		FileOutputStream fs =null;
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { //文件存在时
				inStream = new FileInputStream(oldPath); //读入原文件
				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[8192];
				while((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			String simplename = e.getClass().getSimpleName();
			if("ClientAbortException".equals(simplename)){
				System.out.println("客户端刷新太快了");   
			}else {
				e.printStackTrace();   
			}
			return false;
		}finally {
			try {
				if (null!=inStream) {inStream.close();}
				if (null!=fs) {fs.close();}
			} catch (IOException e) {
				System.out.println("复制单个文件操作出错");
				String simplename = e.getClass().getSimpleName();
				if("ClientAbortException".equals(simplename)){
					System.out.println("客户端刷新太快了");   
				}else {
					e.printStackTrace();   
				}
			}
		}
	}

	/**
	 * 根据模板导出使用
	 * 定位当前行、列 设置列值
	 * @param obj
	 * @param row
	 * @param cell
	 * @param index
	 */
	public static void createRowAndCell(Object obj, Row row, HSSFCell cell, int index) {
		cell = (HSSFCell)row.getCell(index);
		if (obj != null) {
			cell.setCellValue(obj.toString());
		} else {
			cell.setCellValue("");
		}
	}

	/**
	 * 根据模板导出使用
	 * 复制文件
	 * @param s 源文件
	 * @param t 复制到的新文件
	 */
	public static void fileChannelCopy(File s, File t) {
		InputStream in = null;
		OutputStream out = null;
		try {
			try {
				in = new BufferedInputStream(new FileInputStream(s), 1024);
				out = new BufferedOutputStream(new FileOutputStream(t), 1024);
				byte[] buffer = new byte[8192];
				int len;
				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
			}finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		}catch (Exception e) {
			String simplename = e.getClass().getSimpleName();
			if("ClientAbortException".equals(simplename)){
				System.out.println("客户端刷新太快了");   
			}else {
				e.printStackTrace();   
			}
		}finally {
			try {
				if (null!=in) {in.close();}
				if (null!=out) {out.close();}
			} catch (IOException e) {
				System.out.println("复制单个文件操作出错");
				String simplename = e.getClass().getSimpleName();
				if("ClientAbortException".equals(simplename)){
					System.out.println("客户端刷新太快了");   
				}else {
					e.printStackTrace();   
				}
			}
		}
	}


	/**
	 * 读取模板文件 创建新文件
	 * @param tempPath
	 * @param rPath
	 * @param newFileName
	 * @return
	 */
	public static File createNewFile(String tempPath, String rPath,String newFileName) {
		// 读取模板，并赋值到新文件
		// 文件模板路径
		String path = (tempPath);
		File file = new File(path);
		// 保存文件的路径
		String realPath = rPath;

		// 判断路径是否存在
		File dir = new File(realPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 写入到新的excel
		File newFile = new File(realPath, newFileName);
		try {
			newFile.createNewFile();
			// 复制模板到新文件
			fileChannelCopy(file, newFile);
		} catch (Exception e) {
			String simplename = e.getClass().getSimpleName();
			if("ClientAbortException".equals(simplename)){
				System.out.println("客户端刷新太快了");   
			}else {
				e.printStackTrace();   
			}
		}
		return newFile;
	}

	/**删除文件*/
	public static void deleteFile(File... files) {
		for (File file : files) {
			if (file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * 判断文件类型
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 判定是否是图片文件：jpg、png、gif、bmp、tif、jpeg、tiff
	 */
	public static boolean getImageType(InputStream is) throws Exception{
		byte[] b = new byte[3];
		is.read(b, 0, b.length);
		String xxx = bytesToHexString(b);
		xxx = xxx.toUpperCase();
		is.close();
		return checkImageType(xxx);
	}


	public static boolean checkImageType(String variablee) {
		boolean flag=false;
		if (!StringUtils.isEmpty(variablee)) {
			if ("FFD8FF".equals(variablee)) {flag=true;}
			if ("89504E".equals(variablee)) {flag=true;}
			if ("474946".equals(variablee)) {flag=true;}
			if ("424D66".equals(variablee)) {flag=true;}
			if ("49492A".equals(variablee)) {flag=true;}
		}
		return flag;
	}

	/**
	 * 判定是否是办公文件：xls、doc、ppt、xlsx、docx、pptx、txt、pdf、rtf
	 */
	public static boolean getOfficeType(InputStream is) throws Exception{//java.io.ByteArrayInputStream@61c20387
		byte[] b = new byte[3];
		is.read(b, 0, b.length);
		String xxx = bytesToHexString(b);
		xxx = xxx.toUpperCase();
		is.close();
		return checkOfficeType(xxx);
	}

	public static boolean checkOfficeType(String variablee) {
		boolean flag=false;
		if (!StringUtils.isEmpty(variablee)) {
			if ("D0CF11".equals(variablee)) {flag=true;}
			if ("504B03".equals(variablee)) {flag=true;}
			if ("526172".equals(variablee)) {flag=true;}
			if ("B7A2CB".equals(variablee)) {flag=true;}
			if ("255044".equals(variablee)) {flag=true;}
			if ("7B5C72".equals(variablee)) {flag=true;}
		}
		return flag;
	}

	public static boolean getLegitimacyOfFile(InputStream is) throws Exception{
		byte[] b = new byte[3];
		is.read(b, 0, b.length);
		String xxx = bytesToHexString(b);
		xxx = xxx.toUpperCase();
		if (null!=is) {is.close();}
		return checkFileType(xxx);
	}

	public static boolean checkFileType(String variablee) {
		boolean flag=false;
		if (!StringUtils.isEmpty(variablee)) {
			if ("D0CF11".equals(variablee)) {flag=true;}
			if ("504B03".equals(variablee)) {flag=true;}
			if ("526172".equals(variablee)) {flag=true;}
			if ("B7A2CB".equals(variablee)) {flag=true;}
			if ("255044".equals(variablee)) {flag=true;}
			if ("7B5C72".equals(variablee)) {flag=true;}

			if ("33332".equals(variablee)) {flag=true;}
			if ("FFD8FF".equals(variablee)) {flag=true;}
			if ("89504E".equals(variablee)) {flag=true;}
			if ("474946".equals(variablee)) {flag=true;}
			if ("424D66".equals(variablee)) {flag=true;}
			if ("49492A".equals(variablee)) {flag=true;}
		}
		return flag;
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		FileInputStream is = new FileInputStream("D:\\ttt\\1.txt");
		byte[] b = new byte[3];
		is.read(b, 0, b.length);
		String xxx = bytesToHexString(b);
		xxx = xxx.toUpperCase();
		System.out.println("头文件是：" + xxx);
		checkImageType(xxx);
	}

	/**
    常用文件的文件头如下：(以前六位为准)
   JPEG (jpg)，文件头：FFD8FF 
   PNG (png)，文件头：89504E47 
   GIF (gif)，文件头：47494638 
   TIFF (tif)，文件头：49492A00 
   Windows Bitmap (bmp)，文件头：424D 
   CAD (dwg)，文件头：41433130 
   Adobe Photoshop (psd)，文件头：38425053 
   Rich Text Format (rtf)，文件头：7B5C727466 
   XML (xml)，文件头：3C3F786D6C 
   HTML (html)，文件头：68746D6C3E 
   Email [thorough only] (eml)，文件头：44656C69766572792D646174653A 
   Outlook Express (dbx)，文件头：CFAD12FEC5FD746F 
   Outlook (pst)，文件头：2142444E 
   MS Word/Excel (xls.or.doc)，文件头：D0CF11E0 
   MS Access (mdb)，文件头：5374616E64617264204A 
   WordPerfect (wpd)，文件头：FF575043 
   Postscript (eps.or.ps)，文件头：252150532D41646F6265 
   Adobe Acrobat (pdf)，文件头：255044462D312E 
   Quicken (qdf)，文件头：AC9EBD8F 
   Windows Password (pwl)，文件头：E3828596 
   ZIP Archive (zip)，文件头：504B0304 
   RAR Archive (rar)，文件头：52617221 
   Wave (wav)，文件头：57415645 
   AVI (avi)，文件头：41564920 
   Real Audio (ram)，文件头：2E7261FD 
   Real Media (rm)，文件头：2E524D46 
   MPEG (mpg)，文件头：000001BA 
   MPEG (mpg)，文件头：000001B3 
   Quicktime (mov)，文件头：6D6F6F76 
   Windows Media (asf)，文件头：3026B2758E66CF11 
   MIDI (mid)，文件头：4D546864 

   ("4D5A9000", "exe/dll");
	 */

}