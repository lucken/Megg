package com.meggnify.lib;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Utils {

	public static String appId() {
		return LocalContext.getContext().getPackageName();
	}
	
	public static Boolean writeStream(String path, byte[] buffer) {
		try {
			BufferedOutputStream output =new BufferedOutputStream(new FileOutputStream(path), 8192);
			output.write(buffer);
			output.flush();
			output.close();
			
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static String readStream(String path) {
		String data = null;
		try {
			FileInputStream input = new FileInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"), 8);
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line + "\n");
			}
			data = builder.toString();
			input.close();
			reader.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static String readStream(InputStream input) {
		String data = null;
		if (input != null) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"), 8);
				StringBuilder builder = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					builder.append(line + "\n");
				}
				data = builder.toString();
				input.close();
				reader.close();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		return data;
	}

}
