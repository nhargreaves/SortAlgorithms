
/******************************************************/
/*** Purpose: Test class to illustrate Search class ***/
/***                                                ***/
/*** Author: L. J. Steggles                         ***/
/*** Date: 23/09/2016                               ***/
/******************************************************/

import java.io.*;

public class TestSearch {
	public static void main(String[] args) {


		/** RESULTS FOR DATA 1**/
		Search S = new Search(100, 151);

		//Read in test data
		S.readFileIn("data1.txt");
		S.readIntoHash("data1.txt");

		//display data and hash array
		S.displayData(20, "Data Array");
		S.displayHash(20); 

		//test values for data 1
		S.testSeq();

		
		/** RESULTS FOR DATA 2**/
		Search S2 = new Search(1000, 1499);

		//Read in test data
		S2.readFileIn("data2.txt");
		S2.readIntoHash("data2.txt");

		//display data and hash array
		S2.displayData(20, "Data Array");
		S2.displayHash(20); 

		//test values for data 2
		S2.testSeq2();
		
		
	}

}