package com.firebirdcss.tools.file_line_comparator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * 
 * @author Scott Griffis
 *
 */
public class FileProcessor extends Thread {
	private boolean readProblems = false;
	private File file;
	private volatile List<String> lines;
	
	/**
	 * 
	 * @param file
	 * @param listForLines
	 */
	public FileProcessor(File file, List<String> listForLines) {
		this.file = file;
		this.lines = listForLines;
	}
	
	/**
	 * 
	 * @return
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
