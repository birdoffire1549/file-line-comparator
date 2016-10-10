package com.firebirdcss.tools.file_line_comparator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ApplicationMain {
	private static File file1 = null;
	private static File file2 = null;
	
	/**
	 * Application main entry point.
	 * 
	 * @param args - The application's arguments as {@link String}
	 */
	public static void main(String[] args) {
		handleArgs(args);
		doWork();
	}
	
	/**
	 * This is where the bulk of the work of this utility is done.
	 */
	private static void doWork() {
		List<String> lines1 = new ArrayList<>();
		List<String> lines2 = new ArrayList<>();
		
		FileProcessor processor1 = new FileProcessor(file1, lines1);
		FileProcessor processor2 = new FileProcessor(file2, lines2);
		
		processor1.start();
		processor2.start();
		
		while(processor2.isAlive() && processor1.isAlive()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException ok) {}
		}
		
		List<String> dumpList = new ArrayList<>();
		for (String item : lines1) {
			if (lines2.remove(item)) {
				dumpList.add(item);
			}
		}
		
		for (String item : dumpList) lines1.remove(item);
		
		displayResults(lines1, lines2);
	}
	
	private static void displayResults(List<String> lines1, List<String> lines2) {
		if (lines1.size() == 0 && lines2.size() == 0) {
			// TODO: Yea! the files had the same contents...
		} else {
			if (lines1.size() > 0) {
				
			}
		}
	}
	
	private static void handleArgs(String[] args) {
		
	}
	
	private static void displayUsage() {
		
	}
}
