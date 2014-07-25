/* 
 * RespStr.java
 * Created by Jordan Stone, 2014
 * 
 * RespStr.java holds the Dictionary implementation of Response. 
 * This Response is used for QEssay and QShortAns.
 * Only contains a String as an answer.
 * 
 * Subclass of Response.java.
 */

package surveymaker;

public class RespStr extends Response {

	private static final long serialVersionUID = 50L;
	private String ans; //String answer
	
	public RespStr(String answer){ //Constructor
		ans = answer;
	}
	
	public void render(){ //Display the value of the Response object
		Out.getDisp().renderLine(ans);
	}
	
	public Boolean isNull(){ //Checks if ans is null. This is used mainly to differentiate essays and other non-graded questions.
		if (ans == null){
			return true;
		}else return false;
	}
	
	public Boolean isEqual(Response resp){ //Compares two responses of the same subtype by their ans value. Used for grading purposes.
		if (ans.equals(((RespStr)resp).getAns())){
			return true;
		}else return false;
	}

//Getter Methods
	public String getAns(){
		return ans;
	}
	
}
