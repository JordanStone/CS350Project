/*
 * QMatching.java
 * Created by Jordan Stone, 2014
 * 
 * QMatching.java creates and handles the QMatching object. 
 * QMatching creates a matching question that can be used for Surveys and Tests. 
 * Matching questions have the user compare two given columns.
 * 
 * Subclasses include QRanking.
 */

package surveymaker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class QMatching extends Question{

	private static final long serialVersionUID = 12L;
	protected ArrayList<String> left,right; //Represent the left and right columns.
	
	public QMatching(String text, ArrayList<String> leftcol, ArrayList<String> rightcol){ //Constructor
		super (text);
		left = leftcol;
		right = rightcol;
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
			setPrompt(s);
		}else Out.getDisp().renderLine("Prompt left unchanged.");
		
		Out.getDisp().renderLine("Would you like to change the left choices? (Y/N)");
		s = Input.inputString();
		
		if (s.equals("Y")||s.equals("y")){
			for (int count = 1; count <= left.size(); count++){ //Display all left options
				Out.getDisp().renderLine(count + "- " + left.get(count-1));
			}
			boolean repeat = true;
			while(repeat){
				Out.getDisp().renderLine("Enter the number of the choice to modify.");
				val = Input.inputNum(1,left.size());
				Out.getDisp().renderLine("Enter the new choice value.");
				s = Input.inputString();
				
				left.set(val-1, s);
				
				Out.getDisp().renderLine("Edit another choice? (Y/N)");
				s = Input.inputString();
				if (s.equals("N")||s.equals("n")){
					repeat = false;
				}
			}//while(repeat)
			
		}else Out.getDisp().renderLine("Left choices left unchanged.");
		
		Out.getDisp().renderLine("Would you like to change the right choices? (Y/N)");
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
				}
			}//while(repeat)
			
		}else Out.getDisp().renderLine("Right choices left unchanged.");
		
	}
	
	public void show(){ //Displays the Matching question.
		Out.setDisp(2); //Set to Voice
		
		Out.getDisp().renderLine(prompt);
		String formL,formR; //Strings to hold left and right values to make formatting easier
		for (int count = 1; count <= left.size() || count <= right.size(); count++){ //If either side still has elements, loop
			if (count <= left.size()){ //If left side still has elements
				formL = left.get(count-1) + ": "; //Set left formatting
			}else formL = ""; //Blank out left side
			if (count <= right.size()){ //If right side still has elements
				formR = right.get(count-1); //Set right formatting
			}else formR = ""; //Blank out right side
//			System.out.printf("%-20.30s  %-20.30s%n",formL, formR);
			Out.getDisp().renderLine(formL + "                    " + formR); //20 Spaces
		}// for (int count = 1; count <= left.size() || count <= right.size(); count++)
		
		Out.setDisp(1); //Set to Text
		
	}
	
	private Response takeAnswer() throws IOException{ //Implementation to take answer input. Used for storing user responses and storing answer keys.
		String s;
		HashMap<String, ArrayList<String>> dictAns = new HashMap<String, ArrayList<String>>(); //HashMap to pass to Response
		
		for (int i=0; i < getLeft().size(); i++){ //Assign ranks for every value
			@SuppressWarnings("unchecked")
			ArrayList<String> copy = (ArrayList<String>) right.clone(); //Replacement of right to allow popping off options
			ArrayList<String> rank = new ArrayList<String>(); //Stores the matches
			Out.getDisp().renderLine("Enter the values for " + getLeft().get(i) + ":");
			Out.getDisp().renderLine("Type \"-END-\" when finished for the current key.");
			
			boolean isEnd = false; //Boolean to keep looping until valid input confirmed
			while (!isEnd){ //Input loop
				boolean validInput = false;
				while (!validInput){ //Validity loop
					s = Input.inputString();
					if (s.equals("-END-")){ //No more matches to this Option
						copy.clear(); //Skip for loop by clearing the array
						validInput = true; //Stop looping
						isEnd = true; //Stop outer looping
					}
					for (int j = 0; j < copy.size(); j++){ //Compare to every valid response
						if (s.equals(copy.get(j))){ //Good input
							validInput = true; //Stop looping
							rank.add(s); //Add to the array of matches
							copy.remove(j); //Remove the response as an option to avoid duplicates
						}							
					}
					if (!validInput){
						Out.getDisp().renderLine("Input is not one of the question's existing choices. Please input again.");
					}
				} //while (!validInput)
			} //while (!isEnd)				
			dictAns.put(getLeft().get(i), rank); //Add to the Dict
		} //for (int i=1; i <= val; i++)
		
		return new RespDict(dictAns);
	}
	
	public Response modifyAns() throws IOException{ //Used for modifying answers for a Test
		Out.getDisp().renderLine("Enter new correct values for every key.");
		
		return takeAnswer();
	}
	
	public Response take() throws IOException{ //Used for taking Survey or Test
		Out.getDisp().renderLine("Submit responses from the right for each key on the left in order.");
		
		return takeAnswer();
	}
	
	public void tabulate(ArrayList<Response> resp){ //Takes a list of relevant responses for a question and displays all choices made
		HashMap<String, ArrayList<Integer>> count = new HashMap<String, ArrayList<Integer>>();
		HashMap<String, ArrayList<String>> s = null;
		ArrayList<String> vals = null;

		for (int i = 0; i < resp.size(); i++){ //For each response	
			s = ((RespDict)resp.get(i)).getAns(); //Pull a response
			for (Entry<String, ArrayList<String>> entry : s.entrySet()){ //Iterate through all keys
				if (count.get(entry.getKey()) == null){ //Prepare new key
					count.put(entry.getKey(),new ArrayList<Integer>());
				}
				vals = entry.getValue();

				for (int j = 0; j < right.size(); j++){ //For each right value
					if (count.get(entry.getKey()).size() < right.size()){ //Prepare new array element
						count.get(entry.getKey()).add(0);
					}
					for (int k = 0; k < vals.size(); k++){ //For each user input response
						if (right.get(j).equals(vals.get(k))){ //If right value is the response, increment
							count.get(entry.getKey()).set(j, count.get(entry.getKey()).get(j) + 1);
						}
					}//for (int k = 0; k < vals.size(); k++)
				}//for (int j = 0; j < right.size(); j++)
			}//for (Entry<String, ArrayList<String>> entry : s.entrySet())
		}//for (int i = 0; i < resp.size(); i++)
		
		for (int i = 0; i < left.size(); i++){
			Out.getDisp().renderLine(left.get(i) + ":");
			for (int j = 0; j < right.size(); j++){
				Out.getDisp().renderLine(right.get(j) + "- " + count.get(left.get(i)).get(j));
			}
		}
		
	}
	
//Getter Methods
	
	public ArrayList<String> getLeft(){
		return left;
	}
	public ArrayList<String> getRight(){
		return right;
	}
	
}
