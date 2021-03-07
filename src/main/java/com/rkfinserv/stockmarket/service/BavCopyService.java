package com.rkfinserv.stockmarket.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rkfinserv.stockmarket.exception.BavCopyException;
import com.rkfinserv.stockmarket.model.BavCopy;
import com.rkfinserv.stockmarket.model.BavCopyAudit;
import com.rkfinserv.stockmarket.model.BavCopyResult;
import com.rkfinserv.stockmarket.repositories.BavCopyAuditRepository;
import com.rkfinserv.stockmarket.repositories.BavCopyRepository;

@Service
public class BavCopyService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BavCopyRepository bavCopyRepository;
	private final BavCopyAuditRepository bavCopyAuditRepository;

	private final String DOWNLOAD_URL_PATTERN = "https://archives.nseindia.com/content/historical/EQUITIES/%s/%s/cm%sbhav.csv.zip";
	private final String DOWNLOAD_FOLDER_PATH_PATTERN = "cm%sbhav.csv";

	@Autowired
	public BavCopyService(BavCopyRepository bavCopyRepository, BavCopyAuditRepository bavCopyAuditRepository) {
		super();
		this.bavCopyRepository = bavCopyRepository;
		this.bavCopyAuditRepository = bavCopyAuditRepository;
	}

	public List<BavCopy> getBavCopy() {
		return bavCopyRepository.findAll();
	}

	public List<BavCopy> getBavCopy(String symbol) {
		List<BavCopy> bavCopies = new ArrayList<BavCopy>();
		Optional<BavCopy> bavCopyOptional = bavCopyRepository.findById(symbol);
		if (bavCopyOptional.isPresent()) {
			bavCopies.add(bavCopyOptional.get());
		}
		return bavCopies;
	}

	public List<BavCopyAudit> getBavCopyAudit(String symbol) {
		return bavCopyAuditRepository.findBySymbol(symbol);
	}

	public void loadBavCopy(String date, boolean isLatest) throws BavCopyException {
		List<String> seriesFilter = List.of("EQ", "BE");
		String filePath = downloadFile(date);
		List<BavCopy> bavRecords = readFile(filePath);
		List<BavCopy> filteredBavRecords = bavRecords.stream().filter(record -> seriesFilter.contains(record.getSeries())).collect(Collectors.toList());
		persistRecords(filteredBavRecords, isLatest);
		removeFile(filePath);
	}

	private void persistRecords(List<BavCopy> bavCopies, boolean isLatest) {
		bavCopies.forEach(bavCopy -> {
			if (isLatest) {
				bavCopyRepository.save(bavCopy);
			}
			BavCopyAudit bavCopyAudit = BavCopyAudit.createAudit(bavCopy);
			bavCopyAuditRepository.save(bavCopyAudit);
		});

		LOG.info("competed saving all bavcoies {}", bavCopies.size());
	}

	private void removeFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
			LOG.info("File is deleted {}", filePath);
		}

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
						.timeStamp(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss z").parse(elements[10] + " 23:00:00 IST"))
						.totalTrades(Long.valueOf(elements[11])).isin(elements[12]).insertedAt(new Date()).build();
				record.setChange();
				record.setChangePercentage();
				bavRecords.add(record);
			}

		} catch (Exception e) {
			throw new BavCopyException(e, BavCopyResult.FAILED_READ_FILE);
		}

		return bavRecords;
	}

	private String downloadFile(String date) throws BavCopyException {
		String yearText = date.substring(5);
		String monthText = date.substring(2, 5);

		String path = String.format(DOWNLOAD_URL_PATTERN, yearText, monthText, date);

		String folderPath = String.format(DOWNLOAD_FOLDER_PATH_PATTERN, date);
		LOG.info("downloading the bavfile {}", path);
		try {
			FileOutputStream fos = new FileOutputStream(folderPath);
			URL url = new URL(path);
			URLConnection con = url.openConnection();
			con.setConnectTimeout(2000);
			con.setReadTimeout(2000);
			ZipInputStream zipStream = new ZipInputStream(con.getInputStream());
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
