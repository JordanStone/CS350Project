/*
 * Question.java
 * Created by Jordan Stone, 2014
 * 
 * Question.java creates the abstract Question object. Questions are used by Surveys and Tests to represent questions
 * on a survey or test.
 * 
 * Subclasses include QMultiChoice, QEssay, and QMatching.
 */

package surveymaker;

import java.io.IOException;
import java.util.ArrayList;

abstract public class Question implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	protected String prompt; //Holds the prompt
	
	public Question(String inp){ //Constructor. Only for console display now.
		prompt = inp;
	}
	
	abstract protected void modify() throws IOException; //Modify question
		
	abstract protected void show(); //Display question
	
	abstract protected Response take() throws IOException; //Take input from the user to store as a response
	
	abstract protected Response modifyAns() throws IOException; //Modify question's answer (for Tests)
	
	abstract protected void tabulate(ArrayList<Response> resp); //Takes a list of relevant responses for a question and displays all choices made
	
//Getter Methods
	public String getPrompt(){
		return prompt;
	}
	
//Setter Methods
	public void setPrompt(String s){
		prompt = s;
	}

}
