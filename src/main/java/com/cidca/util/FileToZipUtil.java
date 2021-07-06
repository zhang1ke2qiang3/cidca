package com.cidca.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
/**
 * https://www.cnblogs.com/Animation-programmer/p/8124463.html
 *
 */
public class FileToZipUtil {
	/**
	 * 多文件打包
	 * 
	 * @param fileOutName 将要打包的新文件名
	 * @param files 完整路径文件
	 * @throws Exception
	 */
	public static void compress(String fileOutName, List<File> files) throws Exception {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileOutName);
			ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
			zipOutputStream.setEncoding("gbk");
			if (files != null && files.size() > 0) {
				for (int i = 0, size = files.size(); i < size; i++) {
					compress(files.get(i), zipOutputStream, "");
				}
			}
			zipOutputStream.flush();
			zipOutputStream.close();
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param srcFile
	 * @param zipOutputStream
	 * @param basePath
	 * @throws Exception
	 */
	public static void compress(File srcFile, ZipOutputStream zipOutputStream, String basePath) throws Exception {
		compressFile(srcFile, zipOutputStream, basePath);
	}

	/**
	 * 
	 * @param file
	 * @param zipOutputStream
	 * @param dir
	 * @throws Exception
	 */
	private static void compressFile(File file, ZipOutputStream zipOutputStream, String dir) throws Exception {
		try {
			ZipEntry zipEntry = new ZipEntry(dir + file.getName());
			zipOutputStream.putNextEntry(zipEntry);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			int count = 0;
			byte data[] = new byte[2048];
			while ((count = bis.read(data, 0, 2048)) != -1) {
				zipOutputStream.write(data, 0, count);
			}
			bis.close();
			zipOutputStream.closeEntry();
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	/**
	 * 删除文件夹
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if(files[i].exists()) {
					files[i].delete();
				}
			}
			file.delete();// 删除文件夹
		}
	}
	    
	
	
	
	
	
}
