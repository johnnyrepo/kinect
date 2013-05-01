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
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import ee.ttu.kinect.model.Body;

public class FileUtil {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private BufferedWriter fileWriter;

	private BufferedReader fileReader;

	private List<String> stringCache = new ArrayList<String>();

	private int stringCachePosition = 0;

	public void readFile(File file) throws FileNotFoundException {
		fileReader = new BufferedReader(new FileReader(file));
		emptyCache();
		try {
			String input = fileReader.readLine();
			logger.info("opening... " + input);
			while ((input = fileReader.readLine()) != null) {
				addCachedString(input);
			}
			fileReader.close();
		} catch (IOException e) {
			logger.info(e.getLocalizedMessage());
		}
	}

	public synchronized void dumpFile(boolean seatedMode) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd_HH-mm-ss");
		File file = new File(System.getProperty("user.dir") + "/"
				+ sdf.format(new Date()) + ".csv");
		fileWriter = new BufferedWriter(new FileWriter(file, true));

		fileWriter.write("experimentId");
		fileWriter.newLine();
		sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		fileWriter.write(sdf.format(new Date()));
		fileWriter.newLine();
		fileWriter.write(Body.getHeader(seatedMode));
		fileWriter.newLine();

		fileWriter.write(getCachedText());

		emptyCache();
		fileWriter.close();
	}

	public synchronized void addToSave(String text) throws IOException {
		addCachedString(text);
	}

	public synchronized String readNextLine() throws IOException {
		String str = null;
		try {
			str = stringCache.get(stringCachePosition);
			stringCachePosition++;
		} catch(Exception e) {
			logger.info(e.getLocalizedMessage());
		}
		return str;
	}
	
	public synchronized List<String> readAllLines() {
		return stringCache;
	}

	private String getCachedText() {
		StringBuffer cachedText = new StringBuffer();
		for (String cached : stringCache) {
			cachedText = cachedText.append(cached).append("\n");
		}
		return cachedText.toString();
	}

	private void addCachedString(String text) {
		stringCache.add(text);
	}

	private void emptyCache() {
		stringCache = new ArrayList<String>();
		stringCachePosition = 0;
	}

}
