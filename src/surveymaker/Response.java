/* 
 * Response.java
 * Created by Jordan Stone, 2014
 * 
 * Response.java holds the abstract Response object. Responses are used to hold user input for a Survey or Test,
 * as well as to hold the correct answers used as a key in Tests.
 * 
 * Subclasses include RespStr, RespDict, RespStrArray, and RespInt.
 */

package surveymaker;

public abstract class Response implements java.io.Serializable{

	private static final long serialVersionUID = 5L;

	public abstract void render(); //Display the value of the Response object
	
	public abstract Boolean isNull(); //Checks if ans is null. This is used mainly to differentiate essays and other non-graded questions.
	
	public abstract Boolean isEqual(Response resp); //Compares two responses of the same subtype by their ans value. Used for grading purposes.
	
}
