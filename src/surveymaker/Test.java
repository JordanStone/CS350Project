/*
 * Test.java
 * Created by Jordan Stone, 2014
 * 
 * Test.java creates and handles the Test object. Tests emulate a test that can be taken and evaluated.
 * 
 * Subclass of Survey.
 */

package surveymaker;

import java.io.IOException;
import java.util.ArrayList;

public class Test extends Survey {
	
	private static final long serialVersionUID = 3L;
	private ArrayList<Response> answers; //Answer Key
	
	public Test(String n){ //Constructor
		super(n);
	}
	
	public void displayWithAns(){ //Displays all Questions, with Answers also displayed.
		for (int i = 1; i <= questions.size(); i++){
			Out.getDisp().renderLine("Question " + i);
			questions.get(i-1).show(); //Display every question
			Out.getDisp().renderLine("Answer:");
			answers.get(i-1).render();
		}
	}
	
	public void modify() throws IOException{ /*Allows user to modify Questions on a given Test. Questions are modified in the Question classes themselves.
	This overloaded method of Test also includes modifying of Question answers.*/
		boolean repeat = true;
		String s;
		int val = 0;
		while(repeat){
			display();
			Out.getDisp().renderLine("Enter the number of the question to modify.");		
			val = Input.inputNum(1,questions.size()); //Input a number within an acceptable range
			
			Out.getDisp().renderLine("Question " + val + " selected.");
			questions.get(val-1).modify();
			
			Out.getDisp().renderLine("Modify the answer? (Y/N)");
//			answers.get(val-1).render();
			s = Input.inputString();
			if (s.equals("Y")||s.equals("y")){
				answers.set(val-1, questions.get(val-1).modifyAns()); //Modify answer, save in the same location.
			}else Out.getDisp().renderLine("Answer left unchanged.");		
			
			Out.getDisp().renderLine("Edit another question? (Y/N)");
			s = Input.inputString();
			if (s.equals("N")||s.equals("n")) repeat = false;	

		}//while(repeat)
	}
	
	public void grade(ArrayList<Response> resp){ //Grades each question in a test based on a given array of responses and displays this answer.
		int maxGrade = 0, userGrade = 0;
		int POINT = 10; //Point constant
		
		for (int i = 0; i < questions.size(); i++){
			if (!(answers.get(i).isNull())){ //If not an essay / non-graded question, add to the max grade and user grade.
				maxGrade += POINT;
				if (answers.get(i).isEqual(resp.get(i))){ //If the answer is the same, add 10 to the grade.
					userGrade += POINT;
				}
			}
		}
		Out.getDisp().renderLine("The grade is " + userGrade + "/" + maxGrade + ".");
	}
	
//Getter Methods
	
	
//Setter Methods
	public void addQuestion(Question q, Response a){
		questions.add(q);
		answers.add(a);
	}
	
	public void addAnswer (int QuestionNum, Response a){
		answers.set(QuestionNum,a);
	}
	
	public void setAnswerList(ArrayList<Response> r){
		answers = r;
	}
	
}
