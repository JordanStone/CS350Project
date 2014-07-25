/*
 * QTrueFalse.java
 * Created by Jordan Stone, 2014
 * 
 * QTrueFalse.java creates and handles the QTrueFalse object. 
 * QTrueFalse creates a true/false question that can be used for Surveys and Tests. 
 * True/False questions have the user give answer with True or False.
 * 
 * Subclass of QMatching.java.
 */

package surveymaker;

import java.io.IOException;
import java.util.ArrayList;

public class QTrueFalse extends QMultiChoice {
	
	private static final long serialVersionUID = 13L;
	
	public QTrueFalse(String text){ //Constructor
		super(text,new ArrayList<String>());
		choice.add("True");
		choice.add("False");	
	}
	
	public void modify() throws IOException{
		String s;
		
		Out.getDisp().renderLine("Would you like to change the prompt? (Y/N)");
		s = Input.inputString();
		if (s.equals("Y")||s.equals("y")){
			Out.getDisp().renderLine(prompt);
			Out.getDisp().renderLine("Please input a new prompt.");
			s = Input.inputString();
			setPrompt(s);
		}else Out.getDisp().renderLine("Prompt left unchanged.");
	}
	
	private Response takeAnswer() throws IOException{ //Implementation to take answer input. Used for storing user responses and storing answer keys.
		String s = null;
		boolean validIn = false;
		while(!validIn){
			s = Input.inputString();
			if (!(s.equals("True")) && !(s.equals("False"))){
				Out.getDisp().renderLine("Input given neither True or False. Please input again.");
			}else
				validIn = true;
		}//while(!validIn)
		
		return new RespStr(s);
	}
	
	public Response modifyAns() throws IOException{ //Used for modifying answers for a Test
		Out.getDisp().renderLine("Enter a new answer, either True or False.");
		
		return takeAnswer();
	}
	
	public Response take() throws IOException{ //Used for taking Survey or Test
		Out.getDisp().renderLine("Enter either True or False.");
		
		return takeAnswer();
	}
	
	public void tabulate(ArrayList<Response> resp){ //Takes a list of relevant responses for a question and displays all choices made
		int trueCount = 0, falseCount = 0;
		
		for (int i = 0; i < resp.size(); i++){
			if (((RespStr)resp.get(i)).getAns().equals("True")){
				trueCount++;
			}else if (((RespStr)resp.get(i)).getAns().equals("False")){
				falseCount++;
			}
		}
		
		Out.getDisp().renderLine("Number of True responses: " + trueCount);
		Out.getDisp().renderLine("Number of False responses: " + falseCount);
		
	}
	
}
