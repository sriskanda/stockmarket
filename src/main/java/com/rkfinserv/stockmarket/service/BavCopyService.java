package com.rkfinserv.stockmarket.service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rkfinserv.stockmarket.exception.BavCopyException;
import com.rkfinserv.stockmarket.model.BavCopy;
import com.rkfinserv.stockmarket.repositories.BavCopyRepository;

@Service
public class BavCopyService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private final BavCopyRepository bavCopyRepository;

	private final String DOWNLOAD_URL_PATTERN = "https://archives.nseindia.com/content/historical/EQUITIES/2021/JAN/cm%sbhav.csv.zip"; ; 
	private final String DOWNLOAD_FOLDER_PATH_PATTERN = "C:\\\\Users\\\\harivar\\\\Documents\\\\personal\\\\finance\\\\bavcopy\\\\cm%sbhav.csv";
	
	public BavCopyService(BavCopyRepository bavCopyRepository) {
		super();
		this.bavCopyRepository = bavCopyRepository;
	}

	public void persistRecords(List<BavCopy> bavCopies) {
		bavCopies.forEach(bavCopy -> {
			bavCopyRepository.save(bavCopy);
		});

		LOG.info("competed saving all bavcoies {}", bavCopies.size());
	}

	public void loadBavCopy(String date) throws BavCopyException {
		String filePath = downloadFile(date);
		List<BavCopy> bavRecords = readFile(filePath);
		persistRecords(bavRecords);
	}

	private List<BavCopy> readFile(String filePath) throws BavCopyException {
		LOG.info("Reading the file {}", filePath);
		List<BavCopy> bavRecords = new ArrayList<BavCopy>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line;
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] elements = line.split(",");
				BavCopy record = BavCopy.builder().symbol(elements[0]).series(elements[1])
						.open(Double.valueOf(elements[2])).high(Double.valueOf(elements[3]))
						.low(Double.valueOf(elements[4])).close(Double.valueOf(elements[5]))
						.last(Double.valueOf(elements[6])).prevClose(Double.valueOf(elements[7]))
						.totTrdQty(Long.valueOf(elements[8])).totTrdVal(Double.valueOf(elements[9]))
						.timeStamp(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss z").parse(elements[10]+" 23:00:00 IST"))
						.totalTrades(Long.valueOf(elements[11])).isin(elements[12]).insertedAt(new Date()).build();

				bavRecords.add(record);
			}

		} catch (Exception e) {
			throw new BavCopyException(e, BavCopyResult.FAILED_READ_FILE);
		}

		return bavRecords;
	}

	private String downloadFile(String date) throws BavCopyException {
		String path = String.format(DOWNLOAD_URL_PATTERN, date);
		String folderPath = String.format(DOWNLOAD_FOLDER_PATH_PATTERN, date);
		LOG.info("downloading the bavfile {}", path);
		try {
			FileOutputStream fos = new FileOutputStream(folderPath);
			URL url = new URL(path);
			ZipInputStream zipStream = new ZipInputStream(url.openConnection().getInputStream());
			zipStream.getNextEntry();
			byte[] buffer = new byte[1024];
			int len;

			while ((len = zipStream.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}

			zipStream.closeEntry();
			zipStream.close();
			fos.close();
			System.out.println("File has been successfully downloaded : " + path);
		} catch (IOException e) {
			LOG.error("Failed to download the bavfile", e);
			throw new BavCopyException(e, BavCopyResult.FAILED_DOWNLOAD_BAVCOPY);
		}
		return folderPath;
	}

}
