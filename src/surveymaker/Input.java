/*
 * Input.java
 * Created by Jordan Stone, 2014
 * 
 * Simple static input class to be used by other classes.
 * 
 */

package surveymaker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input {
	
	public static String inputString() throws IOException{ //Static readline function to input Strings (Stops from opening multiple readers
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); //Reader
		String s;
		s = in.readLine();
		return s;
	}

	public static int inputNum(int min, int max) throws IOException{ //Static function to ensure input from user is a valid number.
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); //Reader
		int val = 0; //Return variable val
		try{
			val = Integer.parseInt(in.readLine());
		}catch (NumberFormatException e){
			Out.getDisp().renderLine("Input not a number. Please input again.");
			return inputNum(min,max);
		}
		if (val < min || val > max){
			Out.getDisp().renderLine("Input out of range. Please input again.");
			return inputNum(min,max);
		}else{
			return val;
		}
	}
	
	
	
	
}
