package com.firebirdcss.tools.file_line_comparator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * This class handles the processing of a file which is assigned to it.
 * <p>
 * As part of the processing this class breaks a file down into a {@link List} of lines that are trimmed.
 * Empty lines are not added to the file List.
 * 
 * @author Scott Griffis
 *
 */
public class FileProcessor extends Thread {
	private boolean readProblems = false;
	private File file;
	private volatile List<String> lines;
	
	/**
	 * CONSTRUCTOR: Initialization of variables happens here.
	 * @param file
	 * @param listForLines
	 */
	public FileProcessor(File file, List<String> listForLines) {
		this.file = file;
		this.lines = listForLines;
	}
	
	/**
	 * This method can be used post-processing to see if there was a read error generated
	 * during the file read process.
	 * 
	 * @return Returns true if there was a error while reading the file, otherwise false as {@link boolean}
	 */
	public boolean isReadProblems() {
		
		return readProblems;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try (FileReader fReader = new FileReader(file); BufferedReader bReader = new BufferedReader(fReader);) {
			String line = null;
			while((line = bReader.readLine()) != null) {
				line = line.trim();
				if (!line.isEmpty()) {
					this.lines.add(line);
				}
			}
		} catch (Exception e) {
			this.readProblems = true;
		} 
	}
}
