/*
 * Survey.java
 * Created by Jordan Stone, 2014
 * 
 * Survey.java creates and handles the Survey object. Surveys emulate a survey that can be taken and evaluated.
 * 
 * Subclasses include Test.java.
 */

package surveymaker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class Survey implements java.io.Serializable{

	private static final long serialVersionUID = 2L;
	protected String name; //Name
	protected ArrayList<Question> questions; //List of questions
	protected ArrayList<Response> responses; //List of answers
	
	
	public Survey(String n){ //Constructor
		name = n;
		questions = new ArrayList<Question>();
		responses = new ArrayList<Response>();
	}
	
	public void display(){ //Displays all Questions in the Survey
		for (int i = 1; i <= questions.size(); i++){
			
			Out.getDisp().renderLine("Question " + i);
			questions.get(i-1).show(); //Display every question
		}
	}
	
	public void modify() throws IOException{ //Allows user to modify Questions on a given Survey. Questions are modified in the Question classes themselves.
		boolean repeat = true;
		String s;
		int val = 0;
		while(repeat){
			display();
			Out.getDisp().renderLine("Enter the number of the question to modify.");
			val = Input.inputNum(1,questions.size()); //Input a number within an acceptable range
			
			Out.getDisp().renderLine("Question " + val + " selected.");		
			questions.get(val-1).modify();
			
			Out.getDisp().renderLine("Edit another question? (Y/N)");
			s = Input.inputString();
			if (s.equals("N")||s.equals("n")) repeat = false;	

		}//while(repeat)
	}
	
	public void take() throws IOException{ //Has the user answer all questions in the Survey. Responses are saved in a folder called responses with a .resp extension.
		responses.clear();
		Out.getDisp().renderLine("What is your name?");
		String s;
		s = Input.inputString();
		
		for (int i = 1; i <= questions.size(); i++){
			Out.getDisp().renderLine("Question " + i);
			questions.get(i-1).show();
			
			Response resp = questions.get(i-1).take();
//			resp.render();
			responses.add(resp); //Store the answer to the question
		}
		
		final File dir = new File("responses"); //Make sure the files folder exists
		if (!dir.exists()){
			dir.mkdir();
		}
		
		try{
	         FileOutputStream fileOut = new FileOutputStream("responses/" + name + "_" + s + ".resp"); // responses/Surveyname_username.resp
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(responses);
	         out.close();
	         fileOut.close();
	      }catch(IOException i){
	          i.printStackTrace();
	      }
		Out.getDisp().renderLine("Your responses are saved in " + "responses/" + name + "_" + s + ".resp");
	}
	
	public void tabulate(ArrayList<ArrayList<Response>> resp){ /*Organizes a 2D arraylist of responses to store responses per question
	then calls the tabulate function for every question using these response arrays*/
		
		ArrayList<ArrayList<Response>> tab = new ArrayList<ArrayList<Response>>();
		
		for (int i = 1; i < questions.size()+1; i++){ //Create new 2D Response array organizing each question's responses into an array
			tab.add(new ArrayList<Response>());
			for (int j = 0; j < resp.size(); j++){
				tab.get(i-1).add(resp.get(j).get(i-1));
			}
			Out.getDisp().renderLine('\n' + "Question " + i);
			Out.getDisp().renderLine(questions.get(i-1).getPrompt()); //Show each question's prompt
			questions.get(i-1).tabulate(tab.get(i-1));
		}
		
	}
	
//Getter Methods
	public String getName(){
		return name;
	}
	public ArrayList<Question> getQuestions(){
		return questions;
	}
	public ArrayList<Response> getResponses(){
		return responses;
	}
	
	
//Setter Methods
	public void setName(String n){
		name = n;
	}
	public void addQuestion(Question q){
		questions.add(q);
	}
	public void setQuestionList(ArrayList<Question> q){
		questions = q;
	}
	
	
}
