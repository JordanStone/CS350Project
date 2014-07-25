/* 
 * RespDict.java
 * Created by Jordan Stone, 2014
 * 
 * RespDict.java holds the Dictionary implementation of Response. 
 * This Response is used for QMatching and QRanking and contains a HashMap of String and ArrayList<String>.
 * The String represents the first column of these questions, and act here as the key.
 * The ArrayList<String> is those values from the second column that match up to elements in the first column.
 * 
 * Subclass of Response.java.
 */

package surveymaker;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map.Entry;

public class RespDict extends Response {

	private static final long serialVersionUID = 52L;
	private HashMap<String, ArrayList<String>> ans; //Explained above - String represents first column, ArrayList<String> represents second column. 
	
	public RespDict(HashMap<String, ArrayList<String>> answer){ //Constructor
		ans = answer;
	}
	
	public void render(){ //Display the value of the Response object
		ArrayList<String> vals = new ArrayList<String>();
		for (Entry<String, ArrayList<String>> entry : ans.entrySet()){ //Iterate through all keys
			Out.getDisp().render(entry.getKey() + ": "); //Print the key
			vals = entry.getValue(); //Get the ArrayList attached to this key
			for (int i = 0; i < vals.size(); i++){ //Print all values attached to the key
				Out.getDisp().render(vals.get(i));
				if (i < vals.size()-1){ //Add commas until the final value of the key
					Out.getDisp().render(", ");
				}
			}
			System.out.printf("%n");
		}
	}
	
	public Boolean isNull(){ //Checks if ans is null. This is used mainly to differentiate essays and other non-graded questions.
		if (ans == null){
			return true;
		}else return false;
	}
	
	public Boolean isEqual(Response resp){ //Compares two responses of the same subtype by their ans value. Used for grading purposes.
		if (ans.equals(((RespDict)resp).getAns())){
			return true;
		}else return false;
	}
	
//Getter Methods
	public HashMap<String, ArrayList<String>> getAns(){
		return ans;
	}
	
}
