package com.api.auto.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileUtils {
    //Đường dẫn đến properties files trong folder configuration
    private static String CONFIG_PATH = "./configuration/configs.properties";
    //1 Đọc các giá trị property từ file configs.properties
    public static String getProperty(String key) {
    	Properties properties= new Properties();
        String value = null;
        FileInputStream fileInputStream = null;
        //bat exception
        try {
        	fileInputStream = new FileInputStream(CONFIG_PATH);
            properties.load(fileInputStream);
            value = properties.getProperty(key);
            return value;
        } catch (Exception ex) {
            System.out.println("Xảy ra lỗi khi đọc giá trị của  " + key);
            ex.printStackTrace();
        } finally {
		//luôn nhảy vào đây dù có xảy ra exception hay không.
		//thực hiện đóng luồng đọc
		if (fileInputStream != null) {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	 }

        return value;
    }
    private static String TOKEN_PATH = "./configuration/token.properties";
    //2 Ghi giá trị token vào file token.properties để các API khác có thể sử dụng.
    public static void saveToken(String Key, String value) {
    	Properties properties= new Properties();
    	FileOutputStream fileOutputStream = null;
	try {
         //Khởi tạo giá trị cho đối tượng FileOutputStream
	    fileOutputStream = new FileOutputStream(TOKEN_PATH);
         //Load properties file hiện tại và thực hiện mapping value 
         //với key tương ứng
           properties.setProperty(Key, value);
         //Lưu key và value vào properties file
           properties.store(fileOutputStream, "Set new value in properties");
           System.out.println("Set new value in file properties success.");
	} catch (IOException ex) {
	     ex.printStackTrace();
	} finally {
	     //luôn nhảy vào đây dù có xảy ra exception hay không.
	     //thực hiện đóng luồng ghi
	     if (fileOutputStream != null) {
		  try {
			fileOutputStream.close();
		  } catch (IOException e) {
			e.printStackTrace();
		  }
	     }
	}

    }
    //3 Đọc giá trị token từ file  token.properties
    public static String getToken(String key) {
    	Properties properties= new Properties();
        String value = null;
        FileInputStream fileInputStream = null;
        //bat exception
        try {
        	fileInputStream = new FileInputStream(TOKEN_PATH);
            properties.load(fileInputStream);
            value = properties.getProperty(key);
            return value;
        } catch (Exception ex) {
            System.out.println("Xảy ra lỗi khi đọc giá trị của  " + key);
            ex.printStackTrace();
        } finally {
		//luôn nhảy vào đây dù có xảy ra exception hay không.
		//thực hiện đóng luồng đọc
		if (fileInputStream != null) {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	 }

        return value;
    }

}

