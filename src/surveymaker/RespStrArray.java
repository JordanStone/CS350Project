package surveymaker;

import java.util.ArrayList;

public class RespStrArray extends Response {

	private static final long serialVersionUID = 53L;
	private ArrayList<String> ans;
	
	public RespStrArray(ArrayList<String> answer){ //Constructor
		ans = answer;
	}
	
	public void render() { //Display the value of the Response object
		for (int i =0; i < ans.size(); i++){
			Out.getDisp().renderLine(ans.get(i));
		}
	}
	
	public Boolean isNull(){ //Checks if ans is null. This is used mainly to differentiate essays and other non-graded questions.
		if (ans == null){
			return true;
		}else return false;
	}
	
	public Boolean isEqual(Response resp){ //Compares two responses of the same subtype by their ans value. Used for grading purposes.
		if (ans.equals(((RespStrArray)resp).getAns())){
			return true;
		}else return false;
	}
	
//Getter Methods
	public ArrayList<String> getAns(){
		return ans;
	}

}
