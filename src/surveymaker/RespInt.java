/* 
 * RespInt.java
 * Created by Jordan Stone, 2014
 * 
 * RespInt.java holds the Dictionary implementation of Response. 
 * This Response is used for QMultiChoice and QTrueFalse.
 * Only contains an int as an answer.
 * 
 * Subclass of Response.java.
 */

package surveymaker;

public class RespInt extends Response {

	private static final long serialVersionUID = 51L;
	private int ans; //String answer
	
	public RespInt(int answer){ //Constructor
		ans = answer;
	}
	
	public void render(){ //Display the value of the Response object
		String print = ((Integer)ans).toString();
		Out.getDisp().renderLine(print);
	}
	
	public Boolean isNull(){ //Checks if ans is null. This is used mainly to differentiate essays and other non-graded questions.
		if ((Integer)ans == null){
			return true;
		}else return false;
	}
	
	public Boolean isEqual(Response resp){ //Compares two responses of the same subtype by their ans value. Used for grading purposes.
		if (((Integer)ans).equals(((RespInt) resp).getAns())){
			return true;
		}else return false;
	}
	
//Getter Methods
	public int getAns(){
		return ans;
	}
	
}
