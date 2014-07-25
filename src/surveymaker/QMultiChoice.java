/*
 * QMultiChoice.java
 * Created by Jordan Stone, 2014
 * 
 * QMultiChoice.java creates and handles the QMultiChoice object. 
 * QMultiChoice creates a multiple choice question that can be used for Surveys and Tests. 
 * Multiple Choice questions have the user select an option from a given number of options.
 * 
 * Subclasses include QTrueFalse.
 */

package surveymaker;

import java.io.IOException;
import java.util.ArrayList;

public class QMultiChoice extends Question {

	private static final long serialVersionUID = 10L;
	protected ArrayList<String> choice;
	
	public QMultiChoice(String inp, ArrayList<String> c){
		super(inp);
		choice = c;
	}
	
	public void modify() throws IOException{ //Modify question - both prompt and answers
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
		
		Out.getDisp().renderLine("Would you like to modify the choices? (Y/N)");
		s = Input.inputString();
		
		if (s.equals("Y")||s.equals("y")){		
			for (int count = 1; count <= choice.size(); count++){ //Display all options
				Out.getDisp().renderLine(count + "- " + choice.get(count-1));
			}
			boolean repeat = true;
			while(repeat){
				Out.getDisp().renderLine("Enter the number of the choice to modify.");
				val = Input.inputNum(1,choice.size());
				Out.getDisp().renderLine("Enter the new choice value.");
				s = Input.inputString();
				
				choice.set(val-1, s);
				
				Out.getDisp().renderLine("Edit another choice? (Y/N)");
				s = Input.inputString();
				if (s.equals("N")||s.equals("n")){
					repeat = false;
				}//while(!validIn)
			}//while(repeat)
			
		}else Out.getDisp().renderLine("Choices left unchanged.");
	}
	
	public void show(){ //Displays the Multiple Choice question.
		Out.setDisp(2); //Set to Voice
		
		Out.getDisp().renderLine(prompt); //Show the prompt
		for (int count = 1; count <= choice.size(); count++){ //Display all options
			Out.getDisp().renderLine(count + "- " + choice.get(count-1));
		}
		
		Out.setDisp(1); //Set to Text
	}
	
	private Response takeAnswer() throws IOException{ //Implementation to take answer input. Used for storing user responses and storing answer keys.
		String s; //Input String
		@SuppressWarnings("unchecked")
		ArrayList<String> copy = (ArrayList<String>) choice.clone(); //Replacement of choice to allow popping off options
		ArrayList<String> ans = new ArrayList<String>(); //Stores all responses, given as value to returned Response object.
		boolean isEnd = false;
		while(!isEnd){ //Input loop
			boolean validIn = false;
			while(!validIn){ //Validity loop
				s = Input.inputString();
				if (s.equals("-END-")){
					copy.clear(); //Skip for loop by clearing the array
					validIn = true; //Stop looping
					isEnd = true; //Stop outer looping
				}
				for (int i = 0; i < copy.size(); i++){
					if (s.equals(copy.get(i))){
						validIn = true; //Stop looping
						ans.add(s); //Add to answers
						copy.remove(i); //Remove the response as an option
					}
				}//for (int i =0; i < choice.size(); i++)
				
				if (!validIn){
					Out.getDisp().renderLine("Input is not one of the question's existing options. Please input again.");
				}
			}//while(!validIn)
		}//while(!isEnd)
		
		return new RespStrArray(ans);
	}
	
	public Response modifyAns() throws IOException{ //Used for modifying answers for a Test
		Out.getDisp().renderLine("Enter new answers. You may have multiple answers. Enter \"-END-\" when finished.");
		
		return takeAnswer();
	}
	
	public Response take() throws IOException{ //Used for taking Survey or Test
		Out.getDisp().renderLine("Enter all choices that answer the question. Type \"-END-\" when you are finished.");
		
		return takeAnswer();
	}
	
	public void tabulate(ArrayList<Response> resp){ //Takes a list of relevant responses for a question and displays all choices made
		ArrayList <Integer> count = new ArrayList <Integer>();
		ArrayList <String> s = null;
		
		for (int i = 0; i < resp.size(); i++){ //For every response
			for (int j = 0; j < choice.size(); j++){ //For every option in choice
				if (count.size() < choice.size()){ //Bring count size up to size of choice.
					count.add(0);
				}
				s = ((RespStrArray)resp.get(i)).getAns();
				for (int k = 0; k < s.size(); k++){ //For every selection in the given response
					if (s.get(k).equals(choice.get(j))){ //If a response is equal to a choice...
						count.set(j, count.get(j) + 1); //...Increment that choice's count.
					}//if (s.get(k).equals(choice.get(j)))
				}//for (int k = 0; k < s.size(); k++)
			}//for (int j = 0; j < choice.size(); j++)
		}//for (int i = 0; i < resp.size(); i++)
		
		for (int i = 0; i < count.size(); i++){
			Out.getDisp().renderLine("Number of " + choice.get(i) + " responses: " + count.get(i));
		}
	}
	
//Getter Methods
	public ArrayList<String> getChoice(){
		return choice;
	}
	
}
