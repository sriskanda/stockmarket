package com.rkfinserv.stockmarket;

public class POC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String DOWNLOAD_URL_PATTERN = "https://archives.nseindia.com/content/historical/EQUITIES/%s/%s/cm%sbhav.csv.zip"; ; 
		
		String dateText = "02JAN2021";
		String yearText = dateText.substring(5);
		String monthText = dateText.substring(2, 5);
		
		
		String path = String.format(DOWNLOAD_URL_PATTERN,yearText,monthText,yearText);
		System.out.println(path);
		
	}

}
