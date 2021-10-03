package com.rkfinserv.stockmarket.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.rkfinserv.stockmarket.model.LiveStockPrice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LiveStockPriceService {

	public List<LiveStockPrice> getLiveStockPrices() {
		List<LiveStockPrice> liveStockPrices = new ArrayList<LiveStockPrice>();
		File file = null;
		FileInputStream fis = null;
		
		try {
			file = new File("C:\\Users\\harivar\\Documents\\personal\\finance\\YahooStockData.xlsx"); // creating a
																											// new file
																											// instance
			fis = new FileInputStream(file); // obtaining bytes from the file
			// creating Workbook instance that refers to .xlsx file
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0); // creating a Sheet object to retrieve object
			Iterator<Row> itr = sheet.iterator(); // iterating over excel file

			while (itr.hasNext()) {
				LiveStockPrice liveStockPrice = LiveStockPrice.builder().livePrice(BigDecimal.ZERO).percentageChage(BigDecimal.ZERO).build();
				Row row = itr.next();
				Iterator<Cell> cellIterator = row.cellIterator(); // iterating over each column
				int cellNo = 0;
				while (cellIterator.hasNext()) {
					cellNo++;
					if (cellNo == 1) {
						cellIterator.next(); // ignore first column
					} else {
						Cell cell = cellIterator.next();

						switch (cell.getCachedFormulaResultType()) {
						case STRING: // field that represents string cell type
							if (cellNo == 2) {
								liveStockPrice.setSymbol(cell.getRichStringCellValue().toString());
							}
							System.out.print(cell.getRichStringCellValue() + "\t\t\t");
							break;
						case NUMERIC: // field that represents number cell type
							if (cellNo == 3) {
								liveStockPrice.setLivePrice(BigDecimal.valueOf(cell.getNumericCellValue()).setScale(2,
										RoundingMode.HALF_UP));
							}else if(cellNo ==4) {
								liveStockPrice.setPercentageChage(BigDecimal.valueOf(cell.getNumericCellValue()*100).setScale(2,
										RoundingMode.HALF_UP));
							}else if (cellNo == 5) {
								liveStockPrice.setLastTradedAt(cell.getDateCellValue());
							}else if (cellNo == 6) {
								liveStockPrice.setYearHigh(BigDecimal.valueOf(cell.getNumericCellValue()).setScale(2,
										RoundingMode.HALF_UP));
							}else if (cellNo == 7) {
								liveStockPrice.setYearLow(BigDecimal.valueOf(cell.getNumericCellValue()).setScale(2,
										RoundingMode.HALF_UP));
							}else if (cellNo == 8) {
								liveStockPrice.setVolume(BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0,
										RoundingMode.HALF_UP));
							}
							else {
								
							}
							break;
						case FORMULA: // field that represents number cell type
							System.out.print(cell.getCellFormula() + "\t\t\t");
							break;
						default:
							System.out.println("xxx");
						}
					}

				}
				liveStockPrices.add(liveStockPrice);
			}

		} catch (Exception e) {
			log.error("Failed read live prices from excel ", e);
			e.printStackTrace();
		}finally {
			if(fis !=null)
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
		return liveStockPrices;
	}

	
	public Map<String, LiveStockPrice> getLiveStockPriceMap(){
		Map<String, LiveStockPrice>	livePriceMap = new ConcurrentHashMap<String, LiveStockPrice>();
		getLiveStockPrices().forEach(livePrice -> {
			livePriceMap.put(livePrice.getSymbol(), livePrice);
		});;	
		return livePriceMap;
	}
}
