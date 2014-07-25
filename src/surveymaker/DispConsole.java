/* 
 * DispConsole.java
 * Created by Jordan Stone, 2014
 * 
 * DispConsole.java holds the console implementation of Display. 
 * DispConsole outputs to a Console.
 * 
 * Subclass of Display.java.
 */

package surveymaker;

public class DispConsole implements Display{

	public void render(String in){ //Outputs object value (prompt)
		System.out.print(in);
	}
	
	public void renderLine(String in){
		System.out.println(in);
	}
	
}