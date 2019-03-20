
/************************************************/
/*** Purpose:                                 ***/
/***                                          ***/
/***                                          ***/
/*** Author:                                  ***/
/*** Date:                                    ***/
/***                                          ***/
/*** Note: Based on skeleton code provided by ***/
/*** Jason Steggles 23/09/2016                ***/
/************************************************/

import java.io.*;
import java.text.*;
import java.util.*;

public class Search {

	/** Global variables for counting comparisons **/
	public int compSeq = 0;
	public int compBin = 0;
	public int compHash = 0;

	/** Global variables for tracking total comparisons **/
	public int totalSeq = 0;
	public int totalBin = 0;
	public int totalHash = 0;

	/** Global variables for tracking total times compared **/
	public int countSeq = 0;
	public int countBin = 0;
	public int countHash = 0;

	/** Array of values to be searched and size **/
	private int[] A;
	private int size;

	/** Hash array and size **/
	private int[] H;
	private int hSize;

	/** Constructor **/
	Search(int n, int hn) {
		/** set size of array **/
		size = n;
		hSize = hn;

		/** Create arrays **/
		A = new int[size];
		H = new int[hSize];

		/** Initialize hash array **/
		/** Assume -1 indicates a location is empty **/
		for (int i = 0; i < hSize; i++) {
			H[i] = -1;
		}
	}

	/********************************************/
	/*** Read a file of numbers into an array ***/
	/********************************************/
	public void readFileIn(String file) {
		try {
			/** Set up file for reading **/
			FileReader reader = new FileReader(file);
			Scanner in = new Scanner(reader);

			/** Loop round reading in data **/
			for (int i = 0; i < size; i++) {
				/** Get net value **/
				A[i] = in.nextInt();
			}
		} catch (IOException e) {
			System.out.println("Error processing file " + file);
		}
	}

	/*********************/
	/*** Hash Function ***/
	/*********************/
	public int hash(int key) {
		return key % hSize;
	}

	/*****************************/
	/*** Display array of data ***/
	/*****************************/
	public void displayData(int line, String header) {
		/* ** Integer Formatter ** */
		NumberFormat FI = NumberFormat.getInstance();
		FI.setMinimumIntegerDigits(3);

		/** Print header string **/
		System.out.print("\n\n" + header);

		/** Display array data **/
		for (int i = 0; i < size; i++) {
			/** New line? **/
			if (i % line == 0) {
				System.out.println();
			}

			/** Display value **/
			System.out.print(FI.format(A[i]) + " ");
		}
	}

	/**************************/
	/*** Display hash array ***/
	/**************************/
	public void displayHash(int line) {
		/** Integer Formatter **/
		NumberFormat FI = NumberFormat.getInstance();
		FI.setMinimumIntegerDigits(3);

		/** Print header string **/
		System.out.print("\n\nHash Array of size " + hSize);

		/** Display array data **/
		for (int i = 0; i < hSize; i++) {
			/** New line? **/
			if (i % line == 0) {
				System.out.println();
			}

			/** Display value **/
			System.out.print(FI.format(H[i]) + " ");
		}
	}

	public int seqSearch(int key) {
		/* search in array A for specific key */
		
		compSeq = 0;
		int i = 0;
		for (i = 0; A[i] <= key; i++) { //check through all array until key is bigger than array value
			compSeq++;
			if (A[i] == key) { //if key found, print attempts and return i
				System.out.println(key + " Sequental attempts: " + compSeq);
				totalSeq = totalSeq + compSeq;
				countSeq++;
				return i;
			}
		}

		// }
		System.out.println(key + " Sequential attempts: " + compSeq); //if key not found, return -1
		totalSeq = totalSeq + compSeq;
		countSeq++;
		return -1;

	}

	public int binSearch(int key, int L, int R) {
		int m = -1;
		if (R < L) { //if r<l, print attempts and return -1
			System.out.println("Binary search attempts: " + compBin);
			totalBin = totalBin + compBin;
			countBin++;
			compBin = 0;
			return -1;
		}
		m = (R + L) / 2; //set m to next pointer
		if (key == A[m]) { //if key = m position, print attempts and return m
			compBin++;
			System.out.println("Binary search attempts: " + compBin);
			totalBin = totalBin + compBin;
			countBin++;
			compBin = 0;
			return m;
		}
		if (key > A[m]) { //if key bigger than m position, recursive with m+1 as l
			compBin++;
			return binSearch(key, m + 1, R);
		} else {
			compBin++;
			return binSearch(key, L, m - 1); //if key smaller than m position, recursive with m-1 as r
		}

	}

	private void addToHash(int value) {
		int location = hash(value);
		while (H[location] != -1) { //WHILE LOCATION NOT EMPTY
			if (location == hSize) { //if location is end of hash, return to beginning of hash
				location = 0;
			} else { //or increment location until empty value found
				location++;
			}
		}
		H[location] = value;
	}

	public void readIntoHash(String file) {
		try {
			/** Set up file for reading **/
			FileReader reader = new FileReader(file);
			Scanner in = new Scanner(reader);

			/** Loop round reading in data **/
			for (int i = 0; i < size; i++) {
				/** Get net value **/
				addToHash(in.nextInt());
			}

		} catch (IOException e) {
			System.out.println("Error processing file " + file);
		}
	}

	public int hashSearch(int key) { //search for value using hash search
		int location = 0;
		location = hash(key); //find hash location
		compHash = 0;

		for (int i = location; i < hSize; i++) { //from location, check all values up to end of hash
			compHash++;
			if (H[i] == key) { //if key found, print attempts and return location
				System.out.println("Hash search attempts: " + compHash);
				totalHash = totalHash + compHash; //add to total hash
				countHash++; //increment count hash
				return location;
			} else if (H[i] == -1) { //if value is empty, return to loop statement
				break;
			}
		}

		//if not found, print attempts and return -1
		System.out.println("Hash search attempts: " + compHash);
		totalHash = totalHash + compHash;
		countHash++;
		return -1;

	}

	public void testSeq() {
		//perform first set of tests on sequential
		System.out.println("\n\nSequential searches:");
		System.out.println("18 location: " + seqSearch(18));
		System.out.println("69 location: " + seqSearch(69));
		System.out.println("201 location: " + seqSearch(201));
		System.out.println("331 location: " + seqSearch(331));
		System.out.println("17 location: " + seqSearch(17));
		System.out.println("67 location: " + seqSearch(67));
		System.out.println("209 location: " + seqSearch(209));
		System.out.println("372 location: " + seqSearch(372));
		System.out.println("498 location: " + seqSearch(498));
		System.out.println("Sequential Average: " + getAverage(totalSeq, countSeq));
		
		//perform first set of tests on binary
		System.out.println("\n\nBinary searches:");
		System.out.println("18 location: " + binSearch(18, 0, 100));
		System.out.println("69 location: " + binSearch(69, 0, 100));
		System.out.println("201 location: " + binSearch(201, 0, 100));
		System.out.println("331 location: " + binSearch(331, 0, 100));
		System.out.println("17 location: " + binSearch(17, 0, 100));
		System.out.println("67 location: " + binSearch(67, 0, 100));
		System.out.println("209 location: " + binSearch(209, 0, 100));
		System.out.println("372 location: " + binSearch(372, 0, 100));
		System.out.println("498 location: " + binSearch(498, 0, 100));
		System.out.println("Binary Average: " + getAverage(totalBin, countBin));

		//perform first set of tests on hash
		System.out.println("\n\nHash searches:");
		System.out.println("18 location: " + hashSearch(18));
		System.out.println("69 location: " + hashSearch(69));
		System.out.println("201 location: " + hashSearch(201));
		System.out.println("331 location: " + hashSearch(331));
		System.out.println("17 location: " + hashSearch(17));
		System.out.println("67 location: " + hashSearch(67));
		System.out.println("209 location: " + hashSearch(209));
		System.out.println("372 location: " + hashSearch(372));
		System.out.println("498 location: " + hashSearch(498));
		System.out.println("Hash Average: " + getAverage(totalHash, countHash));
	}

	public void testSeq2() {
		//reset total and count values from first set of tests, perform second set on sequential
		totalSeq = 0;
		countSeq = 0;
		System.out.println("\n\nSequential searches:");
		System.out.println("20 location: " + seqSearch(20));
		System.out.println("832 location: " + seqSearch(832));
		System.out.println("1452 location: " + seqSearch(1452));
		System.out.println("1937 location: " + seqSearch(1937));
		System.out.println("2615 location: " + seqSearch(2615));
		System.out.println("87 location: " + seqSearch(87));
		System.out.println("851 location: " + seqSearch(851));
		System.out.println("1350 location: " + seqSearch(1350));
		System.out.println("1990 location: " + seqSearch(1990));
		System.out.println("2631 location: " + seqSearch(2631));
		System.out.println("Sequential Average: " + getAverage(totalSeq, countSeq));

		//reset total and count values from first set of tests, perform second set on binary
		totalBin = 0;
		countBin = 0;
		System.out.println("\n\nBinary searches:");
		System.out.println("20 location: " + binSearch(20, 0, 1000));
		System.out.println("832 location: " + binSearch(832, 0, 1000));
		System.out.println("1452 location: " + binSearch(1452, 0, 1000));
		System.out.println("1937 location: " + binSearch(1937, 0, 1000));
		System.out.println("2615 location: " + binSearch(2615, 0, 1000));
		System.out.println("87 location: " + binSearch(87, 0, 1000));
		System.out.println("851 location: " + binSearch(851, 0, 1000));
		System.out.println("1350 location: " + binSearch(1350, 0, 1000));
		System.out.println("1990 location: " + binSearch(1990, 0, 1000));
		System.out.println("2631 location: " + binSearch(2631, 0, 1000));
		System.out.println("Binary Average: " + getAverage(totalBin, countBin));

		//reset total and count values from first set of tests, perform second set on hash
		totalHash = 0;
		countHash = 0;
		System.out.println("\n\nHash searches:");
		System.out.println("20 location: " + hashSearch(20));
		System.out.println("832 location: " + hashSearch(832));
		System.out.println("1452 location: " + hashSearch(1452));
		System.out.println("1937 location: " + hashSearch(1937));
		System.out.println("2615 location: " + hashSearch(2615));
		System.out.println("87 location: " + hashSearch(87));
		System.out.println("851 location: " + hashSearch(851));
		System.out.println("1350 location: " + hashSearch(1350));
		System.out.println("1990 location: " + hashSearch(1990));
		System.out.println("2631 location: " + hashSearch(2631));
		System.out.println("Hash Average: " + getAverage(totalHash, countHash));
	}

	public int getAverage(int total, int count) {
		int average = total / count;
		return average;
	}
} /*** End of class Search ***/