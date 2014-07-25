/*
 * QShortAns.java
 * Created by Jordan Stone, 2014
 * 
 * QShortAns.java creates and handles the QShortAns object. 
 * QShortAns creates a short answer question that can be used for Surveys and Tests. 
 * Short Answer questions have the user give word input with a specific character limit.
 * 
 * Subclass of QEssay.java.
 */

package surveymaker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class QShortAns extends QEssay {

	private static final long serialVersionUID = 14L;
	private int limit; //Character limit
	
	public QShortAns(String text, int textLimit){ //Constructor
		super(text);
		limit = textLimit;
	}
	
	public void modify() throws IOException{
		String s;
		int val = 0;
		
		super.modify();
		
		Out.getDisp().renderLine("Would you like to change the character limit? (Y/N)");
		s = Input.inputString();
		
		if (s.equals("Y")||s.equals("y")){
			Out.getDisp().renderLine("Current char limit: " + limit);
			
			Out.getDisp().renderLine("Enter the new limit value.");
			boolean validIn = false;
			
			while(!validIn){
				try{
					val = Integer.parseInt(Input.inputString());
				}catch (NumberFormatException e){
					Out.getDisp().renderLine("Input not a number. Please input again.");
				}
				if (val < 1){
					Out.getDisp().renderLine("Input out of range. Please input again.");
				}else validIn = true;
			}
			
		}
		
	}
	
	private Response takeAnswer() throws IOException{ //Implementation to take answer input. Used for storing user responses and storing answer keys.
		String s;
		s = Input.inputString();
		while (s.length() > limit){ //Make sure the answer is in the limits
			Out.getDisp().renderLine("Answer is too long. Please give a new answer within the limit (" + limit + " chars).");
			s = Input.inputString();
		}
		return new RespStr(s);	
	}
	
	public Response modifyAns() throws IOException{ //Used for modifying answers for a Test
		Out.getDisp().renderLine("Enter the new correct answer within " + limit + " characters.");
		
		return takeAnswer();
	}
	
	public Response take() throws IOException{ //Used for taking Survey or Test
		Out.getDisp().renderLine("Enter your answer within " + limit + " characters.");
		
		return takeAnswer();
	}
	
	public void tabulate(ArrayList<Response> resp){ //Takes a list of relevant responses for a question and displays all choices made
		HashMap<String, Integer> answers = new HashMap<String, Integer>();
		
		for (int i = 0; i < resp.size(); i++){
			String key = ((RespStr)resp.get(i)).getAns();
			if (answers.get(key) == null){ //If no value found, put a 1.
				answers.put(key,1);
			}else{
				answers.put(key, answers.get(key)+1); //Else, increment
			}	
		}
		
		for (Entry<String, Integer> entry : answers.entrySet()){ //Iterate through all keys
			Out.getDisp().renderLine(entry.getKey() + ": " + entry.getValue()); //Print the key and number of times it appears		
		}

	}

//Getter Methods	
	public int getLimit(){		
		return limit;
	}
}
