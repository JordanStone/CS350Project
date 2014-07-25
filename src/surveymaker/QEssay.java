/*
 * QEssay.java
 * Created by Jordan Stone, 2014
 * 
 * QEssay.java creates and handles the QEssay object. 
 * QEssay creates an essay question that can be used for Surveys and Tests. 
 * Essay questions have the user give word input without a limit.
 * 
 * Subclasses include QShortAns.
 */

package surveymaker;

import java.io.IOException;
import java.util.ArrayList;

public class QEssay extends Question {

	private static final long serialVersionUID = 11L;
	
	public QEssay(String text){
		super(text);
	}
	
	public void modify() throws IOException {
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
	
	public void show() { //Displays the Essay question.
		Out.setDisp(2); //Set to Voice
		Out.getDisp().renderLine(prompt);
		Out.setDisp(1); //Set to Text
	}
	
	public Response modifyAns() throws IOException{ //Shouldn't be used for Essays - only Short Answer.
		Out.getDisp().renderLine("Essays do not have a defined answer.");
		return new RespStr(null);
	}

	public Response take() throws IOException { //Take input from the user to store as a response
		String s;
		Out.getDisp().renderLine("Enter your answer. There is no defined limit.");
		
		s = Input.inputString();
		return new RespStr(s);
	}
	
	public void tabulate(ArrayList<Response> resp){ //Takes a list of relevant responses for a question and displays all choices made
		for (int i = 1; i < resp.size()+1; i++){
			Out.getDisp().renderLine("Response " + i + ":");
			resp.get(i-1).render();
		}
	}

}
