package com.classsocialnet.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * Utility class to load Data from text Files
 * 
 * @since October 30 2012
 * */
public class LoadData {
	
	public static String[][] readTextFiles(){
		File folder = new File("resources");
		File[] files;
		BufferedReader input;
		String[][] rm = null;
		
		if(folder.isDirectory()){
			files = folder.listFiles();
			
			rm = new String[files.length][];
			
			for(int i = 0; i < files.length; i++){
				try {
					input = new BufferedReader(new FileReader(files[i]));
					
					String line = null;
					
					while((line = input.readLine()) != null){						
						line = line.replaceAll(" ", "");
						line = line.replaceAll("-", ",");
						line = line.toUpperCase();
						
						System.out.println(line);
						
						String[] splitLine = line.split(","); 
						rm[i] = new String[splitLine.length];
						
						for(int j = 0; j < splitLine.length; j++){
							rm[i][j] = splitLine[j];
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
		return rm;
	}
}
