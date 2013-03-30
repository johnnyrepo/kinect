package ee.ttu.kinect.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class FileWorker {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private BufferedWriter fileWriter;

	private BufferedReader fileReader;
	
	private List<String> writerCache = new ArrayList<String>();
	
	public void openFileToWrite(String fileName, String experimentId, String header) throws IOException {
		emptyCache();
		File file = new File(fileName);
		fileWriter = new BufferedWriter(new FileWriter(file, true));
		fileWriter.write(experimentId);
		fileWriter.newLine();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		fileWriter.write(sdf.format(new Date()));
		fileWriter.newLine();
		fileWriter.write(header);
		fileWriter.newLine();
	}

	public void openFileToRead(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		fileReader = new BufferedReader(new FileReader(file));
		try {
			logger.info("opening... " + fileReader.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeFile() throws IOException {
		if (fileWriter != null) {
			fileWriter.write(getCachedText());
			emptyCache();
			fileWriter.close();
		}

		if (fileReader != null) {
			fileReader.close();
		}
	}

	public void saveToFile(String textToSave) throws IOException {
		addCachedText(textToSave);
	}

	private String getCachedText() {
		StringBuffer cachedText = new StringBuffer();
		for (String cached : writerCache) {
			cachedText = cachedText.append(cached).append("\n");
		}
		return cachedText.toString();
	}

	private void addCachedText(String text) {
		writerCache.add(text);
	}
	
	private void emptyCache() {
		writerCache = new ArrayList<String>();
	}
	
	public void deleteFile(String fileName) {
		File file = new File(fileName);
		file.delete();
	}
	
	public String readNextLine() throws IOException {
		return fileReader.readLine();
	}

}
