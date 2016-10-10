package com.firebirdcss.tools.file_line_comparator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The Application's main point of entry.
 * 
 * @author Scott Griffis
 *
 */
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
		
		if (processor1.isReadProblems() || processor2.isReadProblems()) {
			System.out.println("ERROR: There were problems processing at least one of the files; Please check to ensure the files are valid!");
			System.exit(1);
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
	
	/**
	 * Displays the results.
	 * 
	 * @param lines1 - Final list of non-matching lines from file 1 as {@link List} of {@link String}s
	 * @param lines2 - Final list of non-matching lines from file 2 as {@link List} of {@link String}s
	 */
	private static void displayResults(List<String> lines1, List<String> lines2) {
		if (lines1.size() == 0 && lines2.size() == 0) {
			System.out.println("The two files are the same.");
		} else {
			if (lines1.size() > 0) {
				System.out.println("File #1 contains '" + lines1.size() + "' line(s) that were not in file #2.\nThey are as follows:");
				for (String line : lines1) {
					System.out.println("\tLINE: " + line);
				}
				System.out.println();
			}
			
			if (lines2.size() > 0) {
				System.out.println("File #2 contains '" + lines2.size() + "' line(s) that were not in file #1.\nThey are as follows:");
				for (String line : lines2) {
					System.out.println("\tLINE: " + line);
				}
				System.out.println();
			}
		}
	}
	
	/**
	 * Handles processing the arguments passed into the file.
	 * 
	 * @param args - The arguments as a {@link String} array
	 */
	private static void handleArgs(String[] args) {
		for (String arg : args) {
			switch (arg) {
				case "-h":
				case "--help":
					displayUsage();
					break;
				default:
					if (file1 == null) {
						file1 = new File(arg);
						if (!(file1.isFile() && file1.canRead())) {
							System.out.println("ERROR: The supplied file #1 of '" + file1.getName() + "' is either not a file or not readable!");
							System.exit(1);
						}
					} else if (file2 == null) {
						file2 = new File(arg);
						if (!(file2.isFile() && file2.canRead())) {
							System.out.println("ERROR: The supplied file #2 of '" + file2.getName() + "' is either not a file or not readable!");
							System.exit(1);
						}
					} else {
						displayUsage();
						System.exit(1);
					}
					break;
			}
		}
	}
	
	/**
	 * Displays the program usage syntax for the user.
	 */
	private static void displayUsage() {
		System.out.println("usage: file-line-comparator <file 1> <file 2>\n");
	}
}
