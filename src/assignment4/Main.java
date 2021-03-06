package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Wesley Klock
 * wtk332
 * 15455
 * Slip days used: <0>
 * Fall 2016
 */

import java.util.Scanner;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        // handling each element possible input command
        while(true) {
        	String input = kb.nextLine();
        	String[] params = input.split(" ");
        	if(params[0].trim().equals("quit")) {
        		break;
        	}
        	else if(params[0].trim().equals("show")) {
        		Critter.displayWorld();
        	}
        	else if(params[0].trim().equals("step")) {
        		if(params.length==1) {
        			Critter.worldTimeStep();
        		}
        		else {
        			try {
        				int count = Integer.parseInt(params[1].trim());
        				for(int i = 0; i<count; i++) {
            				Critter.worldTimeStep();
            			}
        			}
        			catch(Exception e){
        				System.out.println("error processing:"+input);
        			}
        		}
        	}
        	else if(params[0].trim().equals("seed")) {
        		try {
        			Critter.setSeed(Long.parseLong(params[1].trim()));
        		}
        		catch(Exception e) {
        			System.out.println("error processing:"+input);
        		}
        	}
        	else if(params[0].trim().equals("make")){
        		String name = params[1].trim();
        		if(params.length==2) {
        			try {
    					Critter.makeCritter(name);
    				} 
            		catch (InvalidCritterException e) {
            			System.out.println("error processing:"+input);
    				}
        		}
        		int count = 0;
        		if(params.length>2) {
        			try {
        				count=Integer.parseInt(params[2].trim());
        			}
        			catch(NumberFormatException e){
        				System.out.println("error processing:"+input);
        			}
        		}
        		for(int i = 0; i<count; i++) {
        			try {
    					Critter.makeCritter(name);
    				} 
            		catch (InvalidCritterException e) {
            			System.out.println("error processing:"+input);
    					e.printStackTrace();
    				}
    			}
        	}
        	else if(params[0].trim().equals("stats")) {
        		String name = params[1].trim();
        		try {
        			// using reflection to invoke runStats
        			Method stats =Class.forName(myPackage+"."+name).getMethod("runStats", java.util.List.class);
					java.util.List<Critter> instances = Critter.getInstances(name);
					stats.invoke(null, instances);
				} 
        		catch (InvalidCritterException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					System.out.println("error processing: "+input);
				}
        	}
        	else {
        		System.out.println("invalid command: "+input);
        	}
        	
        		
        }
        
        /* Write your code above */
        System.out.flush();

    }
}
