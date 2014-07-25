/*
 * Menu.java
 * Created by Jordan Stone, 2014
 * 
 * Menu.java is the Main class for SurveyTest - Contains functions to create, display, save, and load Survey and Test objects. 
 * Full functionality will eventually expand beyond this to make full use of Survey and Test objects. 
 */

package surveymaker;

import java.io.*;
import java.util.ArrayList;

public class Menu {
	
	public Survey makeSurvey(){	//Simple class to create a new Survey.
		Survey surv = new Survey("");
		return surv;	
	}
	
	public Test makeTest(){	//Simple class to create a new Test.
		Test tes = new Test("");	
		return tes;	
	}
	
	public void displaySurvey(Survey surv){ //Calls to Survey display method.
		surv.display();
	}
	
	public void displayTest(Test tes) throws IOException{ //Calls to Test display method, first checking if user wants to display answers as well.
		String s;
		Out.getDisp().renderLine("Display with answers? (Y/N)");
		s = Input.inputString();
		if (s.equals("Y")||s.equals("y")){
			tes.displayWithAns();
		}else tes.display();	
	}
	
	public Survey loadSurvey() throws IOException{ //Loads a Survey using serialization.
		String s = null; //Read string
		Survey surv = null; //Return variable
		ArrayList<String> files = new ArrayList<String>(); //Stores found survey objects in folder
		
		final File dir = new File("files");
		if (!dir.exists()){ // files folder does not exist
			Out.getDisp().renderLine("Folder \"files\" not found. Please create a Survey or Test first.");
			return null;
		}else{ // files folder does exist
			for(final File fileEntry : dir.listFiles()){
				String ext = fileEntry.getName().substring(fileEntry.getName().lastIndexOf(".") + 1, fileEntry.getName().length()); //Take the extension of each file
				if (ext.equals("surv")){ //If the extension is .surv, store it in the file array.
					files.add(fileEntry.getName());
				}
			}//for(final File fileEntry : dir.listFiles())
		}//else
		
		if (files.size() != 0){
			Out.getDisp().renderLine("Survey Files found.");
			for (int i = 0; i < files.size(); i++){
				Out.getDisp().renderLine(files.get(i));
			}
			Out.getDisp().renderLine("Please enter the desired Survey to load.");
			boolean validIn = false;
			while(!validIn){ //Validity loop
				s = Input.inputString();
				for (int i =0; i < files.size(); i++){
					if (s.equals(files.get(i))){
						validIn = true; //Stop looping
						Out.getDisp().renderLine("Survey " + files.get(i) + " selected.");
					}
				}//for (int i =0; i < choice.size(); i++)
				
				if (!validIn){
					Out.getDisp().renderLine("Input is not an existing Survey. Please input an existing Survey.");
				}
			}//while(!validIn)
			
			//Deserialization
			try
		      {
		         FileInputStream fileIn = new FileInputStream("files/" + s);
		         ObjectInputStream read = new ObjectInputStream(fileIn);
		         surv = (Survey) read.readObject();
		         read.close();
		         fileIn.close();
		      }catch(IOException i)
		      {
		         i.printStackTrace();
		         return null;
		      }catch(ClassNotFoundException c)
		      {
		         Out.getDisp().renderLine("Survey not found");
		         c.printStackTrace();
		         return null;
		      }
			return surv;	
		}else{
			Out.getDisp().renderLine("No Survey Files Found. Please create a Survey first.");
			return null;
		}
	}
	
	public Test loadTest() throws IOException{ //Loads a Test using serialization.
		String s = null; //Read string
		Test tes = null; //Return variable
		ArrayList<String> files = new ArrayList<String>(); //Stores found survey objects in folder
		
		final File dir = new File("files");
		if (!dir.exists()){ // files folder does not exist
			Out.getDisp().renderLine("Folder \"files\" not found. Please create a Survey or Test first.");
			return null;
		}else{ // files folder does exist
			for(final File fileEntry : dir.listFiles()){
				String ext = fileEntry.getName().substring(fileEntry.getName().lastIndexOf(".") + 1, fileEntry.getName().length()); //Take the extension of each file
				if (ext.equals("tes")){ //If the extension is .tes, store it in the file array.
					files.add(fileEntry.getName());
				}
			}
		}
		
		if (files.size() != 0){
			Out.getDisp().renderLine("Test Files found.");
			for (int i = 0; i < files.size(); i++){
				Out.getDisp().renderLine(files.get(i));
			}
			Out.getDisp().renderLine("Please enter the desired Test to load.");
			boolean validIn = false;
			while(!validIn){ //Validity loop
				s = Input.inputString();
				for (int i =0; i < files.size(); i++){
					if (s.equals(files.get(i))){
						validIn = true; //Stop looping
						Out.getDisp().renderLine("Test " + files.get(i) + " selected.");
					}
				}//for (int i =0; i < choice.size(); i++)
				
				if (!validIn){
					Out.getDisp().renderLine("Input is not an existing Test. Please input an existing Test.");
				}
			}//while(!validIn)
			
			//Deserialization
			try
		      {
		         FileInputStream fileIn = new FileInputStream("files/" + s);
		         ObjectInputStream read = new ObjectInputStream(fileIn);
		         tes = (Test) read.readObject();
		         read.close();
		         fileIn.close();
		      }catch(IOException i)
		      {
		         i.printStackTrace();
		         return null;
		      }catch(ClassNotFoundException c)
		      {
		         Out.getDisp().renderLine("Test not found");
		         c.printStackTrace();
		         return null;
		      }
			return tes;	
		}else{
			Out.getDisp().renderLine("No Test Files Found. Please create a Test first.");
			return null;
		}
	}
	
	public void storeSurvey(Survey surv){ //Stores a Survey using serialization at specified location.
		
		final File dir = new File("files"); //Make sure the files folder exists
		if (!dir.exists()){
			dir.mkdir();
		}
		
		//Serialization
		try{
	         FileOutputStream fileOut = new FileOutputStream("files/" + surv.getName() + ".surv");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(surv);
	         out.close();
	         fileOut.close();
	      }catch(IOException i){
	          i.printStackTrace();
	      }
		
	}
	
	public void storeTest(Test tes){ //Stores a Test using serialization at specified location.
		
		final File dir = new File("files"); //Make sure the files folder exists
		if (!dir.exists()){
			dir.mkdir();
		}
		
		//Serialization
		try{
	         FileOutputStream fileOut = new FileOutputStream("files/" + tes.getName() + ".tes");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(tes);
	         out.close();
	         fileOut.close();
	      }catch(IOException i){
	          i.printStackTrace();
	      }
		
	}
	
	@SuppressWarnings("unchecked")
	public void tabulateSurvey(Survey s) throws IOException{ //Takes a Survey given by the user and shows all response results for the Survey.
		Survey surv = s;
		ArrayList<ArrayList<Response>> resp = new ArrayList<ArrayList<Response>>();
		
		final File dir = new File("responses");
		if (!dir.exists()){ // files folder does not exist
			Out.getDisp().renderLine("Folder \"responses\" not found. Please take the Survey first to create this folder and populate it with responses.");
			return;
		}else{ // files folder does exist
			for(final File fileEntry : dir.listFiles()){
				
				String ext = fileEntry.getName().substring(fileEntry.getName().lastIndexOf(".") + 1, fileEntry.getName().length()); //Take the extension of each file
				if (ext.equals("resp")){ //If the extension is .resp, check for the right survey
					String surveyName = fileEntry.getName().substring(0,fileEntry.getName().indexOf("_")); //Take the Survey/Test name in the response file
					if (surveyName.equals(surv.getName())){ //If it is the right survey, add to the list of files
						//Deserialization
						try
					      {
					         FileInputStream fileIn = new FileInputStream("responses/" + fileEntry.getName());
					         ObjectInputStream read = new ObjectInputStream(fileIn);
					         resp.add((ArrayList<Response>) read.readObject());
					         read.close();
					         fileIn.close();
					      }catch(IOException i)
					      {
					         i.printStackTrace();
					         return;
					      }catch(ClassNotFoundException c)
					      {
					         Out.getDisp().renderLine("Response not found.");
					         c.printStackTrace();
					         return;
					      }//try catch
					}//if (surveyName.equals(surv.getName())) 
				}//if (ext.equals("resp"))			
			}//for(final File fileEntry : dir.listFiles())
		}//else
		
		if (resp.size() == 0){
			Out.getDisp().renderLine("No responses found for this Survey.");
		}else{
			surv.tabulate(resp); //Tabulate the ArrayList of ArrayList of Responses
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void tabulateTest(Test t) throws IOException{ //Takes a Test given by the user and shows all response results for the Test.
		Test tes = t;
		ArrayList<ArrayList<Response>> resp = new ArrayList<ArrayList<Response>>();
		
		final File dir = new File("responses");
		if (!dir.exists()){ // files folder does not exist
			Out.getDisp().renderLine("Folder \"responses\" not found. Please take the Test first to create this folder and populate it with responses.");
			return;
		}else{ // files folder does exist
			for(final File fileEntry : dir.listFiles()){
				
				String ext = fileEntry.getName().substring(fileEntry.getName().lastIndexOf(".") + 1, fileEntry.getName().length()); //Take the extension of each file
				if (ext.equals("resp")){ //If the extension is .resp, check for the right survey
					String surveyName = fileEntry.getName().substring(0,fileEntry.getName().indexOf("_")); //Take the Survey/Test name in the response file
					if (surveyName.equals(tes.getName())){ //If it is the right survey, add to the list of files
						//Deserialization
						try
					      {
					         FileInputStream fileIn = new FileInputStream("responses/" + fileEntry.getName());
					         ObjectInputStream read = new ObjectInputStream(fileIn);
					         resp.add((ArrayList<Response>) read.readObject());
					         read.close();
					         fileIn.close();
					      }catch(IOException i)
					      {
					         i.printStackTrace();
					         return;
					      }catch(ClassNotFoundException c)
					      {
					         Out.getDisp().renderLine("Response not found.");
					         c.printStackTrace();
					         return;
					      }//try catch
					}//if (surveyName.equals(surv.getName())) 
				}//if (ext.equals("resp"))			
			}//for(final File fileEntry : dir.listFiles())
		}//else
		
		if (resp.size() == 0){
			Out.getDisp().renderLine("No responses found for this Test.");
		}else{
			tes.tabulate(resp); //Tabulate the ArrayList of ArrayList of Responses
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void grade(Test t) throws IOException{ //Takes a Test and a response for that Test from the user and displays the calculated grade.
		Test tes = t;
		ArrayList<String> files = new ArrayList<String>();
		ArrayList<Response> resp;
		String s = null;
		
		final File dir = new File("responses");
		if (!dir.exists()){ // files folder does not exist
			Out.getDisp().renderLine("Folder \"responses\" not found. Please take the Test first to create this folder and populate it with responses.");
			return;
		}else{
			for(final File fileEntry : dir.listFiles()){
			
			String ext = fileEntry.getName().substring(fileEntry.getName().lastIndexOf(".") + 1, fileEntry.getName().length()); //Take the extension of each file
				if (ext.equals("resp")){ //If the extension is .resp, check for the right survey
					String surveyName = fileEntry.getName().substring(0,fileEntry.getName().indexOf("_")); //Take the Survey/Test name in the response file
					if (surveyName.equals(tes.getName())){ //If it is the right survey, add to the list of files
						files.add(fileEntry.getName());
					}//if (surveyName.equals(surv.getName())) 
				}//if (ext.equals("resp"))			
			}//for(final File fileEntry : dir.listFiles())
		}//else
		
		if (files.size() != 0){
			Out.getDisp().renderLine("Test Responses found.");
			for (int i = 0; i < files.size(); i++){
				Out.getDisp().renderLine(files.get(i));
			}
			Out.getDisp().renderLine("Please enter the desired Response to load.");
			boolean validIn = false;
			while(!validIn){ //Validity loop
				s = Input.inputString();
				for (int i =0; i < files.size(); i++){
					if (s.equals(files.get(i))){
						validIn = true; //Stop looping
						Out.getDisp().renderLine("Response " + files.get(i) + " selected.");
					}
				}//for (int i =0; i < choice.size(); i++)
				
				if (!validIn){
					Out.getDisp().renderLine("Input is not an existing Response. Please input an existing Response.");
				}
			}//while(!validIn)
			
			//Deserialization
			try
		      {
		         FileInputStream fileIn = new FileInputStream("responses/" + s);
		         ObjectInputStream read = new ObjectInputStream(fileIn);
		         resp = (ArrayList<Response>) read.readObject();
		         read.close();
		         fileIn.close();
		      }catch(IOException i)
		      {
		         i.printStackTrace();
		         return;
		      }catch(ClassNotFoundException c)
		      {
		         Out.getDisp().renderLine("Test not found");
		         c.printStackTrace();
		         return;
		      }
			tes.grade(resp);
		}else{
			Out.getDisp().renderLine("No Responses Found. Please take the Test first.");
		}	
	}
	
	public void menuOne() throws IOException{ //Main Menu method. 
		/*Handles calls to create, display, load, save, modify, take, grade, and tabulate surveys and tests.
		*/
		
		Survey surv = null; //Active Survey object
		boolean repeat = true; //Boolean to loop menu
		
		while (repeat){
			
			Out.setDisp(2); //Set to Voice
			
			Out.getDisp().renderLine("1) Create New Survey");
			Out.getDisp().renderLine("2) Create New Test");
			Out.getDisp().renderLine("3) Display Survey");
			Out.getDisp().renderLine("4) Display Test");
			Out.getDisp().renderLine("5) Load Survey");
			Out.getDisp().renderLine("6) Load Test");
			Out.getDisp().renderLine("7) Save Survey");
			Out.getDisp().renderLine("8) Save Test");
			Out.getDisp().renderLine("9) Modify Existing Survey");
			Out.getDisp().renderLine("10) Modify Existing Test");
			Out.getDisp().renderLine("11) Take a Survey");
			Out.getDisp().renderLine("12) Take a Test");
			Out.getDisp().renderLine("13) Grade a Test");
			Out.getDisp().renderLine("14) Tabulate a Survey");
			Out.getDisp().renderLine("15) Tabulate a Test");
			Out.getDisp().renderLine("16) Quit");
		
			String s = Input.inputString();
			
			switch (s){ //Menu Switch
				case "1": 
					Out.setDisp(1); //Set to Text
					surv = makeSurvey(); //Create Survey
					surv = menuTwo(surv);
					break;
					
				case "2": 
					Out.setDisp(1); //Set to Text
					surv = makeTest(); //Create Test
					surv = menuTwo(surv);
					break;
					
				case "3": //Display Survey
					Out.setDisp(1); //Set to Text
					
					if (surv != null){ //Does Survey exist
						if (!(surv instanceof Test)){ //Is it actually a Survey
							displaySurvey(surv);
						}else Out.getDisp().renderLine("Selected Survey is a Test. Choose to display a Test and try again.");
					}else Out.getDisp().renderLine("Survey is empty. Please load or create a survey first.");
					break;
					
				case "4": //Display Test
					Out.setDisp(1); //Set to Text
					
					if (surv != null){ //Does Test exist
						if (surv instanceof Test){ //Is it actually a Test
							displayTest((Test)surv);
						}else Out.getDisp().renderLine("Selected Survey is not a Test. Choose to display a Survey and try again.");
					}else Out.getDisp().renderLine("Test is empty. Please load or create a test first.");
					break;
					

				case "5": //Load Survey
					Out.setDisp(1); //Set to Text
					surv = loadSurvey();
					break;
					
				case "6": //Load Test
					Out.setDisp(1); //Set to Text
					surv = loadTest();
					break;
					
				case "7":  //Save Survey
					Out.setDisp(1); //Set to Text
					if (surv != null){
						if (!(surv instanceof Test)){ //Is it actually a Survey
							storeSurvey(surv);
							Out.getDisp().renderLine("Serialized survey " + surv.getName() + " is saved in " + surv.getName() + ".surv.");
						}else
							Out.getDisp().renderLine("Selected Survey is a Test. Choose to save a Test and try again.");
					}else{
						Out.getDisp().renderLine("Survey is empty. Please make a Survey first.");
					}
					
					break;
					
				case "8": //Save Test
					Out.setDisp(1); //Set to Text
					if (surv != null){
						if (surv instanceof Test){ //Is it actually a Test
							storeTest((Test)surv);
							Out.getDisp().renderLine("Serialized test " + surv.getName() + " is saved in " + surv.getName() + ".tes.");
						}else
							Out.getDisp().renderLine("Selected Survey is not a Test. Choose to save a Survey and try again.");
					}else{
						Out.getDisp().renderLine("Test is empty. Please make a Test first.");
					}
					
					break;
					
				case "9": //Modify Survey
					Out.setDisp(1); //Set to Text
					surv = loadSurvey();
					if (surv != null){
						surv.modify();
					}
					break;
					
				case "10"://Modify Test
					Out.setDisp(1); //Set to Text
					surv = loadTest();
					if(surv != null){
						surv.modify();
					}
					break;
					
				case "11"://Take Survey
					Out.setDisp(1); //Set to Text
					surv = loadSurvey();
					if (surv != null){
						surv.take();
						Out.getDisp().renderLine("The survey is complete. Thank you for your input.");
						storeSurvey(surv); //Save the survey to update the responses array
					}
					break;
					
				case "12"://Take Test
					Out.setDisp(1); //Set to Text
					surv = loadTest();
					if (surv != null){
						surv.take();
						Out.getDisp().renderLine("The test is complete. Thank you for your input.");
						storeTest((Test)surv); //Save the test to update the responses array
					}
					break;
					
				case "13"://Grade Test
					Out.setDisp(1); //Set to Text
					surv = loadTest();
					if (surv != null){
						grade((Test)surv);
					}
					break;
					
				case "14"://Tabulate Survey
					Out.setDisp(1); //Set to Text
					surv = loadSurvey();
					if (surv != null){
						tabulateSurvey(surv);
					}
					break;
					
				case "15"://Tabulate Test
					Out.setDisp(1); //Set to Text
					surv = loadTest();
					if (surv != null){
						tabulateTest((Test)surv);
					}
					break;
					
				case "16": Out.getDisp().renderLine("Good Bye."); //Exit
					repeat = false;
					break;
					
				default:
					Out.getDisp().renderLine("Invalid input.");
					break;
			}
		
		}
	}
	
	public Survey menuTwo(Survey surv) throws IOException{ //Secondary Menu method. 
		/*Used to construct new surveys and tests, adding questions (responses for tests) and naming.
		*/
		boolean repeat = true; //Boolean to loop menu
		
		String s; //String to take user input
		int val = 0; //int to store number inputs
		ArrayList<Question> q = new ArrayList<Question>(); //Stores all Questions and then adds them once user is done.
		ArrayList<Response> r = new ArrayList<Response>(); //Used only for Tests
		
		while (repeat){
		Out.getDisp().renderLine("What question type would you like to add?");
		Out.getDisp().renderLine("1) Add True/False Question");
		Out.getDisp().renderLine("2) Add Multiple Choice Question");
		Out.getDisp().renderLine("3) Add Short Answer Question");
		Out.getDisp().renderLine("4) Add Essay Question");
		Out.getDisp().renderLine("5) Add Ranking Question");
		Out.getDisp().renderLine("6) Add Matching Question");
		Out.getDisp().renderLine("7) Done Questions");
		
		s = Input.inputString();
		ArrayList<String> responses = new ArrayList<String>();
		ArrayList<String> responses2 = new ArrayList<String>();
	
			switch (s){
			case "1": Out.getDisp().renderLine("Enter the Question Prompt."); //TrueFalse
				s = Input.inputString();
				q.add(new QTrueFalse(s));
				
				//Add Answer for Tests
				if (surv instanceof Test){
					r.add(q.get(q.size()-1).modifyAns()); //Adds new answer
				}
				break;
				
			case "2": Out.getDisp().renderLine("Enter the Question Prompt."); //MultiChoice
				s = Input.inputString();
				Out.getDisp().renderLine("How many Options?");
				val = Input.inputNum(1,Integer.MAX_VALUE);
				for (int i = 1; i <= val; i++){ //Add Options
					Out.getDisp().renderLine("Option " + i + "?");
					responses.add(Input.inputString());
				}
				q.add(new QMultiChoice(s,responses));
				
				//Add Answer for Tests
				if (surv instanceof Test){
					r.add(q.get(q.size()-1).modifyAns()); //Adds new answer
				}
				break;
				
			case "3": Out.getDisp().renderLine("Enter the Question Prompt."); //ShortAns
				s = Input.inputString();
				Out.getDisp().renderLine("How many characters long can the answer be?");
				val = Input.inputNum(1,Integer.MAX_VALUE);
				q.add(new QShortAns(s,val));
				
				//Add Answer for Tests
				if (surv instanceof Test){
					r.add(q.get(q.size()-1).modifyAns()); //Adds new answer
				}
				break;
				
			case "4": Out.getDisp().renderLine("Enter the Question Prompt."); //Essay
				s = Input.inputString();
				q.add(new QEssay(s));
				
				//Add Answer for Tests
				if (surv instanceof Test){
					r.add(new RespStr(null)); //Fill in a response for Essay although it doesn't actually have an answer
				}
				break;
				
			case "5": Out.getDisp().renderLine("Enter the Question Prompt."); //Ranking
				s = Input.inputString();
				Out.getDisp().renderLine("How many Options?"); 
				val = Input.inputNum(1,Integer.MAX_VALUE);
				for (int i = 1; i <= val; i++){ //Add Options
					Out.getDisp().renderLine("Option " + i + "?");
					responses.add(Input.inputString());
				}
				q.add(new QRanking(s,responses));
				
				//Add Answer for Tests		
				if (surv instanceof Test){
					r.add(q.get(q.size()-1).modifyAns()); //Adds new answer
				}		
				break;
				
			case "6": Out.getDisp().renderLine("Enter the Question Prompt."); //Matching
				s = Input.inputString();
				Out.getDisp().renderLine("How many Options on Left Side?"); 
				val = Input.inputNum(1,Integer.MAX_VALUE);
				for (int i = 1; i <= val; i++){ //Add Left Options
					Out.getDisp().renderLine("Left Option " + i + "?");
					responses.add(Input.inputString());
				}
				
				Out.getDisp().renderLine("How many Options on Right Side?"); 
				val = Input.inputNum(1,Integer.MAX_VALUE);
				for (int i = 1; i <= val; i++){ //Add Right Options
					Out.getDisp().renderLine("Right Option " + i + "?");
					responses2.add(Input.inputString());
				}
				q.add(new QMatching(s,responses,responses2));
				
				//Add Answer for Tests
				if (surv instanceof Test){	
					r.add(q.get(q.size()-1).modifyAns()); //Adds new answer
				}
				break;
				
			case "7": Out.getDisp().renderLine("Finished questions."); //End
				surv.setQuestionList(q); //Add the questions
				if (surv instanceof Test){
					((Test) surv).setAnswerList(r); //Add the answers
				}
				repeat = false; //End the Question loop
				break;
				
			default:
				Out.getDisp().renderLine("Invalid input.");
				break;
			}
		} // while (repeat)
		
		Out.getDisp().renderLine("Please name this Survey or Test. (Don't use underscores)"); 
		boolean isValid = false; 
		while(!isValid){ //Shouldn't have underscores as it will break the response deserialization
			s = Input.inputString();
			if (!s.contains("_")){ //Doesn't contain underscore
				surv.setName(s);
				isValid = true;
			}else{
				Out.getDisp().renderLine("Input contains an underscore. Please give a name without an underscore.");
			}
		}//while(!isValid)
		
		return surv;
	}
	
	
	
	public static void main(String[] args) throws IOException{ //Main

		Menu menu = new Menu();
		
//		Out.getDisp().renderLine("What type of output would you like? (1 for Console, 2 for Voice)");
//		int s = Input.inputNum(1, 2);
//		Out.setDisp(s);
		
		Out.getDisp().renderLine("Hello, Please Choose an Option from the Menu.");
		menu.menuOne();

	}
	
}
