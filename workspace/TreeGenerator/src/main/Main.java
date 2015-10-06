package main;

import java.io.*;
import java.util.Random;

public class Main {
	static final String OUTPUT_PATH = "/Users/DYKim/Desktop/Algohw1/input.txt";	// "c:\\hw1\\2012147504.txt";	// output file path
	private static PrintWriter fout;										// file Output objects
	
	static final int depth = 20;
	static final int numberOfTree = 1;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		fout = new PrintWriter(Main.OUTPUT_PATH);
		
		fout.println(numberOfTree);
		for(int i=0; i<numberOfTree; i++) {
			fout.print((depth+1) + " ");
			generateTree();
		}
		
		fout.close();
	}
	
	static void generateTree() {
		generateTree(Main.depth);
		fout.print("\n");
	}
	static void generateTree(int depth) {
		fout.print("(");
		if(depth != 1) {
			fout.print("1");
		}
		else {
			Random random = new Random();
			if(random.nextInt(2000) < 1) {	// 0.05%
				fout.print("2");
			}
			else {
				fout.print("1");
			}
		}
		if(depth!=1) generateTree(depth-1);
		else fout.print("()");
		if(depth!=1) generateTree(depth-1);
		else fout.print("()");
		fout.print(")");
	}

}
