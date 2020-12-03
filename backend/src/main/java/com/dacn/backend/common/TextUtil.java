package com.dacn.backend.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextUtil {
	
	public static String getDataFromTxt(File file) {
		String data = "";
		try {
		      Scanner myReader = new Scanner(file);
		      
		      while (myReader.hasNextLine()) {
		    	  String temp = myReader.nextLine();
		    	  temp.replace("#", "");
		    	  data += temp;
		    	  if (myReader.hasNext()) {
		    		  data += "\n";
		    	  }
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		}
		return data;
	}
	
	public static List<String> dataToList(File file) {
		List<String> lst = new ArrayList<>();
		try {
		      Scanner myReader = new Scanner(file);
		      
		      while (myReader.hasNextLine()) {
		    	  String data = myReader.nextLine() + "\n";
		    	  lst.add(data);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		}
		return lst;
	}
}
