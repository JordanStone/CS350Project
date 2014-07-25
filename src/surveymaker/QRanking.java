/*
 * QRanking.java
 * Created by Jordan Stone, 2014
 * 
 * QRanking.java creates and handles the QRanking object. 
 * QRanking creates a ranking question that can be used for Surveys and Tests. 
 * Ranking questions have the user place several given options into ranks.
 * 
 * Subclass of QEssay.java.
 */

package surveymaker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class QRanking extends QMatching {
	
	private static final long serialVersionUID = 15L;
	
	public QRanking(String text, ArrayList<String> rightcol){ //Constructor
		super(text,new ArrayList<String>(),rightcol);
		for (int i=1; i<=rightcol.size();i++){
			left.add(Integer.toString(i));
		}
	}
	
	public void modify() throws IOException{
		String s;
		int val = 0;
		
		Out.getDisp().renderLine("Would you like to change the prompt? (Y/N)");
		s = Input.inputString();
		if (s.equals("Y")||s.equals("y")){
			Out.getDisp().renderLine(prompt);
			Out.getDisp().renderLine("Please input a new prompt.");
			s = Input.inputString();
			setPrompt(prompt);
		}else Out.getDisp().renderLine("Prompt left unchanged.");
		
		Out.getDisp().renderLine("Would you like to change the choices? (Y/N)");
		s = Input.inputString();
		
		if (s.equals("Y")||s.equals("y")){
			for (int count = 1; count <= right.size(); count++){ //Display all right options
				Out.getDisp().renderLine(count + "- " + right.get(count-1));
			}
			boolean repeat = true;
			while(repeat){
				Out.getDisp().renderLine("Enter the number of the choice to modify.");
				val = Input.inputNum(1,right.size());
				Out.getDisp().renderLine("Enter the new choice value.");
				s = Input.inputString();
				
				right.set(val-1, s);
				
				Out.getDisp().renderLine("Edit another choice? (Y/N)");
				s = Input.inputString();
				if (s.equals("N")||s.equals("n")){
					repeat = false;
				}//while(!validIn)
			}//while(repeat)
			
		}else Out.getDisp().renderLine("Left choices left unchanged.");
	}
	
	private Response takeAnswer() throws IOException{ //Implementation to take answer input. Used for storing user responses and storing answer keys.
		String s = null;
		@SuppressWarnings("unchecked")
		ArrayList<String> copy = (ArrayList<String>) right.clone(); //Replacement of responses so responses are not lost
		HashMap<String, ArrayList<String>> dictAns = new HashMap<String, ArrayList<String>>(); //HashMap to pass to Response
		for (int i=1; i <= left.size(); i++){ //Assign ranks for every value
			ArrayList<String> arr = new ArrayList<String>(); //Stores the rankings
			
			Out.getDisp().renderLine("Enter the value at rank " + i + ":"); 
			
			boolean hasFailed = true; //Boolean to keep looping until valid input confirmed
			while (hasFailed){ //Input loop
				s = Input.inputString();
				for (int j = 0; j < copy.size(); j++){ //Compare to every valid response
					if (s.equals(copy.get(j))){ //Good input
						hasFailed = false; //Stop looping
						copy.remove(j); //Remove the response as an option to avoid duplicates
					}
				}
				if (hasFailed)
					Out.getDisp().renderLine("Given Answer Not In Given Options. Please enter again.");
			} //while (hasFailed)
			
			arr.add(s); //Pop on the single rank
			dictAns.put(getLeft().get(i-1), arr); //Add to the Dict
		} //for (int i=1; i <= val; i++)
		
		return new RespDict(dictAns);
	}
	
	public Response modifyAns() throws IOException{ //Used for modifying answers for a Test
		Out.getDisp().renderLine("Enter new ranks for all given options.");
		
		return takeAnswer();
	}
	
	public Response take() throws IOException{ //Used for taking Survey or Test
		Out.getDisp().renderLine("Rank the given options in order.");
		
		return takeAnswer();
	}
	
}
